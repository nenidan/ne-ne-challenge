package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository;

import java.util.List;
import java.util.Optional;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Participant;

public interface ParticipantRepository {
    Participant save(Participant participant);

    Optional<Participant> findById(Long id);

    Optional<Participant> findByChallengeIdAndUserId(Long challengeId, Long userId);

    List<Participant> findbyChallengeId(Long challengeId);

    int getParticipantCount(Long challengeId);
}
