package ua.training.servlet_project.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long id;
    private String userEmail;
    private LocalDateTime orderDate;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private List<OrderItemDTO> orderItems;

    public OrderDTO() {
    }

    public OrderDTO(Long id, String userEmail, LocalDateTime orderDate, LocalDateTime startsAt, LocalDateTime endsAt, List<OrderItemDTO> orderItems) {
        this.id = id;
        this.userEmail = userEmail;
        this.orderDate = orderDate;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "OrderDTO{" +
                "id=" + id +
                ", userEmail='" + userEmail + '\'' +
                ", orderDate=" + orderDate +
                ", startsAt=" + startsAt +
                ", endsAt=" + endsAt +
                '}';
    }

    public static OrderDTOBuilder builder() {
        return new OrderDTOBuilder();
    }

    public static class OrderDTOBuilder {
        private Long id;
        private String userEmail;
        private LocalDateTime orderDate;
        private LocalDateTime startsAt;
        private LocalDateTime endsAt;
        private List<OrderItemDTO> orderItems;

        OrderDTOBuilder() {
        }

        public OrderDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrderDTOBuilder userEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public OrderDTOBuilder orderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public OrderDTOBuilder startsAt(LocalDateTime startsAt) {
            this.startsAt = startsAt;
            return this;
        }

        public OrderDTOBuilder endsAt(LocalDateTime endsAt) {
            this.endsAt = endsAt;
            return this;
        }

        public OrderDTOBuilder orderItems(List<OrderItemDTO> orderItems) {
            this.orderItems = orderItems;
            return this;
        }


        public OrderDTO build() {
            return new OrderDTO(id, userEmail, orderDate, startsAt, endsAt, orderItems);
        }
    }
}
