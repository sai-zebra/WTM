package com.workcloud.wtm.feeds.application.dto;

import java.time.OffsetDateTime;

/**
 * Data Transfer Object for Feed.
 * Used to transfer data between layers without exposing domain entities.
 */
public class FeedDto {
    private String id;
    private String title;
    private String message;
    private String status;
    private OffsetDateTime createdAt;

    public FeedDto() {
    }

    public FeedDto(String id, String title, String message, String status, OffsetDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
