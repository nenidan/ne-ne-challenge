package com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PointWalletResult {

    private Long id;
    private Long userId;
    private int balance;
}
