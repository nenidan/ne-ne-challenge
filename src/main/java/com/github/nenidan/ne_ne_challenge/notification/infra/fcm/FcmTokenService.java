package com.github.nenidan.ne_ne_challenge.notification.infra.fcm;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FcmTokenService {

	private final FcmTokenRepository fcmTokenRepository;

	@Transactional
	public Void saveToken(Long userId, String token) {
		Optional<FcmToken> fcmTokenOpt = fcmTokenRepository.findByUserId(userId);

		if ( fcmTokenOpt.isPresent() ){ // 토큰을 이미 가지고 있는 유저라면
			FcmToken fcmToken = fcmTokenOpt.get();
			fcmToken.updateToken(token);
		}else{ // 토큰을 가지고 있지않다면 토큰을 생성해서 DB 에 저장
			FcmToken fcmToken = new FcmToken(userId,token);
			fcmTokenRepository.save(fcmToken);
		}

		return null;
	}
}
