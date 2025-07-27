package com.github.nenidan.ne_ne_challenge.domain.point.application;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.github.nenidan.ne_ne_challenge.domain.point.application.client.UserClient;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.PointBalanceResult;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.PointHistoryResult;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.UserIdResult;
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

        UserIdResult user = userClient.getUser(userId);

        return pointService.getBalance(user.getId());
    }

    public CursorResponse<PointHistoryResult, Long> searchMyPointHistory(Long userId, Long cursor, int size,
        String reason, LocalDate startDate, LocalDate endDate) {

        UserIdResult user = userClient.getUser(userId);

        return pointService.searchMyPointHistory(user.getId(), cursor, size, reason, startDate, endDate);
    }
}
