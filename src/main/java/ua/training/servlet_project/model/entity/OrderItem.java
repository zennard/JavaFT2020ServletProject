package ua.training.servlet_project.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderItem {
    private Long id;
    private Apartment apartment;
    private Order order;
    private ApartmentTimetable schedule;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private BigDecimal price;
    private Integer amount;

    public OrderItem() {
    }

    public OrderItem(Long id, Apartment apartment, Order order, ApartmentTimetable schedule, LocalDateTime startsAt, LocalDateTime endsAt, BigDecimal price, Integer amount) {
        this.id = id;
        this.apartment = apartment;
        this.order = order;
        this.schedule = schedule;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.price = price;
        this.amount = amount;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ApartmentTimetable getSchedule() {
        return schedule;
    }

    public void setSchedule(ApartmentTimetable schedule) {
        this.schedule = schedule;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", apartment=" + apartment +
                ", order=" + order +
                ", schedule=" + schedule +
                ", startsAt=" + startsAt +
                ", endsAt=" + endsAt +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }

    public static OrderItemBuilder builder() {
        return new OrderItemBuilder();
    }

    public static class OrderItemBuilder {
        private Long id;
        private Apartment apartment;
        private Order order;
        private ApartmentTimetable schedule;
        private LocalDateTime startsAt;
        private LocalDateTime endsAt;
        private BigDecimal price;
        private Integer amount;

        OrderItemBuilder() {
        }

        public OrderItemBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderItemBuilder apartment(Apartment apartment) {
            this.apartment = apartment;
            return this;
        }

        public OrderItemBuilder order(Order order) {
            this.order = order;
            return this;
        }

        public OrderItemBuilder schedule(ApartmentTimetable schedule) {
            this.schedule = schedule;
            return this;
        }

        public OrderItemBuilder startsAt(LocalDateTime startsAt) {
            this.startsAt = startsAt;
            return this;
        }

        public OrderItemBuilder endsAt(LocalDateTime endsAt) {
            this.endsAt = endsAt;
            return this;
        }

        public OrderItemBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public OrderItemBuilder amount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(id, apartment, order, schedule, startsAt, endsAt, price, amount);
        }
    }
}
