package com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PointClientCommand {

    private Long userId;
    private int amount;
    private String reason;
    private String description;
}
