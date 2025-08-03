package com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.request;

import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.NotificationType;
import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.fcm.Platform;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SendNotificationRequest {
	private String title;
	private String content;
	private NotificationType notificationType;
	private Long receiverId;
	private Long senderId;
	private Platform platform;
}
