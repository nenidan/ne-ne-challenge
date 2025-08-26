package com.github.nenidan.ne_ne_challenge.domain.challenge.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;

public interface JpaChallengeRepository extends JpaRepository<Challenge, Long> {

    Optional<Challenge> findByIdAndDeletedAtIsNull(Long id);

    List<Challenge> findAllByDeletedAtIsNull();
}
