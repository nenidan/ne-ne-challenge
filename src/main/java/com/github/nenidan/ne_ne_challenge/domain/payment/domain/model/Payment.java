package com.github.nenidan.ne_ne_challenge.domain.payment.domain.model;

import com.github.nenidan.ne_ne_challenge.domain.payment.domain.type.PaymentStatus;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentException;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private int amount; // TOSS : totalAmount

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod; // TOSS : method

    @Column(name = "payment_key", unique = true)
    private String paymentKey; // TOSS : paymentKey -> nullable : key - in 호출 전에는 null

    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId; // TOSS : orderId

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status; // TOSS : status

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt; // TOSS : requestedAt

    @Column(name = "approved_at")
    private LocalDateTime approvedAt; // TOSS : approvedAt 결제 승인(성공) 시간

    @Column(name = "failed_ar")
    private LocalDateTime failedAt; // 결제 실패 시각

    private Payment(Long userId, int amount, PaymentStatus status, PaymentMethod method, LocalDateTime requestedAt) {
        this.userId = userId;
        this.amount = amount;
        this.status = status;
        this.method = method;
        this.requestedAt = requestedAt;
    }

    public static Payment createChargePayment(Long userId, int amount, PaymentMethod method) {
        return new Payment(
            userId,
            amount,
            PaymentStatus.PENDING,
            method,
            LocalDateTime.now()
        );
    }

    public void succeed() {
        if (this.status != PaymentStatus.PENDING) {
            throw new PaymentException(PaymentErrorCode.ALREADY_PROCESSED_PAYMENT);
        }
        this.status = PaymentStatus.SUCCESS;
        this.confirmedAt = LocalDateTime.now();
    }

    public void fail() {
        if (this.status != PaymentStatus.PENDING) {
            throw new PaymentException(PaymentErrorCode.ALREADY_PROCESSED_PAYMENT);
        }
        this.status = PaymentStatus.FAIL;
        this.failedAt = LocalDateTime.now();
    }
}