package ua.training.servlet_project.model.dao.mapper;

import ua.training.servlet_project.filters.HttpMethodType;
import ua.training.servlet_project.model.entity.RoleType;
import ua.training.servlet_project.model.entity.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class RuleMapper implements ObjectMapper<Rule> {
    @Override
    public Rule extractFromResultSet(ResultSet rs) throws SQLException {
        return Rule.builder()
                .id(rs.getLong("id"))
                .pattern(rs.getString("url_pattern"))
                .role(RoleType.valueOf(rs.getString("role")))
                .method(HttpMethodType.valueOf(rs.getString("method")))
                .build();
    }

    @Override
    public Rule makeUnique(Map<Long, Rule> cache, Rule entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
