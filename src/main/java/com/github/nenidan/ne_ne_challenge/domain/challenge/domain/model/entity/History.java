package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "challengeId", "date"}))
public class History extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long challengeId;

    private String content;
    private boolean isSuccess;

    private LocalDate date;

    private History(Long userId, Long challengeId, String content, boolean isSuccess, LocalDate date) {
        if(content == null || content.isEmpty()) {
            throw new ChallengeException(ChallengeErrorCode.EMPTY_HISTORY_CONTENT);
        }

        this.userId = userId;
        this.challengeId = challengeId;
        this.content = content;
        this.isSuccess = isSuccess;
        this.date = date;
    }

    public static History createSuccesfulHistory(Long userId, Long challengeId, String content) {
        return new History(userId, challengeId, content, true, LocalDate.now());
    }
}
