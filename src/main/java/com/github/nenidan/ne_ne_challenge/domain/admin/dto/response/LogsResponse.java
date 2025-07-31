package com.github.nenidan.ne_ne_challenge.domain.admin.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class LogsResponse {
    private String type;
    private LocalDateTime createdAt;

    public LogsResponse(String type, LocalDateTime createdAt) {
        this.type = type;
        this.createdAt = createdAt;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
