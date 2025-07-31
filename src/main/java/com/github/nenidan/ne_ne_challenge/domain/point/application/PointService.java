package com.github.nenidan.ne_ne_challenge.domain.point.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.request.PointChargeCommand;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.PointBalanceResult;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.PointHistoryResult;
import com.github.nenidan.ne_ne_challenge.domain.point.application.mapper.PointApplicationMapper;
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

    private final PointWalletRepository pointWalletRepository;
    private final PointTransactionRepository pointTransactionRepository;

    public PointBalanceResult getBalance(Long userId) {

        PointWallet pointWallet = getPointWallet(userId);

        return new PointBalanceResult(pointWallet.getBalance());
    }

    public CursorResponse<PointHistoryResult, Long> searchMyPointHistory(Long userId, Long cursor, int size, String reason, LocalDate startDate, LocalDate endDate) {

        LocalDateTime start = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime end = (endDate != null) ? endDate.atTime(23, 59, 59) : null;
        PointReason pointReason = StringUtils.hasText(reason) ? PointReason.of(reason) : null;

        PointWallet pointWallet = getPointWallet(userId);

        List<PointHistoryResult> pointHistoryResultList = pointTransactionRepository.searchMyPointHistory(pointWallet.getId(), cursor, pointReason, start, end, size + 1)
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
        boolean exists = pointWalletRepository.existsByUserId(userId);
        if (exists) {
            throw new PointException(PointErrorCode.WALLET_ALREADY_EXISTS);
        }

        PointWallet pointWallet = PointWallet.createPointWallet(userId);
        pointWalletRepository.save(pointWallet);
    }

    @Transactional
    public void charge(PointChargeCommand command) {

        PointReason pointReason = PointReason.of(command.getReason());

        PointWallet pointWallet = getPointWallet(command.getUserId());
        pointWallet.increase(command.getAmount());
        pointWalletRepository.save(pointWallet);

        PointTransaction pointTransaction = PointTransaction.createChargeTransaction(pointWallet, command.getAmount(), pointReason, command.getDescription());
        pointTransactionRepository.save(pointTransaction);
    }

    public PointWallet getPointWallet(Long userId) {
        return pointWalletRepository.findWalletByUserId(userId)
            .orElseThrow(() -> new PointException(PointErrorCode.POINT_WALLET_NOT_FOUND));
    }
}
