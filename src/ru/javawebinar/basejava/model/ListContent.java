package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class ListContent {
    private List<String> content;

    public ListContent(List<String> content) {
        Objects.requireNonNull(content, "content must not be null");
        this.content = content;
    }

    public List<String> getContent() {
        return content;
    }
}