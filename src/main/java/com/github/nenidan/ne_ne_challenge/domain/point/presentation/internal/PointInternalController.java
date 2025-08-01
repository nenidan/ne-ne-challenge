package com.github.nenidan.ne_ne_challenge.domain.point.presentation.internal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.point.application.PointFacade;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.request.PointAmountRequest;
import com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.request.PointChargeRequest;

import com.github.nenidan.ne_ne_challenge.domain.point.application.PointService;
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
}
