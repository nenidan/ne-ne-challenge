package com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs;

import java.time.LocalDateTime;

import lombok.Getter;


@Getter
public class PointHistoryResponse extends LogsResponse{
    private Long id;

    private Long userId;

    private int amount;

    private String reason;

    private String description;

    public PointHistoryResponse(String type, LocalDateTime createdAt, Long id, Long userId, int amount, String reason, String description) {
        super(type, createdAt);

        this.id = id;

        this.userId = userId;

        this.amount = amount;

        this.reason = reason;

        this.description = description;
    }
}
