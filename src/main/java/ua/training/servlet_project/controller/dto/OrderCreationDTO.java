package ua.training.servlet_project.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderCreationDTO {
    private String userEmail;
    private LocalDateTime orderDate;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private List<OrderItemDTO> orderItems;

    public OrderCreationDTO() {
    }

    public OrderCreationDTO(String userEmail, LocalDateTime orderDate, LocalDateTime startsAt, LocalDateTime endsAt, List<OrderItemDTO> orderItems) {
        this.userEmail = userEmail;
        this.orderDate = orderDate;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.orderItems = orderItems;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "OrderCreationDTO{" +
                "userEmail='" + userEmail + '\'' +
                ", orderDate=" + orderDate +
                ", startsAt=" + startsAt +
                ", endsAt=" + endsAt +
                ", orderItems=" + orderItems +
                '}';
    }

    public static OrderCreationDTOBuilder builder() {
        return new OrderCreationDTOBuilder();
    }

    public static class OrderCreationDTOBuilder {
        private String userEmail;
        private LocalDateTime orderDate;
        private LocalDateTime startsAt;
        private LocalDateTime endsAt;
        private List<OrderItemDTO> orderItems;

        OrderCreationDTOBuilder() {
        }

        public OrderCreationDTOBuilder userEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public OrderCreationDTOBuilder orderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public OrderCreationDTOBuilder startsAt(LocalDateTime startsAt) {
            this.startsAt = startsAt;
            return this;
        }

        public OrderCreationDTOBuilder endsAt(LocalDateTime endsAt) {
            this.endsAt = endsAt;
            return this;
        }

        public OrderCreationDTOBuilder orderItems(List<OrderItemDTO> orderItems) {
            this.orderItems = orderItems;
            return this;
        }


        public OrderCreationDTO build() {
            return new OrderCreationDTO(userEmail, orderDate, startsAt, endsAt, orderItems);
        }
    }
}
