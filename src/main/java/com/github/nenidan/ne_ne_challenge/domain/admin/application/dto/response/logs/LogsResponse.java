package com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public abstract class LogsResponse {
    private String type;
    private LocalDateTime createdAt;

}
