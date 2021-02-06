package ua.training.servlet_project.controller.dto;

import java.time.LocalDate;
import java.util.List;

public class BookingRequestCreationDTO {
    private Long userId;
    private LocalDate startsAt;
    private LocalDate endsAt;
    private List<BookingRequestItemDTO> requestItems;

    public BookingRequestCreationDTO() {
    }

    public BookingRequestCreationDTO(Long userId, LocalDate startsAt, LocalDate endsAt, List<BookingRequestItemDTO> requestItems) {
        this.userId = userId;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.requestItems = requestItems;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDate startsAt) {
        this.startsAt = startsAt;
    }


    public LocalDate getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(LocalDate endsAt) {
        this.endsAt = endsAt;
    }

    public List<BookingRequestItemDTO> getRequestItems() {
        return requestItems;
    }

    public void setRequestItems(List<BookingRequestItemDTO> requestItems) {
        this.requestItems = requestItems;
    }

    @Override
    public String toString() {
        return "BookingRequestDTO{" +
                "userId=" + userId +
                ", startsAt=" + startsAt +
                ", endsAt=" + endsAt +
                ", requestItems=" + requestItems +
                '}';
    }

    public static BookingRequestCreationDTOBuilder builder() {
        return new BookingRequestCreationDTOBuilder();
    }

    public static class BookingRequestCreationDTOBuilder {
        private Long userId;
        private LocalDate startsAt;
        private LocalDate endsAt;
        private List<BookingRequestItemDTO> requestItems;

        BookingRequestCreationDTOBuilder() {
        }

        public BookingRequestCreationDTOBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public BookingRequestCreationDTOBuilder startsAt(LocalDate startsAt) {
            this.startsAt = startsAt;
            return this;
        }

        public BookingRequestCreationDTOBuilder endsAt(LocalDate endsAt) {
            this.endsAt = endsAt;
            return this;
        }

        public BookingRequestCreationDTOBuilder requestItems(List<BookingRequestItemDTO> requestItems) {
            this.requestItems = requestItems;
            return this;
        }

        public BookingRequestCreationDTO build() {
            return new BookingRequestCreationDTO(userId, startsAt, endsAt, requestItems);
        }
    }
}
