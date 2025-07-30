package com.github.nenidan.ne_ne_challenge.domain.point.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.point.dto.response.PointBalanceResponse;
import com.github.nenidan.ne_ne_challenge.domain.point.dto.response.PointHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.point.entity.PointWallet;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointException;
import com.github.nenidan.ne_ne_challenge.domain.point.repository.PointTransactionRepository;
import com.github.nenidan.ne_ne_challenge.domain.point.repository.PointWalletRepository;
import com.github.nenidan.ne_ne_challenge.domain.point.type.PointReason;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserException;
import com.github.nenidan.ne_ne_challenge.domain.user.repository.UserRepository;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointService {

    private final UserRepository userRepository;
    private final PointWalletRepository pointWalletRepository;
    private final PointTransactionRepository pointTransactionRepository;

    @Transactional
    public void createWallet(Long userId) {

        User findUser = userRepository.findById(userId)
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        PointWallet pointWallet = new PointWallet(findUser);
        pointWalletRepository.save(pointWallet);
    }

    public PointBalanceResponse getMyBalance(Long userId) {

        User findUser = userRepository.findById(userId)
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        PointWallet pointWallet = pointWalletRepository.findByUserId(findUser.getId())
            .orElseThrow(() -> new PointException(PointErrorCode.POINT_WALLET_NOT_FOUND));

        return PointBalanceResponse.from(pointWallet);
    }

    public CursorResponse<PointHistoryResponse, Long> searchMyPointHistory(Long userId, Long cursor, int size, PointReason reason, LocalDate startDate, LocalDate endDate) {

        LocalDateTime start = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime end = (endDate != null) ? endDate.atTime(23, 59, 59) : null;

        User findUser = userRepository.findById(userId)
            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        PointWallet pointWallet = pointWalletRepository.findByUserId(findUser.getId())
            .orElseThrow(() -> new PointException(PointErrorCode.POINT_WALLET_NOT_FOUND));

        List<PointHistoryResponse> pointHistoryList = pointTransactionRepository.searchMyPointHistory(pointWallet.getId(), cursor, reason, start, end, size + 1)
            .stream()
            .map(PointHistoryResponse::from)
            .toList();

        boolean hasNext = pointHistoryList.size() > size;

        List<PointHistoryResponse> content = hasNext ? pointHistoryList.subList(0, size) : pointHistoryList;

        Long nextCursor = hasNext ? pointHistoryList.get(pointHistoryList.size() - 1).getPointTransactionId() : null;

        return new CursorResponse<>(content, nextCursor, hasNext);
    }
}
