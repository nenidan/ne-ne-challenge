package com.github.nenidan.ne_ne_challenge.domain.point.application;


import org.springframework.stereotype.Component;

import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.request.PointSearchCommand;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.PointBalanceResult;
import com.github.nenidan.ne_ne_challenge.domain.point.application.dto.response.PointHistoryResult;
import com.github.nenidan.ne_ne_challenge.global.client.user.UserClient;
import com.github.nenidan.ne_ne_challenge.global.client.user.dto.UserResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
 public class PointFacade {

    private final UserClient userClient;
    private final PointService pointService;

    // ============================= 나의 잔액 조회 =============================
    public PointBalanceResult getMyBalance(Long userId) {

        userClient.getUserById(userId);

        return pointService.getBalance(userId);
    }

    // ============================= 나의 포인트 사용 이력 조회 =============================
    public CursorResponse<PointHistoryResult, Long> searchMyPointHistory(Long userId, PointSearchCommand command) {

        UserResponse user = userClient.getUserById(userId);

        return pointService.searchMyPointHistory(user.getId(), command);
    }
}
