package com.github.nenidan.ne_ne_challenge.domain.admin.presentation.dto.response.logs;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentHistoryResponse extends LogsResponse {
    private String itemName;
    private int price;
    private String user;

    public PaymentHistoryResponse(String type, LocalDateTime createdAt, String itemName, int price, String user) {
        super(type, createdAt);
        this.itemName = itemName;
        this.price = price;
        this.user = user;
    }

}
