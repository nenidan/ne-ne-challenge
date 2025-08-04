package com.github.nenidan.ne_ne_challenge.domain.point.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointAmountCommand {

    int amount;
    String reason;
}
