package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PointDto {
    private Long pointTransactionId;

    private int amount;

    private String reason;

    private String description;
}
