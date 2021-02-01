package ua.training.servlet_project.model.dao.impl;

import ua.training.servlet_project.model.dao.BookingRequestDao;
import ua.training.servlet_project.model.entity.BookingRequest;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JDBCBookingRequestDao implements BookingRequestDao {
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

    }
}
