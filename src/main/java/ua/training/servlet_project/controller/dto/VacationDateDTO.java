package ua.training.servlet_project.controller.dto;

import java.time.LocalDate;

public class VacationDateDTO {
    private LocalDate startsAt;
    private LocalDate endsAt;

    public VacationDateDTO() {
    }

    public VacationDateDTO(LocalDate startsAt, LocalDate endsAt) {
        this.startsAt = startsAt;
        this.endsAt = endsAt;
    }

    public LocalDate getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDate startsAt) {
        this.startsAt = startsAt;
    }

    public LocalDate getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(LocalDate endsAt) {
        this.endsAt = endsAt;
    }

    @Override
    public String toString() {
        return "VacationDateDTO{" +
                "startsAt=" + startsAt +
                ", endsAt=" + endsAt +
                '}';
    }
}

