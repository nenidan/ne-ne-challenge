package com.github.nenidan.ne_ne_challenge.domain.payment.domain.model;

import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.vo.Money;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.vo.OrderId;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.vo.OrderName;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.vo.PaymentKey;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.type.PaymentStatus;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.payment.exception.PaymentException;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_payment_order_id", columnNames = "order_id"),
        @UniqueConstraint(name = "uk_payment_payment_key", columnNames = "payment_key")
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Embedded
    private Money amount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Embedded
    private PaymentKey paymentKey;

    @Embedded
    private OrderId orderId;

    @Embedded
    private OrderName orderName;

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

    private Payment(Long userId, OrderId orderId, Money amount, OrderName orderName) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.orderName = orderName;
        this.status = PaymentStatus.PENDING;
        this.requestedAt = LocalDateTime.now();
    }

    // ====================== 결제 준비 관련 ======================

    public static Payment createPreparePayment(Long userId, Money amount) {
        OrderId orderId = OrderId.generate();
        OrderName orderName = OrderName.forPointCharge(amount);
        return new Payment(userId, orderId, amount, orderName);
    }

    // ====================== 결제 승인 관련 ======================

    public void markAsSuccess(String paymentKey, String status, String method, LocalDateTime approvedAt) {

        if (this.status != PaymentStatus.PENDING) {
            throw new PaymentException(PaymentErrorCode.INVALID_PAYMENT_STATUS);
        }

        PaymentStatus paymentStatus = PaymentStatus.of(status);

        this.paymentKey = PaymentKey.of(paymentKey);
        this.status = paymentStatus;
        this.paymentMethod = method;
        this.approvedAt = approvedAt;
    }

    public void markAsFailed(String paymentKey) {
        this.paymentKey = PaymentKey.of(paymentKey);
        this.failedAt = LocalDateTime.now();
        this.status = PaymentStatus.FAIL;
    }

    // ====================== 결제 취소 관련 ======================

    public void cancel(String cancelReason) {

        // 취소 가능한 결제인지 확인합니다.
        validateCancelable();

        this.cancelReason = cancelReason;
        this.canceledAt = LocalDateTime.now();
        this.status = PaymentStatus.CANCELED;
    }

    private void validateCancelable() {
        // 상태 확인
        if (this.status != PaymentStatus.DONE) {
            throw new PaymentException(PaymentErrorCode.CANNOT_CANCEL_PAYMENT);
        }

        // 7일이 지났는지 확인
        if (this.approvedAt != null && this.approvedAt.isBefore(LocalDateTime.now().minusDays(7))) {
            throw new PaymentException(PaymentErrorCode.CANNOT_CANCEL_EXPIRED);
        }
    }

    public void rollbackCancel() {
        if (this.status != PaymentStatus.CANCELED) {
            throw new PaymentException(PaymentErrorCode.INVALID_PAYMENT_STATUS);
        }

        this.status = PaymentStatus.DONE;
        this.cancelReason = null;
        this.canceledAt = null;
    }
}