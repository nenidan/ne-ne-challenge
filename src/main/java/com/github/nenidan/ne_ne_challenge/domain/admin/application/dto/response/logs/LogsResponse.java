package com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class LogsResponse {
    private String type;
    private LocalDateTime createdAt;

}
