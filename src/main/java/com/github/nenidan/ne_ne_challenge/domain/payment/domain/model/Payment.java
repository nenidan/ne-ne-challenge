package com.github.nenidan.ne_ne_challenge.domain.payment.domain.model;

import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.payment.domain.type.PaymentStatus;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentException;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private int amount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_key", unique = true)
    private String paymentKey;

    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(name = "cancel_reason")
    private String cancelReason;

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "failed_at")
    private LocalDateTime failedAt;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    private Payment(Long userId, String paymentKey, String orderId, String status, String method,
        LocalDateTime requestedAt, LocalDateTime approvedAt, int totalAmount) {
        this.userId = userId;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.status = PaymentStatus.of(status);
        this.paymentMethod = method;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
        this.amount = totalAmount;
    }

    public static Payment createPaymentFromConfirm(
        Long userId,
        String paymentKey,
        String orderId,
        String status,
        String method,
        LocalDateTime requestedAt,
        LocalDateTime approvedAt,
        int totalAmount
    ) {
        return new Payment(
            userId,
            paymentKey,
            orderId,
            status,
            method,
            requestedAt,
            approvedAt,
            totalAmount
        );
    }

    // ================= 비즈니스 로직 =================

    public void fail(String status, LocalDateTime canceledAt) {
        PaymentStatus paymentStatus = PaymentStatus.of(status);
        if (paymentStatus != PaymentStatus.CANCELED) {
            throw new PaymentException(PaymentErrorCode.ALREADY_PROCESSED_PAYMENT);
        }
        this.status = PaymentStatus.FAIL;
        this.failedAt = canceledAt;
    }

    // ================= 조회 메서드 =================

    public void validateCancelable() {
        // 상태 확인
        if (this.status != PaymentStatus.DONE) {
            throw new PaymentException(PaymentErrorCode.CANNOT_CANCEL_PAYMENT);
        }

        // 7일이 지났는지 확인
        if (this.approvedAt != null && this.approvedAt.isBefore(LocalDateTime.now().minusDays(7))) {
            throw new PaymentException(PaymentErrorCode.CANNOT_CANCEL_EXPIRED);
        }
    }

    // ================= 유틸리티 메서드 =================

    public void cancel(String cancelReason, LocalDateTime canceledAt, String status) {
        this.cancelReason = cancelReason;
        this.canceledAt = canceledAt;
        this.status = PaymentStatus.of(status);
    }

}