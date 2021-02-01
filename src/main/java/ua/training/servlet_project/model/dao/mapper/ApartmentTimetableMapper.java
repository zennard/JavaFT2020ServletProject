package ua.training.servlet_project.model.dao.mapper;

import ua.training.servlet_project.model.entity.ApartmentTimetable;
import ua.training.servlet_project.model.entity.RoomStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

public class ApartmentTimetableMapper implements ObjectMapper<ApartmentTimetable> {
    @Override
    public ApartmentTimetable extractFromResultSet(ResultSet rs) throws SQLException {
        Timestamp startsAtTimestamp = rs.getTimestamp("starts_at");
        Timestamp endsAtTimestamp = rs.getTimestamp("ends_at");
        String statusString = rs.getString("status");
        RoomStatus status = null;
        LocalDateTime startsAt = null;
        LocalDateTime endsAt = null;
        if (startsAtTimestamp != null && endsAtTimestamp != null && statusString != null) {
            startsAt = startsAtTimestamp.toLocalDateTime();
            endsAt = endsAtTimestamp.toLocalDateTime();
            status = RoomStatus.valueOf(statusString);
        }

        return ApartmentTimetable.builder()
                .id(rs.getLong("slot_id"))
                .startsAt(startsAt)
                .endsAt(endsAt)
                .status(status)
                .build();
    }

    @Override
    public ApartmentTimetable makeUnique(Map<Long, ApartmentTimetable> cache, ApartmentTimetable entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
