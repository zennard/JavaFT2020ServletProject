package ua.training.servlet_project.controller.dto;

import ua.training.servlet_project.model.entity.RoomStatus;
import ua.training.servlet_project.model.entity.RoomType;

import java.util.List;

public class ApartmentCriteriaDTO {
    private List<RoomType> types;
    private RoomStatus status;

    public ApartmentCriteriaDTO() {
    }

    public ApartmentCriteriaDTO(List<RoomType> types, RoomStatus status) {
        this.types = types;
        this.status = status;
    }

    public List<RoomType> getTypes() {
        return types;
    }

    public void setTypes(List<RoomType> types) {
        this.types = types;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ApartmentCriteriaDTO{" +
                "types=" + types +
                ", status=" + status +
                '}';
    }
}
