package ua.training.servlet_project.model.dao;

import ua.training.servlet_project.model.entity.BookingRequestItem;

import java.util.List;

public interface BookingRequestItemDao extends GenericDao<BookingRequestItem> {
    List<BookingRequestItem> findAllByRequestId(Long requestId);
}
