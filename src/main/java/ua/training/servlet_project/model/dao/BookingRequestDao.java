package ua.training.servlet_project.model.dao;

import ua.training.servlet_project.controller.dto.BookingRequestCreationDTO;
import ua.training.servlet_project.controller.dto.BookingRequestUpdateDTO;
import ua.training.servlet_project.model.entity.BookingRequest;
import ua.training.servlet_project.model.entity.Page;
import ua.training.servlet_project.model.entity.Pageable;
import ua.training.servlet_project.model.entity.RequestStatus;

public interface BookingRequestDao extends GenericDao<BookingRequest> {
    Long save(BookingRequestCreationDTO bookingRequestCreationDTO);

    void updateStatus(BookingRequestUpdateDTO bookingRequestUpdateDTO);

    Page<BookingRequest> findAllByStatus(RequestStatus status, Pageable pageable);
}
