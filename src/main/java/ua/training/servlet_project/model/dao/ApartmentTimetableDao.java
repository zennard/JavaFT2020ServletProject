package ua.training.servlet_project.model.dao;

import ua.training.servlet_project.model.entity.ApartmentTimetable;

import java.time.LocalDateTime;
import java.util.List;

public interface ApartmentTimetableDao extends GenericDao<ApartmentTimetable> {
    List<ApartmentTimetable> findAllScheduleByIdAndDate(LocalDateTime checkIn, LocalDateTime checkOut, Long id);
}
