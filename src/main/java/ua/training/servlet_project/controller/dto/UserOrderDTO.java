package ua.training.servlet_project.controller.dto;

import ua.training.servlet_project.model.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class UserOrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private OrderStatus status;
    private List<OrderItemDTO> orderItems;

    public UserOrderDTO() {
    }

    public UserOrderDTO(Long id, LocalDateTime orderDate, LocalDateTime startsAt, LocalDateTime endsAt, OrderStatus status, List<OrderItemDTO> orderItems) {
        this.id = id;
        this.orderDate = orderDate;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.status = status;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "UserOrderDTO{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", startsAt=" + startsAt +
                ", endsAt=" + endsAt +
                ", status=" + status +
                '}';
    }

    public static UserOrderDTOBuilder builder() {
        return new UserOrderDTOBuilder();
    }

    public static class UserOrderDTOBuilder {
        private Long id;
        private LocalDateTime orderDate;
        private LocalDateTime startsAt;
        private LocalDateTime endsAt;
        private OrderStatus status;
        private List<OrderItemDTO> orderItems;

        UserOrderDTOBuilder() {
        }

        public UserOrderDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserOrderDTOBuilder orderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public UserOrderDTOBuilder startsAt(LocalDateTime startsAt) {
            this.startsAt = startsAt;
            return this;
        }

        public UserOrderDTOBuilder endsAt(LocalDateTime endsAt) {
            this.endsAt = endsAt;
            return this;
        }

        public UserOrderDTOBuilder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        public UserOrderDTOBuilder orderItems(List<OrderItemDTO> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public UserOrderDTO build() {
            return new UserOrderDTO(id, orderDate, startsAt, endsAt, status, orderItems);
        }
    }
}
