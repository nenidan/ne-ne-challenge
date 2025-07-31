package com.github.nenidan.ne_ne_challenge.domain.payment.presentation.controller.external;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.PaymentFacade;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentConfirmResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.PaymentSearchResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request.PaymentConfirmRequest;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request.PaymentPrepareRequest;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.request.PaymentSearchRequest;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response.PaymentConfirmResponse;
import com.github.nenidan.ne_ne_challenge.domain.payment.presentation.dto.response.PaymentPrepareResponse;
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
     * 포인트 충전을 위한 결제 준비 API
     *
     * 토스페이먼츠 결제창 호출 전에 필요한 결제 정보를 생성하고 저장합니다.
     * 결제 상태는 PENDING으로 초기화되며, 실제 결제는 confirm API에서 처리됩니다.
     * /payments/confirm API 호출 후에 포인트 충전을 성공하면 결제 상태가 DONE, 실패하면 결제상태가 FAIL로 저장됩니다.
     *
     * 처리 과정
     *
     * 고유한 주문 ID 생성 (UUID)
     * Payment 엔티티 생성 및 DB 저장 (상태: PENDING)
     * 프론트엔드에서 토스 결제창 호출에 필요한 정보 반환
     * 토스 공식문서에서 /payments/confirm 요청 시 필요한 정보 반환
     * amount, orderId, orderName
     *
     * 결제 플로우
     *
     * 1. prepare (현재 API) → Payment 생성 (PENDING)
     * 2. 토스 결제창 → 사용자 결제 진행
     * 3. confirm API → 결제 확인 및 포인트 충전
     *
     */
    @PostMapping("/payments/prepare")
    public ResponseEntity<ApiResponse<PaymentPrepareResponse>> preparePayment(
        @Valid @RequestBody PaymentPrepareRequest request,
        @AuthenticationPrincipal Auth auth
    ) {

        return ApiResponse.success(
            HttpStatus.CREATED,
            "결제 준비가 완료되었습니다.",
            PaymentPresentationMapper.toPaymentPrepareResponse(
                paymentFacade.createPreparePayment(
                    auth.getId(),
                    PaymentPresentationMapper.toPaymentPrepareCommand(request)
                )
            )
        );
    }

    @PostMapping("/payments/confirm")
    public ResponseEntity<ApiResponse<PaymentConfirmResponse>> confirmAndChargePoint(
        @RequestBody PaymentConfirmRequest request,
        @AuthenticationPrincipal Auth auth) {

        PaymentConfirmResult paymentConfirmResult = paymentFacade.confirmAndChargePoint(
            auth.getId(),
            PaymentPresentationMapper.toPaymentConfirmCommand(request)
        );

        return ApiResponse.success(
            HttpStatus.OK,
            "포인트 충전이 완료되었습니다.",
            PaymentPresentationMapper.toPaymentConfirmResponse(paymentConfirmResult));
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
