package ua.training.servlet_project.controller.dto;

import ua.training.servlet_project.model.entity.RequestStatus;

public class BookingRequestUpdateDTO {
    private Long id;
    private RequestStatus status;

    public BookingRequestUpdateDTO() {
    }

    public BookingRequestUpdateDTO(Long id, RequestStatus status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BookingRequestUpdateDTO{" +
                "userId=" + id +
                ", status=" + status +
                '}';
    }

    public static BookingRequestUpdateDTOBuilder builder() {
        return new BookingRequestUpdateDTOBuilder();
    }

    public static class BookingRequestUpdateDTOBuilder {
        private Long id;
        private RequestStatus status;

        BookingRequestUpdateDTOBuilder() {
        }

        public BookingRequestUpdateDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BookingRequestUpdateDTOBuilder status(RequestStatus status) {
            this.status = status;
            return this;
        }

        public BookingRequestUpdateDTO build() {
            return new BookingRequestUpdateDTO(id, status);
        }
    }
}
