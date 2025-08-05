package com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.request;

import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationLog;
import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.fcm.Platform;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RetryNotificationRequest {
	private Long notificationLogId;
	private Long receiverId;
	private String title;
	private String content;
	private Platform platform;
	private int retryCount;

	public static RetryNotificationRequest of(NotificationLog log) {
		return new RetryNotificationRequest(
			log.getId(),
			log.getUserId(),
			log.getTitle(),
			log.getContent(),
			log.getPlatform(),
			0
		);
	}
}
