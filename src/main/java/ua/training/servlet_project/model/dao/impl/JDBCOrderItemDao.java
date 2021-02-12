package ua.training.servlet_project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet_project.model.dao.OrderItemDao;
import ua.training.servlet_project.model.dao.mapper.OrderItemMapper;
import ua.training.servlet_project.model.entity.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JDBCOrderItemDao implements OrderItemDao {
    private static final Logger LOGGER = LogManager.getLogger(JDBCOrderItemDao.class);
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
    public List<OrderItem> findAllByOrderId(Long orderId) {
        Map<Long, OrderItem> orderItems = new LinkedHashMap<>();

        try (PreparedStatement ps = connection.prepareCall(
                "SELECT * FROM order_item o " +
                        "WHERE o.order_id = ? ")
        ) {
            ps.setLong(1, orderId);

            ResultSet rs = ps.executeQuery();

            OrderItemMapper orderItemMapper = new OrderItemMapper();
            while (rs.next()) {
                OrderItem orderItem = orderItemMapper
                        .extractFromResultSet(rs);
                orderItemMapper
                        .makeUnique(orderItems, orderItem);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex);
            throw new RuntimeException(ex);
        }
        return new ArrayList<>(orderItems.values());
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
        try {
            connection.close();
        } catch (SQLException throwable) {
            LOGGER.error(throwable);
        }
    }
}
