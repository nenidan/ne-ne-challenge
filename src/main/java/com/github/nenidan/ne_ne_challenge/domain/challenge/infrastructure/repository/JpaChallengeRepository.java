package com.github.nenidan.ne_ne_challenge.domain.challenge.infrastructure.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChallengeRepository extends JpaRepository<Challenge, Long> {

}
