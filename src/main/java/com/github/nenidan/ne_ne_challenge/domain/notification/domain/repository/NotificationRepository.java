package com.github.nenidan.ne_ne_challenge.domain.notification.domain.repository;

import java.util.List;
import java.util.Optional;

import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.Notification;

public interface NotificationRepository {

	void save(Notification notification);
	Optional<Notification> findById(Long id);

	List<Notification> searchByUserIdAndIsReadWithType(Long receiverId, boolean isRead, String type, Long cursorId, int size);
	List<Notification> searchByUserIdAndType(Long receiverId, String type, Long cursorId, int size);
}
