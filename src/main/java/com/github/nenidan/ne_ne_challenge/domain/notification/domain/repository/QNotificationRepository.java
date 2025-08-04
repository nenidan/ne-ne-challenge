package com.github.nenidan.ne_ne_challenge.domain.notification.domain.repository;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.Notification;

public interface QNotificationRepository {
	List<Notification> searchByUserIdAndIsReadWithType(
		Long receiverId,
		boolean isRead,
		String type,
		Long cursorId,
		int size);

	List<Notification> searchByUserIdAndType(
		Long receiverId,
		String type,
		Long cursorId,
		int size);
}
