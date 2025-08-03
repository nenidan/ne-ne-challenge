package com.github.nenidan.ne_ne_challenge.domain.payment.domain.model;

import static com.github.nenidan.ne_ne_challenge.domain.payment.util.DateTimeUtil.*;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.TossConfirmResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.type.PaymentStatus;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentException;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

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

    // ================= 생성자 =================

    private Payment(Long userId, String orderId, int amount) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
        this.requestedAt = LocalDateTime.now();
    }

    // ================= 정적 팩토리 메서드 =================

    public static Payment createPreparePayment(Long userId, int amount) {
        String orderId = generateOrderId();
        return new Payment(userId, orderId, amount);
    }

    // ================= 비즈니스 로직 =================

    public void updateConfirm(TossConfirmResult result) {
        this.paymentKey = result.getPaymentKey();
        this.paymentMethod = result.getMethod();
        this.status = PaymentStatus.of(result.getStatus());
        this.approvedAt = parseToLocalDateTime(result.getApprovedAt());
    }

    public void fail() {
        if (this.status != PaymentStatus.PENDING) {
            throw new PaymentException(PaymentErrorCode.ALREADY_PROCESSED_PAYMENT);
        }
        this.status = PaymentStatus.FAIL;
        this.failedAt = LocalDateTime.now();
    }

    public void validateAmountForConfirm(int requestAmount) {
        if (this.amount != requestAmount) {
            throw new PaymentException(PaymentErrorCode.AMOUNT_MISMATCH);
        }
    }

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

    public void cancel(String cancelReason, String canceledAt, String status) {
        this.cancelReason = cancelReason;
        this.canceledAt = parseToLocalDateTime(canceledAt);
        this.status = PaymentStatus.of(status);
    }

    // ================= 조회 메서드 =================

    public String getOrderName() {
        return generateOrderName(this.amount);
    }

    // ================= 유틸리티 메서드 =================

    private static String generateOrderId() {
        return "order-" + UUID.randomUUID();
    }

    private static String generateOrderName(int amount) {
        return "포인트 " + amount + "원 충전";
    }

}