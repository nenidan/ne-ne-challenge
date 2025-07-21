package com.github.nenidan.ne_ne_challenge.domain.challenge.service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.request.CreateChallengeRequest;
import com.github.nenidan.ne_ne_challenge.domain.challenge.dto.response.ChallengeResponse;
import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.repository.ChallengeRepository;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserException;
import com.github.nenidan.ne_ne_challenge.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeUserService challengeUserService;

    private final ChallengeRepository challengeRepository;

    private final UserRepository userRepository;

    @Transactional
    public ChallengeResponse createChallenge(CreateChallengeRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        Challenge newChallenge = request.toEntity();

        Challenge savedChallenge = challengeRepository.save(newChallenge);
        challengeUserService.joinChallenge(user.getId(), savedChallenge.getId(), true);

        return ChallengeResponse.from(savedChallenge);
    }
}
