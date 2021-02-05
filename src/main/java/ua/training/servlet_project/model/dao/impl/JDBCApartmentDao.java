package ua.training.servlet_project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.model.dao.ApartmentDao;
import ua.training.servlet_project.model.dao.mapper.ApartmentMapper;
import ua.training.servlet_project.model.dao.mapper.ApartmentTimetableMapper;
import ua.training.servlet_project.model.entity.Apartment;
import ua.training.servlet_project.model.entity.ApartmentTimetable;
import ua.training.servlet_project.model.entity.Page;
import ua.training.servlet_project.model.entity.Pageable;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class JDBCApartmentDao implements ApartmentDao {
    private static final Logger LOGGER = LogManager.getLogger(JDBCApartmentDao.class);
    private static final String COUNT_COLUMN_NAME = "COUNT(*)";
    private Connection connection;

    public JDBCApartmentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Apartment entity) {

    }

    @Override
    public Optional<Apartment> findById(Long id) {
        Optional<Apartment> apartment = Optional.empty();

        try (PreparedStatement ps = connection.prepareCall(
                "SELECT * FROM apartment " +
                        "WHERE id = ?")) {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            ApartmentMapper mapper = new ApartmentMapper();
            if (rs.next()) {
                apartment = Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException ex) {
            LOGGER.error(ex);
        }

        return apartment;
    }

    @Override
    public List<Apartment> findAllByIds(List<Long> ids) {
        Map<Long, Apartment> apartments = new LinkedHashMap<>();
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));

        try (PreparedStatement ps = connection.prepareCall(String.format("SELECT * FROM apartment a WHERE a.id IN (%s)", inSql))
        ) {
            //@TODO check if list is empty
            for (int i = 1; i <= ids.size(); i++) {
                ps.setLong(i, ids.get(i - 1));
            }

            ResultSet rs = ps.executeQuery();

            ApartmentMapper apartmentMapper = new ApartmentMapper();
            while (rs.next()) {
                Apartment apartment = apartmentMapper
                        .extractFromResultSet(rs);
                apartmentMapper
                        .makeUnique(apartments, apartment);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex);
            throw new RuntimeException(ex);
        }
        return new ArrayList<>(apartments.values());
    }

    @Override
    public List<Apartment> findAll() {
        return null;
    }

    @Override
    public Page<Apartment> findAllAvailableByDate(LocalDateTime checkIn, LocalDateTime checkOut, Pageable pageable) {
        Map<Long, Apartment> apartments = new LinkedHashMap<>();
        Map<Long, ApartmentTimetable> schedules = new LinkedHashMap<>();
        int totalPages = 0;

        LOGGER.info(checkIn);
        LOGGER.info(checkOut);
        LOGGER.info(pageable);

        try (PreparedStatement ps = connection.prepareCall(
                String.format("SELECT id, beds_count," +
                        " price, type, starts_at," +
                        " ends_at, status, slot_id " +
                        "FROM apartment ap " +
                        "LEFT JOIN " +
                        "    (SELECT starts_at, ends_at, status, apartment_id, t.id as slot_id " +
                        "     FROM apartment a " +
                        "     LEFT JOIN apartment_timetable t " +
                        "     ON a.id = t.apartment_id " +
                        "     WHERE is_available = true AND (" +
                        "       t.starts_at <= ? AND t.ends_at >= ? OR " +
                        "       t.starts_at BETWEEN ? AND ? OR " +
                        "       t.ends_at BETWEEN ? AND ? " +
                        "     ) " +
                        ") AS r " +
                        "ON ap.id = r.apartment_id " +
                        "ORDER BY %s, id " +
                        "LIMIT ? " +
                        "OFFSET ? ", pageable.getSortBy().toString().toLowerCase()));
             PreparedStatement countQuery = connection.prepareCall(
                     "SELECT COUNT(*) " +
                             "FROM apartment"
             )
        ) {
            ps.setTimestamp(1, Timestamp.valueOf(checkIn));
            ps.setTimestamp(2, Timestamp.valueOf(checkOut));
            ps.setTimestamp(3, Timestamp.valueOf(checkIn));
            ps.setTimestamp(4, Timestamp.valueOf(checkOut));
            ps.setTimestamp(5, Timestamp.valueOf(checkIn));
            ps.setTimestamp(6, Timestamp.valueOf(checkOut));
            ps.setInt(7, pageable.getPageSize());
            ps.setInt(8, pageable.getPageSize() * pageable.getPageNumber());

            ResultSet rs = ps.executeQuery();
            ResultSet totalElementsResultSet = countQuery.executeQuery();

            ApartmentMapper apartmentMapper = new ApartmentMapper();
            ApartmentTimetableMapper apartmentTimetableMapper = new ApartmentTimetableMapper();
            while (rs.next()) {
                Apartment apartment = apartmentMapper
                        .extractFromResultSet(rs);
                LOGGER.info(apartment);
                ApartmentTimetable schedule = apartmentTimetableMapper
                        .extractFromResultSet(rs);
                apartment = apartmentMapper
                        .makeUnique(apartments, apartment);
                schedule = apartmentTimetableMapper
                        .makeUnique(schedules, schedule);
                if (schedule.getStartsAt() != null && schedule.getEndsAt() != null) {
                    apartment.getSchedule().add(schedule);
                }
            }

            if (totalElementsResultSet.next()) {
                totalPages = apartmentMapper.getTotalPages(totalElementsResultSet,
                        COUNT_COLUMN_NAME, pageable.getPageSize());
                LOGGER.info(totalPages);
            }
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw new RuntimeException(ex);
        }

        return new Page<>(new ArrayList<>(apartments.values()), pageable, totalPages);
    }

    @Override
    public void update(Apartment entity) {

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
