package ua.training.servlet_project.model.entity;

import java.math.BigDecimal;
import java.util.List;

public class Apartment {
    private Long id;
    private BigDecimal price;
    private Integer bedsCount;
    private ua.training.servlet_project.model.entity.RoomType type;
    private String description;
    private Boolean isAvailable;
    private List<ApartmentTimetable> schedule;

    public Apartment() {
    }

    public Apartment(Long id, BigDecimal price, Integer bedsCount, RoomType type, String description, Boolean isAvailable, List<ApartmentTimetable> schedule) {
        this.id = id;
        this.price = price;
        this.bedsCount = bedsCount;
        this.type = type;
        this.description = description;
        this.isAvailable = isAvailable;
        this.schedule = schedule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getBedsCount() {
        return bedsCount;
    }

    public void setBedsCount(Integer bedsCount) {
        this.bedsCount = bedsCount;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public List<ApartmentTimetable> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ApartmentTimetable> schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", price=" + price +
                ", bedsCount=" + bedsCount +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }

    public static ApartmentBuilder builder() {
        return new ApartmentBuilder();
    }

    public static class ApartmentBuilder {
        private Long id;
        private BigDecimal price;
        private Integer bedsCount;
        private RoomType type;
        private String description;
        private Boolean isAvailable;
        private List<ApartmentTimetable> schedule;

        ApartmentBuilder() {
        }

        public ApartmentBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ApartmentBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ApartmentBuilder bedsCount(Integer bedsCount) {
            this.bedsCount = bedsCount;
            return this;
        }

        public ApartmentBuilder type(RoomType type) {
            this.type = type;
            return this;
        }

        public ApartmentBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ApartmentBuilder isAvailable(Boolean isAvailable) {
            this.isAvailable = isAvailable;
            return this;
        }

        public ApartmentBuilder schedule(List<ApartmentTimetable> schedule) {
            this.schedule = schedule;
            return this;
        }

        public Apartment build() {
            return new Apartment(id, price, bedsCount, type, description, isAvailable, schedule);
        }
    }

}
