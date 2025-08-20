package com.github.nenidan.ne_ne_challenge.domain.point.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PointAmountCommand {

    int amount;
    String reason;
}
