package com.github.nenidan.ne_ne_challenge.domain.challenge.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Participant;

@Repository
public interface JpaParticipantRepository extends JpaRepository<Participant, Long> {

    Optional<Participant> findByChallenge_IdAndUserId(Long challengeId, Long userId);

    List<Participant> findByChallenge_Id(Long challengeId);

    int countByChallenge_Id(Long challengeId);
}