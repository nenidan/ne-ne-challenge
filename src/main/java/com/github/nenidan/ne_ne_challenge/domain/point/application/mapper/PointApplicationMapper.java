package com.github.nenidan.ne_ne_challenge.domain.point.application.mapper;

import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.PointHistoryResult;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.PointWalletResult;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.PointTransaction;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.PointWallet;

public class PointApplicationMapper {

    public static PointWalletResult toPointWalletResult(PointWallet wallet) {
        return new PointWalletResult(
            wallet.getId(),
            wallet.getUserId(),
            wallet.getBalance()
        );
    }

    public static PointHistoryResult toPointHistoryResult(PointTransaction transaction) {
        return new PointHistoryResult(
            transaction.getId(),
            transaction.getAmount(),
            transaction.getReason(),
            transaction.getDescription()
        );
    }
}
