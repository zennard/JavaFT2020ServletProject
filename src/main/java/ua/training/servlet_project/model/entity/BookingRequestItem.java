package ua.training.servlet_project.model.entity;

public class BookingRequestItem {
    private Long id;
    private BookingRequest bookingRequest;
    private Integer bedsCount;
    private RoomType type;

    public BookingRequestItem() {
    }

    public BookingRequestItem(Long id, BookingRequest bookingRequest, Integer bedsCount, RoomType type) {
        this.id = id;
        this.bookingRequest = bookingRequest;
        this.bedsCount = bedsCount;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookingRequest getBookingRequest() {
        return bookingRequest;
    }

    public void setBookingRequest(BookingRequest bookingRequest) {
        this.bookingRequest = bookingRequest;
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
        return "BookingRequestItem{" +
                "id=" + id +
                ", bedsCount=" + bedsCount +
                ", type=" + type +
                '}';
    }

    public static BookingRequestItemBuilder builder() {
        return new BookingRequestItemBuilder();
    }

    public static class BookingRequestItemBuilder {
        private Long id;
        private BookingRequest bookingRequest;
        private Integer bedsCount;
        private RoomType type;

        BookingRequestItemBuilder() {
        }

        public BookingRequestItemBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BookingRequestItemBuilder bookingRequest(BookingRequest bookingRequest) {
            this.bookingRequest = bookingRequest;
            return this;
        }

        public BookingRequestItemBuilder bedsCount(Integer bedsCount) {
            this.bedsCount = bedsCount;
            return this;
        }

        public BookingRequestItemBuilder type(RoomType type) {
            this.type = type;
            return this;
        }

        public BookingRequestItem build() {
            return new BookingRequestItem(id, bookingRequest, bedsCount, type);
        }
    }
}
