package com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointAmountRequest {

    int amount;
    String reason;
}

