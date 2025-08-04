package com.github.nenidan.ne_ne_challenge.domain.admin.application.client;

import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.ChallengeDto;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.ChallengeUserDto;

import java.util.List;

public interface ChallengeClientPort {

    List<ChallengeDto> getAllChallenges();

    List<ChallengeUserDto> getAllChallengeUsers();

}
