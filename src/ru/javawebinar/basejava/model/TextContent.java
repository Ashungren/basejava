package ru.javawebinar.basejava.model;

import java.util.Objects;

public class TextContent extends Content {
    private String content;

    public TextContent(String content) {
        Objects.requireNonNull(content, "content must not be null");
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}