package ua.training.servlet_project.model.dao.mapper;

import ua.training.servlet_project.model.entity.Apartment;
import ua.training.servlet_project.model.entity.RoomType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class ApartmentMapper implements ObjectMapper<Apartment> {
    @Override
    public Apartment extractFromResultSet(ResultSet rs) throws SQLException {
        return Apartment.builder()
                .id(rs.getLong("id"))
                .bedsCount(rs.getInt("beds_count"))
                .price(rs.getBigDecimal("price"))
                .type(RoomType.valueOf(rs.getString("type")))
                .schedule(new ArrayList<>())
                .build();
    }

    @Override
    public Apartment makeUnique(Map<Long, Apartment> cache, Apartment entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
