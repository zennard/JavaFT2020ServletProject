package ua.training.servlet_project.controller.dto;

import java.time.LocalDate;

public class DateDTO {
    Integer prevYear;
    Integer nextYear;
    LocalDate checkIn;
    LocalDate checkOut;

    public DateDTO() {
    }

    public DateDTO(Integer prevYear, Integer nextYear, LocalDate checkIn, LocalDate checkOut) {
        this.prevYear = prevYear;
        this.nextYear = nextYear;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Integer getPrevYear() {
        return prevYear;
    }

    public void setPrevYear(Integer prevYear) {
        this.prevYear = prevYear;
    }

    public Integer getNextYear() {
        return nextYear;
    }

    public void setNextYear(Integer nextYear) {
        this.nextYear = nextYear;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return "DateDTO{" +
                "prevYear=" + prevYear +
                ", nextYear=" + nextYear +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                '}';
    }

    public static DateDTOBuilder builder() {
        return new DateDTOBuilder();
    }

    public static class DateDTOBuilder {
        Integer prevYear;
        Integer nextYear;
        LocalDate checkIn;
        LocalDate checkOut;

        DateDTOBuilder() {
        }

        public DateDTOBuilder prevYear(Integer prevYear) {
            this.prevYear = prevYear;
            return this;
        }

        public DateDTOBuilder nextYear(Integer nextYear) {
            this.nextYear = nextYear;
            return this;
        }

        public DateDTOBuilder checkIn(LocalDate checkIn) {
            this.checkIn = checkIn;
            return this;
        }

        public DateDTOBuilder checkOut(LocalDate checkOut) {
            this.checkOut = checkOut;
            return this;
        }

        public DateDTO build() {
            return new DateDTO(prevYear, nextYear, checkIn, checkOut);
        }
    }
}
