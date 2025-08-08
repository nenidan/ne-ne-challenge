package com.github.nenidan.ne_ne_challenge.domain.admin.application.client;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.ChallengeDto;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.ChallengeUserDto;

public interface ChallengeClientPort {

    List<ChallengeDto> getAllChallenges();

    List<ChallengeUserDto> getAllChallengeUsers();

}
