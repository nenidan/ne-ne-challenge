package com.github.nenidan.ne_ne_challenge.domain.challenge.service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.ChallengeUser;
import com.github.nenidan.ne_ne_challenge.domain.challenge.exception.ChallengeErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.challenge.exception.ChallengeException;
import com.github.nenidan.ne_ne_challenge.domain.challenge.repository.ChallengeRepository;
import com.github.nenidan.ne_ne_challenge.domain.challenge.repository.ChallengeUserRepository;
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
public class ChallengeUserService {

    private final UserRepository userRepository;

    private final ChallengeRepository challengeRepository;

    private final ChallengeUserRepository challengeUserRepository;
    
    @Transactional
    public void joinChallenge(long userId, long challengeId, boolean isHost) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(() -> new ChallengeException(
            ChallengeErrorCode.CHALLENGE_NOT_FOUND));

        // Todo 사용자의 포인트 검증
        challenge.addParticipant(user);
        ChallengeUser challengeUser = new ChallengeUser(user, challenge, isHost);
        
        challengeUserRepository.save(challengeUser);
    }
}
