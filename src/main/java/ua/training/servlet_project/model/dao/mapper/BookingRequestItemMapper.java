package ua.training.servlet_project.model.dao.mapper;

import ua.training.servlet_project.model.entity.BookingRequest;
import ua.training.servlet_project.model.entity.BookingRequestItem;
import ua.training.servlet_project.model.entity.RoomType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class BookingRequestItemMapper implements ObjectMapper<BookingRequestItem> {
    @Override
    public BookingRequestItem extractFromResultSet(ResultSet rs) throws SQLException {
        return BookingRequestItem.builder()
                .id(rs.getLong("id"))
                .bedsCount(rs.getInt("beds_count"))
                .type(RoomType.valueOf(rs.getString("type")))
                .bookingRequest(BookingRequest.builder()
                        .id(rs.getLong("booking_request_id"))
                        .build())
                .build();
    }

    @Override
    public BookingRequestItem makeUnique(Map<Long, BookingRequestItem> cache, BookingRequestItem entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
