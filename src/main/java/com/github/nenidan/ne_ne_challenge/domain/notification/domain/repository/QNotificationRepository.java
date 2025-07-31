package com.github.nenidan.ne_ne_challenge.domain.notification.domain.repository;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.Notification;

public interface QNotificationRepository {
	List<Notification> findAllByUserIdAndIsReadWithType(
		Long userId,
		boolean isRead,
		String type,
		Long cursorId,
		int size);

	List<Notification> findAllByUserIdWithType(
		Long userId,
		String type,
		Long cursorId,
		int size);
}
