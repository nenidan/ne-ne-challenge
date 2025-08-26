package com.github.nenidan.ne_ne_challenge.domain.point.domain.model;

import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointException;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointWallet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    private int balance;

    private PointWallet(Long userId) {
        this.userId = userId;
        this.balance = 0;
    }

    public static PointWallet createPointWallet(Long userId) {
        return new PointWallet(
            userId
        );
    }

    public void increase(int amount) {
        if (amount <= 0) {
            throw new PointException(PointErrorCode.INVALID_INCREASE_AMOUNT);
        }
        this.balance += amount;
    }

    public void decrease(int amount) {
        this.balance -= amount;
    }

    public void validateSufficientBalance(int amount) {
        if (this.balance < amount) {
            throw new PointException(PointErrorCode.INSUFFICIENT_POINT_BALANCE);
        }
    }
}