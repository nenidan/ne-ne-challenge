package com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.fcm;

import org.springframework.stereotype.Component;

import com.github.nenidan.ne_ne_challenge.domain.notification.application.sender.NotificationSender;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class FcmNotificationSender implements NotificationSender {

	private final FcmTokenRepository fcmTokenRepository;

	@Override
	public boolean send(Long userId, Platform platform, String title, String content) {
		FcmToken token = getFcmToken(userId, platform);

		Notification notification = Notification.builder()
			.setTitle(title)
			.setBody(content)
			.build();

		Message message = Message.builder()
			.setToken(token.getToken())
			.setNotification(notification)
			.build();
		try {
			String response = FirebaseMessaging.getInstance().send(message);
			log.info("FCM 전송 성공 - userId={}, token={}, response={}", userId, token, response);
			return true;
		} catch (FirebaseMessagingException e) {
			log.error("FCM 전송 실패 - userId={}, token={}", userId, token, e);
			return false;
		}
	}

	private FcmToken getFcmToken(Long userId, Platform platform) {
		return fcmTokenRepository.findByUserIdAndPlatform(userId, platform)
			.orElseThrow(() -> new FcmException(FcmErrorCode.TOKEN_NOT_FOUND));
	}
}