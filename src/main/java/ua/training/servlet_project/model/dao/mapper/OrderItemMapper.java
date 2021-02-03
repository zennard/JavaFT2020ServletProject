package ua.training.servlet_project.model.dao.mapper;

import ua.training.servlet_project.model.entity.Apartment;
import ua.training.servlet_project.model.entity.ApartmentTimetable;
import ua.training.servlet_project.model.entity.Order;
import ua.training.servlet_project.model.entity.OrderItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class OrderItemMapper implements ObjectMapper<OrderItem> {
    @Override
    public OrderItem extractFromResultSet(ResultSet rs) throws SQLException {
        return OrderItem.builder()
                .id(rs.getLong("id"))
                .schedule(ApartmentTimetable.builder()
                        .id(rs.getLong("apartment_timetable_id"))
                        .build())
                .amount(rs.getInt("amount"))
                .apartment(Apartment.builder()
                        .id(rs.getLong("apartment_id"))
                        .build())
                .order(Order.builder()
                        .id(rs.getLong("order_id"))
                        .build())
                .price(rs.getBigDecimal("price"))
                .startsAt(rs.getTimestamp("starts_at").toLocalDateTime())
                .endsAt(rs.getTimestamp("ends_at").toLocalDateTime())
                .build();
    }

    @Override
    public OrderItem makeUnique(Map<Long, OrderItem> cache, OrderItem entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
