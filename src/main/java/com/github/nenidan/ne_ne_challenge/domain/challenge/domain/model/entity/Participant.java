package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity;

import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor
@SQLRestriction("deleted_at IS NULL")
@Table(name = "participant", uniqueConstraints = {@UniqueConstraint(name = "unique_user_id_challenge_id", columnNames = {"user_id", "challenge_id"})})
public class Participant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    private boolean isHost;

    public Participant(Long userId, Challenge challenge, boolean isHost) {
        this.userId = userId;
        this.challenge = challenge;
        this.isHost = isHost;
    }
}