package ua.training.servlet_project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.controller.dto.BookingRequestCreationDTO;
import ua.training.servlet_project.controller.dto.BookingRequestItemDTO;
import ua.training.servlet_project.model.dao.BookingRequestDao;
import ua.training.servlet_project.model.dao.mapper.BookingRequestMapper;
import ua.training.servlet_project.model.entity.BookingRequest;
import ua.training.servlet_project.model.entity.Page;
import ua.training.servlet_project.model.entity.Pageable;
import ua.training.servlet_project.model.entity.RequestStatus;
import ua.training.servlet_project.model.exceptions.BookingRequestCreationException;
import ua.training.servlet_project.model.exceptions.EntityCreationException;
import ua.training.servlet_project.model.util.PropertiesLoader;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class JDBCBookingRequestDao implements BookingRequestDao {
    private static final Logger LOGGER = LogManager.getLogger(JDBCBookingRequestDao.class);
    private static final int SETTLEMENT_MINUTES = 0;
    private static final String BOOKING_REQUEST_NOT_CREATED_EXCEPTION_MESSAGE = "Booking request was not created successfully!";
    private static final String COUNT_COLUMN_NAME = "COUNT(*)";
    private Connection connection;
    private final Integer checkInHours;
    private final Integer checkOutHours;

    public JDBCBookingRequestDao(Connection connection) {
        this.connection = connection;
        Properties properties = PropertiesLoader.getProperties("application.properties");
        checkInHours = Integer.valueOf(properties.getProperty("apartment.check.in.time"));
        checkOutHours = Integer.valueOf(properties.getProperty("apartment.check.out.time"));
    }

    @Override
    public void create(BookingRequest entity) {

    }

    @Override
    public Optional<BookingRequest> findById(Long id) {
        Optional<BookingRequest> result = Optional.empty();

        try (PreparedStatement ps = connection.prepareCall("SELECT * FROM booking_request WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            BookingRequestMapper mapper = new BookingRequestMapper();
            if (rs.next()) {
                result = Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public List<BookingRequest> findAll() {
        return null;
    }

    @Override
    public Page<BookingRequest> findAllByStatus(RequestStatus status, Pageable pageable) {
        Map<Long, BookingRequest> requests = new LinkedHashMap<>();
        int totalPages = 0;

        try (PreparedStatement ps = connection.prepareCall(
                "SELECT * FROM booking_request r " +
                        "WHERE r.request_status = ? " +
                        "ORDER BY r.id " +
                        "LIMIT ? " +
                        "OFFSET ? ");
             PreparedStatement countQuery = connection.prepareCall(
                     "SELECT COUNT(*) FROM booking_request r " +
                             "WHERE r.request_status = ? "
             )
        ) {
            ps.setString(1, status.toString());
            ps.setInt(2, pageable.getPageSize());
            ps.setInt(3, pageable.getPageNumber() * pageable.getPageSize());

            countQuery.setString(1, status.toString());

            ResultSet rs = ps.executeQuery();
            ResultSet totalElementsResultSet = countQuery.executeQuery();

            BookingRequestMapper requestMapper = new BookingRequestMapper();
            while (rs.next()) {
                BookingRequest request = requestMapper
                        .extractFromResultSet(rs);
                requestMapper
                        .makeUnique(requests, request);
            }

            if (totalElementsResultSet.next()) {
                totalPages = requestMapper.getTotalPages(totalElementsResultSet,
                        COUNT_COLUMN_NAME, pageable.getPageSize());
            }
        } catch (SQLException ex) {
            LOGGER.error(ex);
            throw new RuntimeException(ex);
        }
        return new Page<>(new ArrayList<>(requests.values()), pageable, totalPages);
    }

    @Override
    public void update(BookingRequest entity) {

    }

    @Override
    public Long save(BookingRequestCreationDTO bookingRequestCreationDTO) {
        Long newBookingRequestId = null;
        try {
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO booking_request " +
                            " (starts_at, ends_at, request_date, user_id, request_status)" +
                            " VALUES (?, ?, ?, ?, ?) ",
                    Statement.RETURN_GENERATED_KEYS)) {
                LocalDateTime startsAt = LocalDateTime.of(bookingRequestCreationDTO.getStartsAt(),
                        LocalTime.of(checkInHours, SETTLEMENT_MINUTES));
                LocalDateTime endsAt = LocalDateTime.of(bookingRequestCreationDTO.getEndsAt(),
                        LocalTime.of(checkOutHours, SETTLEMENT_MINUTES));
                ps.setTimestamp(1, Timestamp.valueOf(startsAt));
                ps.setTimestamp(2, Timestamp.valueOf(endsAt));
                ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                ps.setLong(4, bookingRequestCreationDTO.getUserId());
                ps.setString(5, RequestStatus.NEW.toString());
                ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        newBookingRequestId = generatedKeys.getLong(1);
                    } else {
                        throw new BookingRequestCreationException(BOOKING_REQUEST_NOT_CREATED_EXCEPTION_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                LOGGER.error(ex);
                throw new EntityCreationException(ex);
            }

            try (PreparedStatement ps = connection.prepareCall("INSERT INTO booking_request_item " +
                    "(beds_count, type, booking_request_id)" +
                    " VALUES (?, ?, ?)")) {
                LOGGER.info(bookingRequestCreationDTO.getRequestItems());
                for (BookingRequestItemDTO item : bookingRequestCreationDTO.getRequestItems()) {
                    ps.setInt(1, item.getBedsCount());
                    ps.setString(2, item.getType().toString());
                    ps.setLong(3, newBookingRequestId);
                    ps.addBatch();
                }

                ps.executeBatch();
            } catch (SQLException ex) {
                LOGGER.error(ex);
                throw new EntityCreationException(ex);
            }

            LOGGER.info("committing...");

            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception ex) {
            LOGGER.error(ex.getStackTrace());
            try {
                connection.rollback();
            } catch (SQLException throwable) {
                LOGGER.error(throwable);
            }
        }
        return newBookingRequestId;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException throwable) {
            LOGGER.error(throwable);
        }
    }
}
