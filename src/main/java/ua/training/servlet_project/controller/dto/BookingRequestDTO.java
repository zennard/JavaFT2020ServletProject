package ua.training.servlet_project.controller.dto;

import ua.training.servlet_project.model.entity.RequestStatus;

import java.time.LocalDateTime;
import java.util.List;

public class BookingRequestDTO {
    private Long id;
    private Long userId;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private LocalDateTime requestDate;
    private List<BookingRequestItemDTO> requestItems;
    private RequestStatus requestStatus;

    public BookingRequestDTO() {
    }

    public BookingRequestDTO(Long id, Long userId, LocalDateTime startsAt, LocalDateTime endsAt,
                             List<BookingRequestItemDTO> requestItems, RequestStatus requestStatus,
                             LocalDateTime requestDate) {
        this.id = id;
        this.userId = userId;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.requestItems = requestItems;
        this.requestStatus = requestStatus;
        this.requestDate = requestDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
    }


    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(LocalDateTime endsAt) {
        this.endsAt = endsAt;
    }

    public List<BookingRequestItemDTO> getRequestItems() {
        return requestItems;
    }

    public void setRequestItems(List<BookingRequestItemDTO> requestItems) {
        this.requestItems = requestItems;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    @Override
    public String toString() {
        return "BookingRequestDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", startsAt=" + startsAt +
                ", endsAt=" + endsAt +
                ", requestDate=" + requestDate +
                ", requestItems=" + requestItems +
                ", requestStatus=" + requestStatus +
                '}';
    }

    public static BookingRequestDTOBuilder builder() {
        return new BookingRequestDTOBuilder();
    }

    public static class BookingRequestDTOBuilder {
        private Long id;
        private Long userId;
        private LocalDateTime startsAt;
        private LocalDateTime endsAt;
        private LocalDateTime requestDate;
        private List<BookingRequestItemDTO> requestItems;
        private RequestStatus requestStatus;

        BookingRequestDTOBuilder() {
        }

        public BookingRequestDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BookingRequestDTOBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public BookingRequestDTOBuilder startsAt(LocalDateTime startsAt) {
            this.startsAt = startsAt;
            return this;
        }

        public BookingRequestDTOBuilder endsAt(LocalDateTime endsAt) {
            this.endsAt = endsAt;
            return this;
        }

        public BookingRequestDTOBuilder requestDate(LocalDateTime requestDate) {
            this.requestDate = requestDate;
            return this;
        }

        public BookingRequestDTOBuilder requestItems(List<BookingRequestItemDTO> requestItems) {
            this.requestItems = requestItems;
            return this;
        }

        public BookingRequestDTOBuilder requestStatus(RequestStatus requestStatus) {
            this.requestStatus = requestStatus;
            return this;
        }

        public BookingRequestDTO build() {
            return new BookingRequestDTO(id, userId, startsAt, endsAt, requestItems, requestStatus, requestDate);
        }
    }
}
