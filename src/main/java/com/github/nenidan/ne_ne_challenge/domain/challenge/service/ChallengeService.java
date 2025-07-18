package com.github.nenidan.ne_ne_challenge.domain.challenge.service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
}
