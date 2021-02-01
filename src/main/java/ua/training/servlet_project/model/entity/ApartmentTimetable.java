package ua.training.servlet_project.model.entity;

import java.time.LocalDateTime;


public class ApartmentTimetable {
    private Long id;
    private Apartment apartment;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private RoomStatus status;

    public ApartmentTimetable() {
    }

    public ApartmentTimetable(Long id, Apartment apartment, LocalDateTime startsAt, LocalDateTime endsAt, RoomStatus status) {
        this.id = id;
        this.apartment = apartment;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
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

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ApartmentTimetable{" +
                "id=" + id +
                ", startsAt=" + startsAt +
                ", endsAt=" + endsAt +
                ", status=" + status +
                '}';
    }

    public static ApartmentTimetableBuilder builder() {
        return new ApartmentTimetableBuilder();
    }

    public static class ApartmentTimetableBuilder {
        private Long id;
        private Apartment apartment;
        private LocalDateTime startsAt;
        private LocalDateTime endsAt;
        private RoomStatus status;

        ApartmentTimetableBuilder() {
        }

        public ApartmentTimetableBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ApartmentTimetableBuilder apartment(Apartment apartment) {
            this.apartment = apartment;
            return this;
        }

        public ApartmentTimetableBuilder startsAt(LocalDateTime startsAt) {
            this.startsAt = startsAt;
            return this;
        }

        public ApartmentTimetableBuilder endsAt(LocalDateTime endsAt) {
            this.endsAt = endsAt;
            return this;
        }

        public ApartmentTimetableBuilder status(RoomStatus status) {
            this.status = status;
            return this;
        }

        public ApartmentTimetable build() {
            return new ApartmentTimetable(id, apartment, startsAt, endsAt, status);
        }
    }
}
