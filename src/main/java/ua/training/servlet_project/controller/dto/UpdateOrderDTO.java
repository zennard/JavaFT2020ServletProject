package ua.training.servlet_project.controller.dto;

import ua.training.servlet_project.model.entity.OrderStatus;

public class UpdateOrderDTO {
    private Long id;
    private OrderStatus status;

    public UpdateOrderDTO() {
    }

    public UpdateOrderDTO(Long id, OrderStatus status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UpdateOrderDTO{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }

    public static UpdateOrderDTOBuilder builder() {
        return new UpdateOrderDTOBuilder();
    }

    public static class UpdateOrderDTOBuilder {
        private Long id;
        private OrderStatus status;

        UpdateOrderDTOBuilder() {
        }

        public UpdateOrderDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UpdateOrderDTOBuilder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        public UpdateOrderDTO build() {
            return new UpdateOrderDTO(id, status);
        }
    }
}
