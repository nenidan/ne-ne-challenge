package com.github.nenidan.ne_ne_challenge.notification.application.dto.response;

import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.notification.domain.entity.Notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
	private Long id;
	private String title;
	private String content;
	private String type;
	private boolean isRead;
	private LocalDateTime createdAt;

	public static NotificationResponse from(Notification notification) {
		return new NotificationResponse(
			notification.getId(),
			notification.getTitle(),
			notification.getContent(),
			notification.getType().name(),
			notification.isRead(),
			notification.getCreatedAt()
		);
	}
}
