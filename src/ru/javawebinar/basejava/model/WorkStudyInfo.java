package ru.javawebinar.basejava.model;

import java.util.Objects;

public class WorkStudyInfo {
    private String homePage;
    private String startDate;
    private String finishDate;
    private String title;
    private String info;

    public WorkStudyInfo(String homePage, String startDate, String finishDate, String title, String info) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(finishDate, "finishDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.homePage = homePage;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.title = title;
        this.info = info;
    }
}