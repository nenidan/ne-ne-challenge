package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity;

import org.hibernate.annotations.SQLRestriction;

import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@SQLRestriction("deleted_at IS NULL")
public class History extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    private String content; // Todo: 인증방식 변경

    private boolean isSuccess;

    public History(Long userId, Challenge challenge, String content, boolean isSuccess) {
        this.userId = userId;
        this.challenge = challenge;
        this.content = content;
        this.isSuccess = isSuccess;
    }
}
