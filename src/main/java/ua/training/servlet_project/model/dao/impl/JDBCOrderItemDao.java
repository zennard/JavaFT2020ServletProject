package ua.training.servlet_project.model.dao.impl;

import ua.training.servlet_project.model.dao.OrderItemDao;
import ua.training.servlet_project.model.entity.OrderItem;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JDBCOrderItemDao implements OrderItemDao {
    private Connection connection;

    public JDBCOrderItemDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(OrderItem entity) {

    }

    @Override
    public Optional<OrderItem> findById(Long id) {
        return null;
    }

    @Override
    public List<OrderItem> findAll() {
        return null;
    }

    @Override
    public void update(OrderItem entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}
