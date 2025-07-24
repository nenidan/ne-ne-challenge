package com.github.nenidan.ne_ne_challenge.domain.point.dto.response;

import com.github.nenidan.ne_ne_challenge.domain.point.entity.PointWallet;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PointBalanceResponse {

    private int balance;

    public static PointBalanceResponse from(PointWallet pointWallet) {
        return new PointBalanceResponse(
            pointWallet.getBalance()
        );
    }
}
