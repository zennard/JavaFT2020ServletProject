package ua.training.servlet_project.model.dao.mapper;

import ua.training.servlet_project.model.entity.BookingRequest;
import ua.training.servlet_project.model.entity.RequestStatus;
import ua.training.servlet_project.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class BookingRequestMapper implements ObjectMapper<BookingRequest> {
    @Override
    public BookingRequest extractFromResultSet(ResultSet rs) throws SQLException {
        return BookingRequest.builder()
                .id(rs.getLong("id"))
                .requestStatus(RequestStatus.valueOf(rs.getString("request_status")))
                .requestDate(rs.getTimestamp("request_date").toLocalDateTime())
                .startsAt(rs.getTimestamp("starts_at").toLocalDateTime())
                .endsAt(rs.getTimestamp("ends_at").toLocalDateTime())
                .user(User.builder().id(rs.getLong("user_id")).build())
                .build();
    }

    @Override
    public BookingRequest makeUnique(Map<Long, BookingRequest> cache, BookingRequest entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
