package com.github.nenidan.ne_ne_challenge.notification.infra.fcm;

import org.springframework.stereotype.Component;

import com.github.nenidan.ne_ne_challenge.notification.domain.exception.NotificationErrorCode;
import com.github.nenidan.ne_ne_challenge.notification.domain.exception.NotificationException;
import com.github.nenidan.ne_ne_challenge.notification.infra.NotificationSender;
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
	public void send(Long userId, String title, String content) {
		FcmToken token = getFcmToken(userId);

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
		} catch (FirebaseMessagingException e) {
			log.error("FCM 전송 실패 - userId={}, token={}", userId, token, e);
		}
	}

	private FcmToken getFcmToken(Long userId) {
		return fcmTokenRepository.findByUserId(userId)
			.orElseThrow(() -> new NotificationException(NotificationErrorCode.NOT_FOUND)); // 임시 에러코드
	}
}