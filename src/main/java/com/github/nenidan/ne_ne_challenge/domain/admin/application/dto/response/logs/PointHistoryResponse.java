package com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs;

import com.github.nenidan.ne_ne_challenge.domain.point.domain.type.PointReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


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
