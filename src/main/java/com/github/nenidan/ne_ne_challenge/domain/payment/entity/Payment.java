package com.github.nenidan.ne_ne_challenge.domain.payment.entity;

import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.payment.type.PaymentMethod;
import com.github.nenidan.ne_ne_challenge.domain.payment.type.PaymentStatus;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    @Column(nullable = false)
    private LocalDateTime requestedAt; // 결제 요청 시각

    private LocalDateTime confirmedAt; // 결제 완료 시각

    private LocalDateTime failedAt; // 결제 실패 시각

    public Payment(User user, int amount, PaymentMethod method) {
        this.user = user;
        this.status = PaymentStatus.SUCCESS;
        this.amount = amount;
        this.method = method;
        this.requestedAt = LocalDateTime.now();
    }

    public void succeed() {
        this.confirmedAt = LocalDateTime.now();
    }

    public void fail() {
        this.status = PaymentStatus.FAIL;
        this.failedAt = LocalDateTime.now();
    }
}
