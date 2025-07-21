package com.github.nenidan.ne_ne_challenge.domain.challenge.entity;

import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ChallengeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    private boolean isHost;

    public ChallengeUser(User user, Challenge challenge, boolean isHost) {
        this.user = user;
        this.challenge = challenge;
        this.isHost = isHost;
    }
}
