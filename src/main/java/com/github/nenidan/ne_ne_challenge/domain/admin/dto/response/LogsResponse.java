package com.github.nenidan.ne_ne_challenge.domain.admin.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public abstract class LogsResponse {
    private String type;
    private LocalDateTime createdAt;

    public LogsResponse(String type, LocalDateTime createdAt) {
        this.type = type;
        this.createdAt = createdAt;
    }
}
