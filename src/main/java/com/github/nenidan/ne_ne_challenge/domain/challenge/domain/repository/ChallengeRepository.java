package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;

import java.util.Optional;

public interface ChallengeRepository {

    Challenge save(Challenge challenge);

    Optional<Challenge> findById(Long id);
}
