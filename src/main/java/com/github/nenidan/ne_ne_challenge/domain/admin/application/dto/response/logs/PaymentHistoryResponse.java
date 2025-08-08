package com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PaymentHistoryResponse extends LogsResponse {

    private Long id;
    private Long userId;
    private Integer amount;
    private String status; //.name() 필요
    private LocalDateTime requestedAt = null;
    private LocalDateTime canceledAt = null;
    private LocalDateTime failedAt = null;

    public PaymentHistoryResponse(String type, LocalDateTime createdAt,
                                  Long id, Long userId, Integer amount,
                                  String status, LocalDateTime requestedAt,
                                  LocalDateTime canceledAt, LocalDateTime failedAt) {
        super(type, createdAt);
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.status = status;
        this.requestedAt = requestedAt;
        this.canceledAt = canceledAt;
        this.failedAt = failedAt;
    }

}
