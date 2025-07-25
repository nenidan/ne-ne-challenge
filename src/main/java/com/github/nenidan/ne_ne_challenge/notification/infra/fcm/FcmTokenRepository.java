package com.github.nenidan.ne_ne_challenge.notification.infra.fcm;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
	Optional<FcmToken> findByUserIdAndPlatform(Long userId, Platform platform);
}
