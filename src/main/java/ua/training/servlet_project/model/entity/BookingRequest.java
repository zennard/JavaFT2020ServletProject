package ua.training.servlet_project.model.entity;

import java.time.LocalDateTime;
import java.util.List;

public class BookingRequest {
    private Long id;
    private User user;
    private LocalDateTime requestDate;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private List<BookingRequestItem> requestItems;

    public BookingRequest() {
    }

    public BookingRequest(Long id, User user, LocalDateTime requestDate, LocalDateTime startsAt, LocalDateTime endsAt, List<BookingRequestItem> requestItems) {
        this.id = id;
        this.user = user;
        this.requestDate = requestDate;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.requestItems = requestItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
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

    public List<BookingRequestItem> getRequestItems() {
        return requestItems;
    }

    public void setRequestItems(List<BookingRequestItem> requestItems) {
        this.requestItems = requestItems;
    }

    @Override
    public String toString() {
        return "BookingRequest{" +
                "id=" + id +
                ", user=" + user +
                ", requestDate=" + requestDate +
                ", startsAt=" + startsAt +
                ", endsAt=" + endsAt +
                '}';
    }

    public static BookingRequestBuilder builder() {
        return new BookingRequestBuilder();
    }

    public static class BookingRequestBuilder {
        private Long id;
        private User user;
        private LocalDateTime requestDate;
        private LocalDateTime startsAt;
        private LocalDateTime endsAt;
        private List<BookingRequestItem> requestItems;

        BookingRequestBuilder() {
        }

        public BookingRequestBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BookingRequestBuilder user(User user) {
            this.user = user;
            return this;
        }

        public BookingRequestBuilder requestDate(LocalDateTime requestDate) {
            this.requestDate = requestDate;
            return this;
        }

        public BookingRequestBuilder startsAt(LocalDateTime startsAt) {
            this.startsAt = startsAt;
            return this;
        }

        public BookingRequestBuilder endsAt(LocalDateTime endsAt) {
            this.endsAt = endsAt;
            return this;
        }

        public BookingRequestBuilder requestItems(List<BookingRequestItem> requestItems) {
            this.requestItems = requestItems;
            return this;
        }

        public BookingRequest build() {
            return new BookingRequest(id, user, requestDate, startsAt, endsAt, requestItems);
        }
    }
}
