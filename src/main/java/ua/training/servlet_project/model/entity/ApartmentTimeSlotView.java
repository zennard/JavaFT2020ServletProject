package ua.training.servlet_project.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ApartmentTimeSlotView {
    private Long id;
    private Long slotId;
    private BigDecimal price;
    private Integer bedsCount;
    private RoomType type;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private RoomStatus status;

    public ApartmentTimeSlotView() {
    }

    public ApartmentTimeSlotView(Long id, Long slotId, BigDecimal price, Integer bedsCount, RoomType type, LocalDateTime startsAt, LocalDateTime endsAt, RoomStatus status) {
        this.id = id;
        this.slotId = slotId;
        this.price = price;
        this.bedsCount = bedsCount;
        this.type = type;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getSlotId() {
        return slotId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getBedsCount() {
        return bedsCount;
    }

    public RoomType getType() {
        return type;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public static ApartmentTimeSlotViewBuilder builder() {
        return new ApartmentTimeSlotViewBuilder();
    }

    public static class ApartmentTimeSlotViewBuilder {
        private Long id;
        private Long slotId;
        private BigDecimal price;
        private Integer bedsCount;
        private RoomType type;
        private LocalDateTime startsAt;
        private LocalDateTime endsAt;
        private RoomStatus status;

        ApartmentTimeSlotViewBuilder() {
        }

        public ApartmentTimeSlotViewBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ApartmentTimeSlotViewBuilder slotId(Long slotId) {
            this.slotId = slotId;
            return this;
        }

        public ApartmentTimeSlotViewBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ApartmentTimeSlotViewBuilder bedsCount(Integer bedsCount) {
            this.bedsCount = bedsCount;
            return this;
        }

        public ApartmentTimeSlotViewBuilder type(RoomType type) {
            this.type = type;
            return this;
        }

        public ApartmentTimeSlotViewBuilder startsAt(LocalDateTime startsAt) {
            this.startsAt = startsAt;
            return this;
        }

        public ApartmentTimeSlotViewBuilder endsAt(LocalDateTime endsAt) {
            this.endsAt = endsAt;
            return this;
        }

        public ApartmentTimeSlotViewBuilder status(RoomStatus status) {
            this.status = status;
            return this;
        }

        public ApartmentTimeSlotView build() {
            return new ApartmentTimeSlotView(id, slotId, price, bedsCount, type, startsAt, endsAt, status);
        }
    }
}
