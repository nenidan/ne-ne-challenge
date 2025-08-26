package com.github.nenidan.ne_ne_challenge.domain.point.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.request.PointAmountCommand;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.request.PointChargeCommand;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.request.PointRefundCommand;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.request.PointSearchCommand;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.PointBalanceResult;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.PointHistoryResult;
import com.github.nenidan.ne_ne_challenge.domain.point.application.mapper.PointApplicationMapper;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.model.Point;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.model.PointTransaction;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.model.PointWallet;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.repository.PointRepository;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.service.PointDomainService;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.type.PointReason;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointException;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PointService {

    private final PointRepository pointRepository;
    private final PointDomainService pointDomainService;

    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
        this.pointDomainService = new PointDomainService(pointRepository);
    }

    // =============================== 외부용 ===============================

    // ============================= 나의 잔액 조회 =============================
    @Transactional(readOnly = true)
    public PointBalanceResult getBalance(Long userId) {

        PointWallet pointWallet = getPointWalletByUserId(userId);

        return new PointBalanceResult(pointWallet.getBalance());
    }

    // ============================= 나의 포인트 사용 이력 조회 =============================
    @Transactional(readOnly = true)
    public CursorResponse<PointHistoryResult, Long> searchMyPointHistory(Long userId, PointSearchCommand command) {

        LocalDateTime start = (command.getStartDate() != null) ? command.getStartDate().atStartOfDay() : null;
        LocalDateTime end = (command.getEndDate() != null) ? command.getEndDate().atTime(23, 59, 59) : null;
        PointReason pointReason = StringUtils.hasText(command.getReason()) ? PointReason.of(command.getReason()) : null;

        PointWallet pointWallet = getPointWalletByUserId(userId);

        List<PointHistoryResult> pointHistoryResultList = pointRepository.searchMyPointHistory(pointWallet.getId(), command.getCursor(), pointReason, start, end, command.getSize() + 1)
            .stream()
            .map(PointApplicationMapper::toPointHistoryResult)
            .toList();

        return CursorResponse.of(pointHistoryResultList, PointHistoryResult::getPointTransactionId, command.getSize());
    }


    // =============================== 내부용 ===============================

    // ============================= 회원 가입 시 포인트 지갑 생성 =============================
    @Transactional
    public void createPointWallet(Long userId) {
        boolean exists = pointRepository.existsByUserId(userId);
        if (exists) {
            throw new PointException(PointErrorCode.WALLET_ALREADY_EXISTS);
        }

        PointWallet pointWallet = PointWallet.createPointWallet(userId);
        pointRepository.save(pointWallet);
    }

    // ============================= 결제 후 포인트 충전 =============================
    @Transactional
    public void charge(Long userId, PointChargeCommand command) {

        PointReason pointReason = PointReason.of(command.getReason());

        PointWallet pointWallet = getPointWalletByUserId(userId);
        pointWallet.increase(command.getAmount());
        pointRepository.save(pointWallet);

        Point chargePoint = Point.createChargePoint(
            pointWallet,
            command.getAmount(),
            command.getOrderId()
        );
        pointRepository.save(chargePoint);

        PointTransaction pointTransaction = PointTransaction.createChargeTransaction(
            pointWallet,
            command.getAmount(),
            pointReason,
            pointReason.getDescription()
        );
        pointRepository.save(pointTransaction);
    }

    // ============================= 포인트 증가 =============================
    @Transactional
    public void increase(Long userId, PointAmountCommand pointAmountCommand) {

        // reason 검증
        PointReason pointReason = PointReason.of(pointAmountCommand.getReason());

        // 포인트 지갑 조회
        PointWallet pointWallet = getPointWalletByUserId(userId);

        // 포인트 증가
        pointWallet.increase(pointAmountCommand.getAmount());

        // 포인트 지갑 저장
        pointRepository.save(pointWallet);

        // 포인트 트랜잭션 생성
        PointTransaction pointTransaction = PointTransaction.createChargeTransaction(
            pointWallet,
            pointAmountCommand.getAmount(),
            pointReason,
            pointReason.getDescription()
        );

        pointRepository.save(pointTransaction);
    }

    // ============================= 포인트 감소 =============================
    @Transactional
    public void decrease(Long userId, PointAmountCommand pointAmountCommand) {

        // reason 검증
        PointReason pointReason = PointReason.of(pointAmountCommand.getReason());

        // 포인트 지갑 조회
        PointWallet pointWallet = getPointWalletByUserId(userId);

        // FIFO 로직을 사용한 포인트 감소 로직
        pointDomainService.usePointsWithFifo(pointWallet, pointAmountCommand.getAmount());

        // 포인트 트랜잭션 생성
        PointTransaction pointTransaction = PointTransaction.createUsageTransaction(
            pointWallet,
            pointAmountCommand.getAmount(),
            pointReason,
            pointReason.getDescription()
        );

        pointRepository.save(pointTransaction);
    }

    // ============================= 포인트 취소 =============================
    @Transactional
    public void cancelPoint(String orderId) {

        // 포인트 조회
        Point point = getPointByOrderId(orderId);
        point.cancel();

        PointWallet pointWallet = point.getPointWallet();
        pointWallet.decrease(point.getAmount());

        PointTransaction pointTransaction = PointTransaction.createUsageTransaction(
            pointWallet,
            point.getAmount(),
            PointReason.CHARGE_CANCEL,
            PointReason.CHARGE_CANCEL.getDescription()
        );
        pointRepository.save(pointTransaction);
    }

    // ============================= 포인트 환불 =============================
    @Transactional
    public void refundPoints(PointRefundCommand pointRefundCommand) {
        PointReason reason = PointReason.CHALLENGE_REFUND;

        for (Long userId : pointRefundCommand.getUserList()) {
            PointWallet pointWallet = getPointWalletByUserId(userId);
            pointWallet.increase(pointRefundCommand.getAmount());
            pointRepository.save(pointWallet);

            PointTransaction pointTransaction = PointTransaction.createChargeTransaction(
                pointWallet,
                pointRefundCommand.getAmount(),
                reason,
                reason.getDescription()
            );
            pointRepository.save(pointTransaction);
        }
    }

    // ============================= 포인트 이력 전체 조회(통계용) =============================
    @Transactional(readOnly = true)
    public List<PointHistoryResult> getAllPointTransactions() {
        return pointRepository.findAll().stream()
            .map(PointApplicationMapper::toPointHistoryResult)
            .toList();
    }

    // ============================= private 헬퍼 메서드 =============================
    private PointWallet getPointWalletByUserId(Long userId) {
        return pointRepository.findWalletByUserId(userId)
            .orElseThrow(() -> new PointException(PointErrorCode.POINT_WALLET_NOT_FOUND));
    }

    private Point getPointByOrderId(String orderId) {
        return pointRepository.findBySourceOrderId(orderId)
            .orElseThrow(() -> new PointException(PointErrorCode.POINT_NOT_FOUND));
    }
}
