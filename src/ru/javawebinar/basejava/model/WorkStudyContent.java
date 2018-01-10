package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class WorkStudyContent extends Content {
    private List<WorkStudyInfo> content;

    public WorkStudyContent(List<WorkStudyInfo> content) {
        Objects.requireNonNull(content, "content must not be null");
        this.content = content;
    }

    public List<WorkStudyInfo> getContent() {
        return content;
    }
}