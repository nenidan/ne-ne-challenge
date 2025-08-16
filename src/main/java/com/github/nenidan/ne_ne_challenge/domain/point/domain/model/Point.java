package com.github.nenidan.ne_ne_challenge.domain.point.domain.model;

import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointException;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Point extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_wallet_id", nullable = false)
    private PointWallet pointWallet;

    @Column(nullable = false)
    private int amount;

    @Column(name = "remaining_amount", nullable = false)
    private int remainingAmount;

    @Column(name = "source_order_id")
    private String sourceOrderId; // Payment에 있는 orderId와 동일한 값

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    @Column(nullable = false)
    private boolean isUsed = false;

    private Point(PointWallet pointWallet, int amount, String sourceOrderId) {
        this.pointWallet = pointWallet;
        this.amount = amount;
        this.remainingAmount = amount;
        this.sourceOrderId = sourceOrderId;
    }

    // 생성 메서드
    public static Point createChargePoint(PointWallet pointWallet, int amount, String sourceOrderId) {
        return new Point(
            pointWallet,
            amount,
            sourceOrderId
        );
    }

    // 사용
    public void decrease(int useAmount) {
        if (this.remainingAmount < useAmount) {
            throw new PointException(PointErrorCode.INSUFFICIENT_POINT_BALANCE);
        }
        this.remainingAmount -= useAmount;
    }

    // 취소
    public void cancel() {
        if (this.isUsed) {
            throw new PointException(PointErrorCode.CANNOT_CANCEL_USED_POINTS);
        }

        if (this.remainingAmount < this.amount) {
            throw new PointException(PointErrorCode.CANNOT_CANCEL_USED_POINTS);
        }

        if (this.canceledAt != null) {
            throw new PointException(PointErrorCode.ALREADY_CANCELED_TRANSACTION);
        }
        this.canceledAt = LocalDateTime.now();
    }

    public void markUsed() {
        this.isUsed = true;
    }
}
