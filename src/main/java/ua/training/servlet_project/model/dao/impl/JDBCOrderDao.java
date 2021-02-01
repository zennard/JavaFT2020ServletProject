package ua.training.servlet_project.model.dao.impl;

import ua.training.servlet_project.model.dao.OrderDao;
import ua.training.servlet_project.model.entity.Order;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JDBCOrderDao implements OrderDao {
    private Connection connection;

    public JDBCOrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Order entity) {

    }

    @Override
    public Optional<Order> findById(Long id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public void update(Order entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}
