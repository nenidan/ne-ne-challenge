package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.controller.internal;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.PaymentService;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response.PaymentStatisticsResponse;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.mapper.PaymentPresentationMapper;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
public class PaymentInternalController {

    private final PaymentService paymentService;

    @GetMapping("/statistics/payments")
    public ResponseEntity<ApiResponse<List<PaymentStatisticsResponse>>> getAllPayments() {

        List<PaymentStatisticsResponse> paymentStatisticsResponseList = paymentService.getAllPayments().stream()
            .map(PaymentPresentationMapper::toPaymentStatisticsResponse)
            .toList();

        return ApiResponse.success(
            HttpStatus.OK,
            "전체 결제 내역",
            paymentStatisticsResponseList
        );
    }
}
