package com.github.nenidan.ne_ne_challenge.domain.notification.dto.request;

import com.github.nenidan.ne_ne_challenge.domain.notification.entity.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationRequest {
	private String title;
	private String content;
	private NotificationType notificationType;
	private Long receiverId;
	private Long senderId;
}