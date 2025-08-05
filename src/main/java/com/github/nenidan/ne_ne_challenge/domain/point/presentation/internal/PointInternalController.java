package com.github.nenidan.ne_ne_challenge.domain.point.presentation.internal;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.point.application.PointFacade;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.request.PointAmountRequest;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.request.PointChargeRequest;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.request.PointRefundRequest;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.response.PointBalanceResponse;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.response.PointHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.mapper.PointPresentationMapper;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
public class PointInternalController {

    private final PointFacade pointFacade;

    @PostMapping("/points/{userId}/wallet")
    public ResponseEntity<Void> createPointWallet(@PathVariable Long userId) {

        pointFacade.createPointWallet(userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/points/{userId}/charge")
    public ResponseEntity<Void> chargePoint(@PathVariable Long userId, @RequestBody PointChargeRequest request) {

        pointFacade.charge(userId, PointPresentationMapper.toPointChargeCommand(request));

        return ResponseEntity.ok().build();
    }

    @GetMapping("/points/{userId}")
    public ResponseEntity<PointBalanceResponse> getMyBalance(@PathVariable Long userId) {

        PointBalanceResponse pointBalanceResponse = PointPresentationMapper.toPointBalanceResponse(
            pointFacade.getMyBalance(userId));

        return ResponseEntity.ok().body(pointBalanceResponse);
    }

    @PostMapping("/points/{userId}/increase")
    public ResponseEntity<Void> increasePoints(@PathVariable Long userId, @RequestBody PointAmountRequest request) {

        pointFacade.increase(userId, PointPresentationMapper.toPointAmountCommand(request));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/points/{userId}/decrease")
    public ResponseEntity<Void> decreasePoints(@PathVariable Long userId, @RequestBody PointAmountRequest request) {

        pointFacade.decrease(userId, PointPresentationMapper.toPointAmountCommand(request));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/points/{orderId}")
    public ResponseEntity<Void> cancelPoint(@PathVariable String orderId) {

        pointFacade.cancelPoint(orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/points/refund")
    public ResponseEntity<Void> refundPoints(@RequestBody PointRefundRequest request) {

        pointFacade.refundPoints(PointPresentationMapper.toPointRefundCommand(request));

        return ResponseEntity.ok().build();
    }

    @GetMapping("/statistics/pointTransactions")
    public ResponseEntity<ApiResponse<List<PointHistoryResponse>>> getAllPointTransactions() {

        List<PointHistoryResponse> pointHistoryResponseList = pointFacade.getAllPointTransactions().stream()
            .map(PointPresentationMapper::toPointHistoryResponse)
            .toList();

        return ApiResponse.success(
            HttpStatus.OK,
            "포인트 사용 이력을 조회하였습니다.",
            pointHistoryResponseList
        );
    }
}
