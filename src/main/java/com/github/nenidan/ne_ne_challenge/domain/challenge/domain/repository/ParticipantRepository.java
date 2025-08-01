package com.github.nenidan.ne_ne_challenge.domain.challenge.domain.repository;

import com.github.nenidan.ne_ne_challenge.domain.challenge.domain.model.entity.Participant;

import java.util.Optional;

public interface ParticipantRepository {
    Participant save(Participant participant);

    Optional<Participant> findById(Long id);
}
