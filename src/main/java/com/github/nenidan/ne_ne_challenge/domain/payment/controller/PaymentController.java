package com.github.nenidan.ne_ne_challenge.domain.payment.controller;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.payment.dto.request.ChargePointRequest;
import com.github.nenidan.ne_ne_challenge.domain.payment.dto.response.PaymentResponse;
import com.github.nenidan.ne_ne_challenge.domain.payment.service.PaymentService;
import com.github.nenidan.ne_ne_challenge.domain.payment.type.PaymentMethod;
import com.github.nenidan.ne_ne_challenge.domain.payment.type.PaymentStatus;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payments/point")
    public ResponseEntity<ApiResponse<PaymentResponse>> chargePoint(@Valid @RequestBody ChargePointRequest chargePointRequest, @AuthenticationPrincipal Auth auth) {
        return ApiResponse.success(HttpStatus.CREATED, "결제가 완료되었습니다.", paymentService.chargePoint(auth.getId(), chargePointRequest));
    }

    @GetMapping("/payments")
    public ResponseEntity<ApiResponse<CursorResponse<PaymentResponse, Long>>> searchMyPayments(
        @AuthenticationPrincipal Auth auth,
        @RequestParam(required = false) Long cursor,
        @RequestParam(defaultValue = "10") @Min(1) int size,
        @RequestParam(required = false) PaymentMethod method,
        @RequestParam(required = false) PaymentStatus status,
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate) {
        return ApiResponse.success(
            HttpStatus.OK,
            "결제 내역 조회가 완료되었습니다.",
            paymentService.searchMyPayments(auth.getId(), cursor, size, method, status, startDate, endDate));
    }
}
