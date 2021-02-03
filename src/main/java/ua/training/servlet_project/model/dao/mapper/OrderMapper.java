package ua.training.servlet_project.model.dao.mapper;

import ua.training.servlet_project.model.entity.Order;
import ua.training.servlet_project.model.entity.OrderStatus;
import ua.training.servlet_project.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class OrderMapper implements ObjectMapper<Order> {
    @Override
    public Order extractFromResultSet(ResultSet rs) throws SQLException {
        return Order.builder()
                .id(rs.getLong("id"))
                .orderDate(rs.getTimestamp("order_date").toLocalDateTime())
                .orderStatus(OrderStatus.valueOf(rs.getString("order_status")))
                .user(User.builder().id(rs.getLong("user_id")).build())
                .orderItems(new ArrayList<>())
                .build();
    }

    @Override
    public Order makeUnique(Map<Long, Order> cache, Order entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
