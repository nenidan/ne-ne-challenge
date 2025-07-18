package com.github.nenidan.ne_ne_challenge.domain.challenge.controller;

import com.github.nenidan.ne_ne_challenge.domain.challenge.service.ChallengeService;
import com.github.nenidan.ne_ne_challenge.domain.challenge.type.ChallengeStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;
}