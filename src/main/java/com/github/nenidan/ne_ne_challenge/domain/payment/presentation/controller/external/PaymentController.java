package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.controller.external;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.PaymentFacade;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request.ChargePointRequest;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response.PaymentResponse;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.mapper.PaymentPresentationMapper;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class PaymentController {

    private final PaymentFacade paymentFacade;

    @PostMapping("/payments/points")
    public ResponseEntity<ApiResponse<PaymentResponse>> chargePoint(
        @Valid @RequestBody ChargePointRequest chargePointRequest,
        @AuthenticationPrincipal Auth auth) {

        PaymentResponse paymentResponse = PaymentPresentationMapper.toPaymentResponse(
            paymentFacade.chargePoint(
                auth.getId(),
                PaymentPresentationMapper.toChargePointCommand(chargePointRequest)
            )
        );

        String message = paymentResponse.getStatus().equals("SUCCESS")
            ? "포인트 충전이 완료되었습니다."
            : "포인트 충전에 실패했습니다. 관리자에게 문의하세요.";

        return ApiResponse.success(HttpStatus.CREATED, message, paymentResponse);
    }

    @GetMapping("/payments")
    public ResponseEntity<ApiResponse<CursorResponse<PaymentResponse, Long>>> searchMyPayments(
        @AuthenticationPrincipal Auth auth,
        @RequestParam(required = false) Long cursor,
        @RequestParam(defaultValue = "10") @Min(1) int size,
        @RequestParam(required = false) String method,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate) {

        CursorResponse<PaymentResult, Long> result = paymentFacade.searchMyPayments(
            auth.getId(), cursor, size, method, status, startDate, endDate);

        List<PaymentResponse> paymentResponseList = result.getContent().stream()
            .map(PaymentPresentationMapper::toPaymentResponse)
            .toList();

        return ApiResponse.success(
            HttpStatus.OK,
            "결제 내역 조회가 완료되었습니다.",
            new CursorResponse<>(paymentResponseList, result.getNextCursor(), result.isHasNext()));
    }
}
