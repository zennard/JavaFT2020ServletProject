package ua.training.servlet_project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.model.dao.ApartmentTimetableDao;
import ua.training.servlet_project.model.dao.mapper.ApartmentTimetableMapper;
import ua.training.servlet_project.model.entity.ApartmentTimetable;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class JDBCApartmentTimetableDao implements ApartmentTimetableDao {
    private static final Logger LOGGER = LogManager.getLogger(JDBCApartmentTimetableDao.class);
    private Connection connection;

    public JDBCApartmentTimetableDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(ApartmentTimetable entity) {
    }

    @Override
    public Optional<ApartmentTimetable> findById(Long id) {
        Optional<ApartmentTimetable> apartmentTimetable = Optional.empty();

        try (PreparedStatement ps = connection.prepareCall("SELECT * FROM apartment_timetable WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            ApartmentTimetableMapper mapper = new ApartmentTimetableMapper();
            if (rs.next()) {
                apartmentTimetable = Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (Exception ex) {
            LOGGER.error(ex);
            throw new RuntimeException(ex);
        }

        LOGGER.info(apartmentTimetable);
        return apartmentTimetable;
    }

    @Override
    public List<ApartmentTimetable> findAll() {
        return null;
    }

    @Override
    public List<ApartmentTimetable> findAllScheduleByIdAndDate(LocalDateTime checkIn, LocalDateTime checkOut, Long id) {
        Map<Long, ApartmentTimetable> schedules = new LinkedHashMap<>();

        try (PreparedStatement ps = connection.prepareCall(
                "SELECT id AS slot_id, starts_at, ends_at, status, apartment_id " +
                        "FROM apartment_timetable t " +
                        "WHERE t.apartment_id = ? AND (" +
                        " t.starts_at BETWEEN ? AND ? OR " +
                        " t.ends_at BETWEEN ? AND ? OR " +
                        " t.starts_at <= ? AND t.ends_at >= ? " +
                        ")")
        ) {
            ps.setLong(1, id);
            ps.setTimestamp(2, Timestamp.valueOf(checkIn));
            ps.setTimestamp(3, Timestamp.valueOf(checkOut));
            ps.setTimestamp(4, Timestamp.valueOf(checkIn));
            ps.setTimestamp(5, Timestamp.valueOf(checkOut));
            ps.setTimestamp(6, Timestamp.valueOf(checkIn));
            ps.setTimestamp(7, Timestamp.valueOf(checkOut));

            ResultSet rs = ps.executeQuery();

            ApartmentTimetableMapper scheduleMapper = new ApartmentTimetableMapper();
            while (rs.next()) {
                ApartmentTimetable schedule = scheduleMapper
                        .extractFromResultSet(rs);
                scheduleMapper
                        .makeUnique(schedules, schedule);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex);
            throw new RuntimeException(ex);
        }
        return new ArrayList<>(schedules.values());
    }

    @Override
    public void update(ApartmentTimetable entity) {

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
