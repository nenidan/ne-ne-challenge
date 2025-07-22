package com.github.nenidan.ne_ne_challenge.domain.notification.repository.fcm;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.nenidan.ne_ne_challenge.domain.notification.enttiy.fcm.FcmToken;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
	Optional<FcmToken> findByUserId(Long userId);
}
