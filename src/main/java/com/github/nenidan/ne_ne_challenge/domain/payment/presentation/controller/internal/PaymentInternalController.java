package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.controller.internal;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.PaymentService;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response.PaymentStatisticsResponse;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.mapper.PaymentPresentationMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
public class PaymentInternalController {

    private final PaymentService paymentService;

    @GetMapping("/statistics/payments")
    public List<PaymentStatisticsResponse> getAllPayments() {

        List<PaymentStatisticsResponse> paymentStatisticsResponseList = paymentService.getAllPayments().stream()
            .map(PaymentPresentationMapper::toPaymentStatisticsResponse)
            .toList();

        return paymentStatisticsResponseList;
    }
}
