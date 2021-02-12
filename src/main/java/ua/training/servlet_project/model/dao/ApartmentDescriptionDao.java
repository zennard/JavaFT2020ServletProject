package ua.training.servlet_project.model.dao;

import ua.training.servlet_project.model.entity.ApartmentDescription;

import java.util.Optional;

public interface ApartmentDescriptionDao extends GenericDao<ApartmentDescription> {
    Optional<ApartmentDescription> findApartmentDescriptionByApartmentIdAndLang(Long id, String lang);
}
