package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.controller.external;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.PaymentFacade;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentCancelResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentConfirmResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentSearchResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request.PaymentCancelRequest;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request.PaymentConfirmRequest;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request.PaymentSearchRequest;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response.PaymentCancelResponse;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response.PaymentConfirmResponse;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response.PaymentSearchResponse;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.mapper.PaymentPresentationMapper;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class PaymentController {

    private final PaymentFacade paymentFacade;

    @PostMapping("/payments/confirm")
    public ResponseEntity<ApiResponse<PaymentConfirmResponse>> confirmAndChargePoint(
        @RequestBody PaymentConfirmRequest request,
        @AuthenticationPrincipal Auth auth) {

        long startTime = System.currentTimeMillis();
        PaymentConfirmResult paymentConfirmResult = paymentFacade.confirmAndChargePoint(
            auth.getId(),
            PaymentPresentationMapper.toPaymentConfirmCommand(request)
        );
        long duration = System.currentTimeMillis() - startTime;
        log.info("CONFIRM API TIME : {}ms", duration);

        return ApiResponse.success(
            HttpStatus.OK,
            "포인트 충전이 완료되었습니다.",
            PaymentPresentationMapper.toPaymentConfirmResponse(paymentConfirmResult));
    }

    @PostMapping("/payments/{orderId}/cancel")
    public ResponseEntity<ApiResponse<PaymentCancelResponse>> cancelPayment(
        @AuthenticationPrincipal Auth auth,
        @PathVariable String orderId,
        @Valid @RequestBody PaymentCancelRequest request) {

        PaymentCancelResult result = paymentFacade.cancelPayment(auth.getId(), orderId,
            PaymentPresentationMapper.toPaymentCancelCommand(request));

        return ApiResponse.success(
            HttpStatus.OK,
            "결제 취소가 완료되었습니다.",
            PaymentPresentationMapper.toPaymentCancelResponse(result)
        );
    }

    @GetMapping("/payments")
    public ResponseEntity<ApiResponse<CursorResponse<PaymentSearchResponse, Long>>> searchMyPayments(
        @AuthenticationPrincipal Auth auth,
        @Valid PaymentSearchRequest request) {

        CursorResponse<PaymentSearchResult, Long> result = paymentFacade.searchMyPayments(
            auth.getId(), PaymentPresentationMapper.toPaymentSearchCommand(request));

        List<PaymentSearchResponse> paymentSearchResponseList = result.getContent().stream()
            .map(PaymentPresentationMapper::toPaymentResponse)
            .toList();

        return ApiResponse.success(
            HttpStatus.OK,
            "결제 내역 조회가 완료되었습니다.",
            new CursorResponse<>(paymentSearchResponseList, result.getNextCursor(), result.isHasNext()));
    }
}
