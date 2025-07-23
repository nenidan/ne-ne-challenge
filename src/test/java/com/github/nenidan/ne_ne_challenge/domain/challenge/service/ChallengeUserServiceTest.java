package com.github.nenidan.ne_ne_challenge.domain.challenge.service;

import com.github.nenidan.ne_ne_challenge.domain.challenge.entity.Challenge;
import com.github.nenidan.ne_ne_challenge.domain.challenge.repository.ChallengeRepository;
import com.github.nenidan.ne_ne_challenge.domain.challenge.repository.ChallengeUserRepository;
import com.github.nenidan.ne_ne_challenge.domain.point.entity.PointWallet;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.point.exception.PointException;
import com.github.nenidan.ne_ne_challenge.domain.point.repository.PointWalletRepository;
import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import com.github.nenidan.ne_ne_challenge.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ChallengeUserServiceTest {

    @Mock
    UserRepository userRepository;
    
    @Mock
    ChallengeUserRepository challengeUserRepository;
    
    @Mock
    PointWalletRepository pointWalletRepository;

    @Mock
    ChallengeRepository challengeRepository;

    @InjectMocks
    ChallengeUserService challengeUserService;

    @Test
    void 포인트_부족_시_챌린지_참가에_실패한다() {
        // given
        User user = mock(User.class);
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        Challenge challenge = mock(Challenge.class);
        given(challengeRepository.findById(1L)).willReturn(Optional.of(challenge));

        PointWallet pointWallet = mock(PointWallet.class);
        willThrow(new PointException(PointErrorCode.INSUFFICIENT_BALANCE)).given(pointWallet).decrease(anyInt());
        given(pointWalletRepository.findByUserId(1L)).willReturn(Optional.of(pointWallet));

        // when + then
        assertThrows(PointException.class, () -> challengeUserService.joinChallenge(1L, 1L, true));
    }
}