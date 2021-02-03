package ua.training.servlet_project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.model.dao.BookingRequestDao;
import ua.training.servlet_project.model.entity.BookingRequest;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JDBCBookingRequestDao implements BookingRequestDao {
    private static final Logger LOGGER = LogManager.getLogger(JDBCBookingRequestDao.class);
    private Connection connection;

    public JDBCBookingRequestDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(BookingRequest entity) {

    }

    @Override
    public Optional<BookingRequest> findById(Long id) {
        return null;
    }

    @Override
    public List<BookingRequest> findAll() {
        return null;
    }

    @Override
    public void update(BookingRequest entity) {

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
