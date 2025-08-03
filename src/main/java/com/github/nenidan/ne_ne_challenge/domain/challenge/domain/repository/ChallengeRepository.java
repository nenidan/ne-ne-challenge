package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeCategory;
import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.type.ChallengeStatus;

public interface ChallengeRepository {

    Challenge save(Challenge challenge);

    Optional<Challenge> findById(Long id);

    List<Challenge> getChallengeList(
        Long userId,
        String name,
        ChallengeStatus status,
        LocalDate dueAt,
        ChallengeCategory category,
        Integer maxParticipationFee,
        LocalDateTime cursor,
        int limit
    );
}
