package com.github.nenidan.ne_ne_challenge.domain.notification.dto.request;

import com.github.nenidan.ne_ne_challenge.domain.notification.enttiy.NotificationType;

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
}
