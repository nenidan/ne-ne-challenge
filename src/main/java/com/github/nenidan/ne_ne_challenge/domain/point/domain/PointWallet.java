package com.github.nenidan.ne_ne_challenge.domain.point.domain;


import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointException;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;
import jakarta.persistence.*;
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

    @Column(name = "user_id", nullable = false)
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
        if (this.balance < amount) {
            throw new PointException(PointErrorCode.INSUFFICIENT_POINT_BALANCE);
        }
        this.balance -= amount;
    }
}