package com.github.nenidan.ne_ne_challenge.notification.domain.interfaces;

import java.util.List;
import java.util.Optional;

import com.github.nenidan.ne_ne_challenge.notification.domain.entity.Notification;

public interface NotificationRepository{

	void save(Notification notification);

	Optional<Notification> findById(Long id);

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
