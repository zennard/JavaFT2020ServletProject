package ua.training.servlet_project.model.dao;

import ua.training.servlet_project.controller.dto.ApartmentCriteriaDTO;
import ua.training.servlet_project.model.entity.Apartment;
import ua.training.servlet_project.model.entity.Page;
import ua.training.servlet_project.model.entity.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ApartmentDao extends GenericDao<Apartment> {
    Page<Apartment> findAllAvailableByDate(LocalDateTime checkIn, LocalDateTime checkOut, Pageable pageable);

    Page<Apartment> findAllFreeAvailableByDate(LocalDateTime checkIn, LocalDateTime checkOut,
                                               Pageable pageable, ApartmentCriteriaDTO apartmentCriteriaDTO);

    List<Apartment> findAllByIds(List<Long> ids);
}
