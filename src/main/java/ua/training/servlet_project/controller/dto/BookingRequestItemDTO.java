package ua.training.servlet_project.controller.dto;

import ua.training.servlet_project.model.entity.RoomType;

public class BookingRequestItemDTO {
    private Integer bedsCount;
    private RoomType type;

    public BookingRequestItemDTO() {
    }

    public BookingRequestItemDTO(Integer bedsCount, RoomType type) {
        this.bedsCount = bedsCount;
        this.type = type;
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

    @Override
    public String toString() {
        return "BookingRequestItemDTO{" +
                "bedsCount=" + bedsCount +
                ", type=" + type +
                '}';
    }

    public static BookingRequestItemDTOBuilder builder() {
        return new BookingRequestItemDTOBuilder();
    }

    public static class BookingRequestItemDTOBuilder {
        private Integer bedsCount;
        private RoomType type;

        BookingRequestItemDTOBuilder() {
        }

        public BookingRequestItemDTOBuilder bedsCount(Integer bedsCount) {
            this.bedsCount = bedsCount;
            return this;
        }

        public BookingRequestItemDTOBuilder type(RoomType type) {
            this.type = type;
            return this;
        }

        public BookingRequestItemDTO build() {
            return new BookingRequestItemDTO(bedsCount, type);
        }
    }
}
