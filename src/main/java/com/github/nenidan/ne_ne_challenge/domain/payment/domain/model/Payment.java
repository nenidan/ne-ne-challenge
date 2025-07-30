package com.github.nenidan.ne_ne_challenge.domain.payment.domain.model;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.response.TossClientResult;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.type.PaymentStatus;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentException;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
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

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "failed_at")
    private LocalDateTime failedAt;

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

    public void updateConfirm(TossClientResult result) {
        this.paymentKey = result.getPaymentKey();
        this.paymentMethod = result.getMethod();
        this.status = PaymentStatus.of(result.getStatus());
        this.approvedAt = parseDateTime(result.getApprovedAt());
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

    // ================= 조회 메서드 =================

    public String getOrderName() {
        return generateOrderName(this.amount);
    }

    // ================= 유틸리티 메서드 =================

    private static LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null) return null;
        return OffsetDateTime.parse(dateTimeStr)
            .atZoneSameInstant(ZoneId.of("Asia/Seoul"))
            .toLocalDateTime();
    }

    private static String generateOrderId() {
        return "order-" + UUID.randomUUID();
    }

    private static String generateOrderName(int amount) {
        return "포인트 " + amount + "원 충전";
    }
}