package ua.training.servlet_project.controller.dto;

import java.math.BigDecimal;

public class OrderItemDTO {
    private Long apartmentId;
    private Integer amount;
    private BigDecimal price;

    public OrderItemDTO() {
    }

    public OrderItemDTO(Long apartmentId, Integer amount, BigDecimal price) {
        this.apartmentId = apartmentId;
        this.amount = amount;
        this.price = price;
    }

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "apartmentId=" + apartmentId +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }

    public static OrderItemDTOBuilder builder() {
        return new OrderItemDTOBuilder();
    }

    public static class OrderItemDTOBuilder {
        private Long apartmentId;
        private Integer amount;
        private BigDecimal price;

        OrderItemDTOBuilder() {
        }

        public OrderItemDTOBuilder apartmentId(Long apartmentId) {
            this.apartmentId = apartmentId;
            return this;
        }

        public OrderItemDTOBuilder amount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public OrderItemDTOBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public OrderItemDTO build() {
            return new OrderItemDTO(apartmentId, amount, price);
        }
    }
}
