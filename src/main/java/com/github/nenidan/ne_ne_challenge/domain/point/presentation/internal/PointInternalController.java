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

import com.github.nenidan.ne_ne_challenge.domain.point.application.PointService;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.request.PointAmountRequest;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.request.PointChargeRequest;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.request.PointRefundRequest;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.response.PointBalanceResponse;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.response.PointHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.mapper.PointPresentationMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
public class PointInternalController {

    private final PointService pointService;

    /**
     * 포인트 지갑 생성 API
     * 회원가입 시 사용자의 포인트 지갑을 생성합니다.
     * @param userId 사용자 ID
     * @return void
     */
    @PostMapping("/points/{userId}/wallet")
    public ResponseEntity<Void> createPointWallet(@PathVariable Long userId) {

        pointService.createPointWallet(userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 포인트 충전 API
     * 결제 성공 시 사용자의 포인트 지갑에 포인트를 충전합니다.
     * @param userId 사용자 ID
     * @param request (amount, reason, orderId)
     * @return void
     */
    @PostMapping("/points/{userId}/charge")
    public ResponseEntity<Void> chargePoint(@PathVariable Long userId, @RequestBody PointChargeRequest request) {

        pointService.charge(userId, PointPresentationMapper.toPointChargeCommand(request));

        return ResponseEntity.ok().build();
    }

    /**
     * 포인트 잔액 조회 API
     * 특정 사용자의 현재 포인트 잔액을 조회합니다.
     * @param userId 사용자 ID
     * @return 포인트 잔액
     */
    @GetMapping("/points/{userId}")
    public ResponseEntity<PointBalanceResponse> getBalance(@PathVariable Long userId) {

        PointBalanceResponse pointBalanceResponse = PointPresentationMapper.toPointBalanceResponse(
            pointService.getBalance(userId));

        return ResponseEntity.ok().body(pointBalanceResponse);
    }

    /**
     * 포인트 증가 API
     * 챌린지 보상, 상점 결제 취소 등으로 사용자의 포인트를 증가시킵니다.
     * @param userId 사용자 ID
     * @param request 증가 정보 (amount: 증가할 포인트, reason: 증가 사유)
     * @return void
     */
    @PostMapping("/points/{userId}/increase")
    public ResponseEntity<Void> increasePoints(@PathVariable Long userId, @RequestBody PointAmountRequest request) {

        pointService.increase(userId, PointPresentationMapper.toPointAmountCommand(request));

        return ResponseEntity.ok().build();
    }

    /**
     * 포인트 차감 API
     * 상품 구매, 챌린지 참가비 등으로 사용자의 포인트를 차감합니다. (FIFO 방식)
     * @param userId 사용자 ID
     * @param request 차감 정보 (amount: 차감할 포인트, reason: 차감 사유)
     * @return void
     */
    @PostMapping("/points/{userId}/decrease")
    public ResponseEntity<Void> decreasePoints(@PathVariable Long userId, @RequestBody PointAmountRequest request) {

        pointService.decrease(userId, PointPresentationMapper.toPointAmountCommand(request));

        return ResponseEntity.ok().build();
    }

    /**
     * 포인트 취소 API
     * 결제 취소 시 해당 주문으로 충전된 포인트를 취소 처리합니다.
     * @param orderId 취소할 주문 ID
     * @return 200 void
     */
    @DeleteMapping("/points/{orderId}")
    public ResponseEntity<Void> cancelPoint(@PathVariable String orderId) {

        pointService.cancelPoint(orderId);
        return ResponseEntity.ok().build();
    }

    /**
     * 대량 포인트 환불 API
     * 챌린지 참가비 환불 등 여러 사용자에게 동시에 포인트를 지급합니다.
     * @param request 환불 정보 (userList: 대상 사용자 목록, amount: 환불할 포인트)
     * @return void
     */
    @PostMapping("/points/refund")
    public ResponseEntity<Void> refundPoints(@RequestBody PointRefundRequest request) {

        pointService.refundPoints(PointPresentationMapper.toPointRefundCommand(request));

        return ResponseEntity.ok().build();
    }

    /**
     * 포인트 거래 통계 조회 API
     * 전체 포인트 거래 내역을 조회합니다. (관리자/모니터링 용도)
     * @return 포인트 거래 내역 목록
     */
    @GetMapping("/statistics/pointTransactions")
    public List<PointHistoryResponse> getAllPointTransactions() {

        List<PointHistoryResponse> pointHistoryResponseList = pointService.getAllPointTransactions().stream()
            .map(PointPresentationMapper::toPointHistoryResponse)
            .toList();

        return pointHistoryResponseList;
    }
}
