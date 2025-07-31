package com.github.nenidan.ne_ne_challenge.domain.point.presentation.internal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.point.application.PointService;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.request.PointChargeRequest;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.mapper.PointPresentationMapper;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
public class PointInternalController {

    private final PointService pointService;

    @PostMapping("/points/wallet")
    public ResponseEntity<ApiResponse<Void>> createPointWallet(@RequestParam Long userId) {

        pointService.createPointWallet(userId);

        return ApiResponse.success(HttpStatus.CREATED, "포인트 지갑을 생성하였습니다.", null);
    }

    @PostMapping("/points/charge")
    public ResponseEntity<ApiResponse<Void>> chargePoint(@RequestBody PointChargeRequest request) {

        pointService.charge(PointPresentationMapper.toPointChargeCommand(request));

        return ApiResponse.success(HttpStatus.OK, "포인트 충전에 성공하였습니다.", null);
    }
}
