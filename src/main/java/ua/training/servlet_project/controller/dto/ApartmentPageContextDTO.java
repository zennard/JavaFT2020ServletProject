package ua.training.servlet_project.controller.dto;

import ua.training.servlet_project.model.entity.Apartment;

import java.util.List;

public class ApartmentPageContextDTO {
    List<Apartment> apartments;
    PageDTO page;
    DateDTO date;

    public ApartmentPageContextDTO() {
    }

    public ApartmentPageContextDTO(List<Apartment> apartments, PageDTO page, DateDTO date) {
        this.apartments = apartments;
        this.page = page;
        this.date = date;
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }

    public PageDTO getPage() {
        return page;
    }

    public void setPage(PageDTO page) {
        this.page = page;
    }

    public DateDTO getDate() {
        return date;
    }

    public void setDate(DateDTO date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ApartmentPageContextDTO{" +
                "apartments=" + apartments +
                ", page=" + page +
                ", date=" + date +
                '}';
    }

    public static ApartmentPageContextDTOBuilder builder() {
        return new ApartmentPageContextDTOBuilder();
    }

    public static class ApartmentPageContextDTOBuilder {
        List<Apartment> apartments;
        PageDTO page;
        DateDTO date;

        ApartmentPageContextDTOBuilder() {
        }

        public ApartmentPageContextDTOBuilder apartments(List<Apartment> apartments) {
            this.apartments = apartments;
            return this;
        }

        public ApartmentPageContextDTOBuilder page(PageDTO page) {
            this.page = page;
            return this;
        }

        public ApartmentPageContextDTOBuilder date(DateDTO date) {
            this.date = date;
            return this;
        }

        public ApartmentPageContextDTO build() {
            return new ApartmentPageContextDTO(apartments, page, date);
        }
    }
}
