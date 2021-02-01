package ua.training.servlet_project.model.dao.impl;

import ua.training.servlet_project.model.dao.BookingRequestItemDao;
import ua.training.servlet_project.model.entity.BookingRequestItem;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JDBCBookingRequestItemDao implements BookingRequestItemDao {
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

    }
}
