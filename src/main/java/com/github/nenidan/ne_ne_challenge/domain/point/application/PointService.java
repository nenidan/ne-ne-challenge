package com.github.nenidan.ne_ne_challenge.domain.point.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.request.PointAmountCommand;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.request.PointChargeCommand;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.PointBalanceResult;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.PointHistoryResult;
import com.github.nenidan.ne_ne_challenge.domain.point.application.mapper.PointApplicationMapper;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.repository.PointRepository;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.repository.PointTransactionRepository;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.repository.PointWalletRepository;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.type.PointReason;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.PointTransaction;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.PointWallet;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointException;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PointService {

    private final PointRepository pointRepository;

    public PointBalanceResult getBalance(Long userId) {

        PointWallet pointWallet = getPointWallet(userId);

        return new PointBalanceResult(pointWallet.getBalance());
    }

    public CursorResponse<PointHistoryResult, Long> searchMyPointHistory(Long userId, Long cursor, int size, String reason, LocalDate startDate, LocalDate endDate) {

        LocalDateTime start = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime end = (endDate != null) ? endDate.atTime(23, 59, 59) : null;
        PointReason pointReason = StringUtils.hasText(reason) ? PointReason.of(reason) : null;

        PointWallet pointWallet = getPointWallet(userId);

        List<PointHistoryResult> pointHistoryResultList = pointRepository.searchMyPointHistory(pointWallet.getId(), cursor, pointReason, start, end, size + 1)
            .stream()
            .map(PointApplicationMapper::toPointHistoryResult)
            .toList();

        boolean hasNext = pointHistoryResultList.size() > size;

        List<PointHistoryResult> content = hasNext ? pointHistoryResultList.subList(0, size) : pointHistoryResultList;

        Long nextCursor = hasNext ? pointHistoryResultList.get(pointHistoryResultList.size() - 1).getPointTransactionId() : null;

        return new CursorResponse<>(content, nextCursor, hasNext);
    }

    @Transactional
    public void createPointWallet(Long userId) {
        boolean exists = pointRepository.existsByUserId(userId);
        if (exists) {
            throw new PointException(PointErrorCode.WALLET_ALREADY_EXISTS);
        }

        PointWallet pointWallet = PointWallet.createPointWallet(userId);
        pointRepository.save(pointWallet);
    }

    @Transactional
    public void charge(Long userId, PointChargeCommand command) {

        PointReason pointReason = PointReason.of(command.getReason());
        if (!pointReason.isIncrease()) {
            throw new PointException(PointErrorCode.INVALID_POINT_REASON);
        }

        PointWallet pointWallet = getPointWallet(userId);
        pointWallet.increase(command.getAmount());
        pointRepository.save(pointWallet);

        PointTransaction pointTransaction = PointTransaction.createPointTransaction(pointWallet, command.getAmount(), pointReason, pointReason.getDescription());
        pointRepository.save(pointTransaction);
    }

    public PointWallet getPointWallet(Long userId) {
        return pointRepository.findWalletByUserId(userId)
            .orElseThrow(() -> new PointException(PointErrorCode.POINT_WALLET_NOT_FOUND));
    }

    @Transactional
    public void increase(Long userId, PointAmountCommand pointAmountCommand) {
        // 포인트 지갑 조회
        PointWallet pointWallet = pointRepository.findWalletByUserId(userId)
            .orElseThrow(() -> new PointException(PointErrorCode.POINT_WALLET_NOT_FOUND));

        // reason 검증
        PointReason pointReason = PointReason.of(pointAmountCommand.getReason());

        // 증가 사유 체크
        if (!pointReason.isIncrease()) {
            throw new PointException(PointErrorCode.INVALID_POINT_REASON);
        }

        // 포인트 증가
        pointWallet.increase(pointAmountCommand.getAmount());

        // 포인트 지갑 저장
        pointRepository.save(pointWallet);

        // 포인트 트랜잭션 생성
        PointTransaction pointTransaction = PointTransaction.createPointTransaction(
            pointWallet,
            pointAmountCommand.getAmount(), pointReason,
            pointReason.getDescription()
        );

        pointRepository.save(pointTransaction);
    }

    @Transactional
    public void decrease(Long userId, PointAmountCommand pointAmountCommand) {
        // 포인트 지갑 조회
        PointWallet pointWallet = pointRepository.findWalletByUserId(userId)
            .orElseThrow(() -> new PointException(PointErrorCode.POINT_WALLET_NOT_FOUND));

        // reason 검증
        PointReason pointReason = PointReason.of(pointAmountCommand.getReason());

        // 감소 사유 체크
        if (!pointReason.isDecrease()) {
            throw new PointException(PointErrorCode.INVALID_POINT_REASON);
        }

        // 포인트 증가
        pointWallet.decrease(pointAmountCommand.getAmount());

        // 포인트 지갑 저장
        pointRepository.save(pointWallet);

        // 포인트 트랜잭션 생성
        PointTransaction pointTransaction = PointTransaction.createPointTransaction(
            pointWallet,
            -pointAmountCommand.getAmount(),
            pointReason,
            pointReason.getDescription()
        );

        pointRepository.save(pointTransaction);
    }
}
