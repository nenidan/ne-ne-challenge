package com.github.nenidan.ne_ne_challenge.domain.notification.service.fcm;

import org.springframework.stereotype.Service;

import com.github.nenidan.ne_ne_challenge.domain.notification.enttiy.fcm.FcmToken;
import com.github.nenidan.ne_ne_challenge.domain.notification.exception.NotificationErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.notification.exception.NotificationException;
import com.github.nenidan.ne_ne_challenge.domain.notification.repository.fcm.FcmTokenRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class FcmService {
	private final FcmTokenRepository fcmTokenRepository;

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
