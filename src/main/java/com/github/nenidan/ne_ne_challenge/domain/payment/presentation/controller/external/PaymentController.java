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

    /**
     * 토스페이 결제 승인 및 포인트 충전 API
     * @param request 토스페이먼츠에서 제공한 결제 정보 (paymentKey, orderId, amount)
     * @param auth 인증된 사용자 정보
     * @return 결제 승인 결과 (orderId, amount, method, status, approvedAt)
     */
    @PostMapping("/payments/confirm")
    public ResponseEntity<ApiResponse<PaymentConfirmResponse>> confirmAndChargePoint(
        @Valid @RequestBody PaymentConfirmRequest request,
        @AuthenticationPrincipal Auth auth) {

        PaymentConfirmResult paymentConfirmResult = paymentFacade.confirmAndChargePoint(
            auth.getId(),
            PaymentPresentationMapper.toPaymentConfirmCommand(request)
        );

        return ApiResponse.success(
            HttpStatus.OK,
            "결제가 완료되었습니다. 포인트는 1~2분 이내로 충전될 예정입니다.",
            PaymentPresentationMapper.toPaymentConfirmResponse(paymentConfirmResult));
    }

    /**
     * 결제 취소 API
     * 사용자는 자신이 결제한 포인트 중에서, 7일 이내의 사용되지 않은 포인트는 환불이 가능하다.
     * @param auth 인증된 사용자 정보
     * @param orderId 결제할 때 사용한 orderId
     * @param request 환불 사유(예: 단순 변심)
     * @return 결제 취소 결과
     */
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

    /**
     * 나의 결제 내역 조회 API
     * @param auth 인증된 사용자 정보
     * @param request 결제 내역 검색 조건(cursor, size, status, startDate, endDate)
     * @return 커서 기반의 자신의 결제 내역
     */
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
