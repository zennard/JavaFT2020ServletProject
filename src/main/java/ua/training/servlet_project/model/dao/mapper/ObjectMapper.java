package ua.training.servlet_project.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface ObjectMapper<T> {

    T extractFromResultSet(ResultSet rs) throws SQLException;

    T makeUnique(Map<Long, T> cache, T entity);

    default int getTotalPages(ResultSet elementsCountResultSet, String countColumnName, int pageSize) throws SQLException {
        return (int) Math.ceil(elementsCountResultSet.getInt(countColumnName) / (double) pageSize);
    }
}
