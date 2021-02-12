package ua.training.servlet_project.model.dao.mapper;

import ua.training.servlet_project.model.entity.Apartment;
import ua.training.servlet_project.model.entity.ApartmentDescription;
import ua.training.servlet_project.model.entity.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ApartmentDescriptionMapper implements ObjectMapper<ApartmentDescription> {
    @Override
    public ApartmentDescription extractFromResultSet(ResultSet rs) throws SQLException {
        return ApartmentDescription.builder()
                .id(rs.getLong("id"))
                .description(rs.getString("description"))
                .lang(Language.valueOf(rs.getString("lang")))
                .apartment(Apartment.builder()
                        .id(rs.getLong("apartment_id"))
                        .build())
                .build();
    }

    @Override
    public ApartmentDescription makeUnique(Map<Long, ApartmentDescription> cache, ApartmentDescription entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
