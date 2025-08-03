package com.github.nenidan.ne_ne_challenge.domain.point.domain;

import com.github.nenidan.ne_ne_challenge.domain.point.domain.type.PointReason;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PointTransaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_wallet_id", nullable = false)
    private PointWallet pointWallet;

    @Column(nullable = false)
    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PointReason reason;

    private String description;

    private PointTransaction(PointWallet pointWallet, int amount, PointReason reason, String description) {
        this.pointWallet = pointWallet;
        this.amount = amount;
        this.reason = reason;
        this.description = description;
    }

    public static PointTransaction createChargeTransaction(PointWallet pointWallet, int amount, PointReason reason, String description) {
        return new PointTransaction(pointWallet, amount, reason, description);
    }

    public static PointTransaction createUsageTransaction(PointWallet pointWallet, int amount, PointReason reason, String description) {
        return new PointTransaction(pointWallet, -amount, reason, description);
    }
}
