package ua.training.servlet_project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.model.dao.BookingRequestItemDao;
import ua.training.servlet_project.model.dao.mapper.BookingRequestItemMapper;
import ua.training.servlet_project.model.entity.BookingRequestItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JDBCBookingRequestItemDao implements BookingRequestItemDao {
    private static final Logger LOGGER = LogManager.getLogger(JDBCBookingRequestItemDao.class);
    private Connection connection;

    public JDBCBookingRequestItemDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(BookingRequestItem entity) {

    }

    @Override
    public Optional<BookingRequestItem> findById(Long id) {
        return null;
    }

    @Override
    public List<BookingRequestItem> findAllByRequestId(Long requestId) {
        Map<Long, BookingRequestItem> requestItems = new LinkedHashMap<>();

        try (PreparedStatement ps = connection.prepareCall(
                "SELECT * FROM booking_request_item i " +
                        "WHERE i.booking_request_id = ? ")
        ) {
            ps.setLong(1, requestId);

            ResultSet rs = ps.executeQuery();

            BookingRequestItemMapper requestItemMapper = new BookingRequestItemMapper();
            while (rs.next()) {
                BookingRequestItem requestItem = requestItemMapper
                        .extractFromResultSet(rs);
                requestItemMapper
                        .makeUnique(requestItems, requestItem);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex);
            throw new RuntimeException(ex);
        }
        return new ArrayList<>(requestItems.values());
    }

    @Override
    public List<BookingRequestItem> findAll() {
        return null;
    }

    @Override
    public void update(BookingRequestItem entity) {

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
