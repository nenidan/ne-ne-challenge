package com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PointAmountRequest {

    private int amount;
    private String reason;
}

