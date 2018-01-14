package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

public class Stage {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;

    public Stage(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
        Objects.requireNonNull(startYear, "startYear must not be null");
        Objects.requireNonNull(startMonth, "startMonth must not be null");
        Objects.requireNonNull(endYear, "endYear must not be null");
        Objects.requireNonNull(endMonth, "endMonth must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.startDate = DateUtil.of(startYear, startMonth);
        this.endDate = DateUtil.of(endYear, endMonth);
        this.title = title;
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Stage{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stage stage = (Stage) o;

        if (!startDate.equals(stage.startDate)) return false;
        if (!endDate.equals(stage.endDate)) return false;
        if (!title.equals(stage.title)) return false;
        return description != null ? description.equals(stage.description) : stage.description == null;
    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
