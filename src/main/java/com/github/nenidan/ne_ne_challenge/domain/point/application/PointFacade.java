package com.github.nenidan.ne_ne_challenge.domain.point.application;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.request.PointAmountCommand;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.request.PointChargeCommand;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.request.PointRefundCommand;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.PointBalanceResult;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.PointHistoryResult;
import com.github.nenidan.ne_ne_challenge.global.client.user.UserClient;
import com.github.nenidan.ne_ne_challenge.global.client.user.dto.UserResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointFacade {

    private final UserClient userClient;
    private final PointService pointService;

    public PointBalanceResult getMyBalance(Long userId) {

        UserResponse user = userClient.getUserById(userId);

        return pointService.getBalance(user.getId());
    }

    public CursorResponse<PointHistoryResult, Long> searchMyPointHistory(Long userId, Long cursor, int size,
        String reason, LocalDate startDate, LocalDate endDate) {

        UserResponse user = userClient.getUserById(userId);

        return pointService.searchMyPointHistory(user.getId(), cursor, size, reason, startDate, endDate);
    }

    public void createPointWallet(Long userId) {
        pointService.createPointWallet(userId);
    }

    public void charge(Long userId, PointChargeCommand pointChargeCommand) {
        pointService.charge(userId, pointChargeCommand);
    }

    public void increase(Long userId, PointAmountCommand pointAmountCommand) {

        userClient.getUserById(userId);

        pointService.increase(userId, pointAmountCommand);
    }

    public void decrease(Long userId, PointAmountCommand pointAmountCommand) {

        userClient.getUserById(userId);

        pointService.decrease(userId, pointAmountCommand);
    }

    public void cancelPoint(String orderId) {
        pointService.cancelPoint(orderId);
    }

    public void refundPoints(PointRefundCommand pointRefundCommand) {
        pointService.refundPoints(pointRefundCommand);
    }

    public List<PointHistoryResult> getAllPointTransactions() {
        return pointService.getAllPointTransactions();
    }
}
