package com.github.nenidan.ne_ne_challenge.domain.point.presentation.mapper;

import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.request.PointAmountCommand;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.request.PointChargeCommand;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.request.PointRefundCommand;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.PointBalanceResult;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.PointHistoryResult;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.request.PointAmountRequest;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.request.PointChargeRequest;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.request.PointRefundRequest;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.response.PointBalanceResponse;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.response.PointHistoryResponse;

public class PointPresentationMapper {

    public static PointBalanceResponse toPointBalanceResponse(PointBalanceResult result) {
        return new PointBalanceResponse(
            result.getBalance()
        );
    }

    public static PointHistoryResponse toPointHistoryResponse(PointHistoryResult result) {
        return new PointHistoryResponse(
            result.getPointTransactionId(),
            result.getAmount(),
            result.getReason(),
            result.getDescription()
        );
    }

    public static PointChargeCommand toPointChargeCommand(PointChargeRequest request) {
        return new PointChargeCommand(
            request.getAmount(),
            request.getReason(),
            request.getOrderId()
        );
    }

    public static PointAmountCommand toPointAmountCommand(PointAmountRequest request) {
        return new PointAmountCommand(
            request.getAmount(),
            request.getReason()
        );
    }

    public static PointRefundCommand toPointRefundCommand(PointRefundRequest request) {
        return new PointRefundCommand(
            request.getUserList(),
            request.getAmount()
        );
    }
}
