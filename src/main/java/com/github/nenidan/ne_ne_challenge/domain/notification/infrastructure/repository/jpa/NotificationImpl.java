package com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.repository.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.Notification;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.repository.NotificationRepository;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.repository.QNotificationRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class NotificationImpl implements NotificationRepository {
	private final JpaNotificationRepository jpaNotificationRepository;
	private final QNotificationRepository qNotificationRepository;

	public List<Notification> searchByUserIdAndIsReadWithType(Long receiverId, boolean isRead, String type, Long cursorId,
		int size) {
		return qNotificationRepository.searchByUserIdAndIsReadWithType(receiverId, isRead, type, cursorId, size);
	}

	public List<Notification> searchByUserIdAndType(Long receiverId, String type, Long cursorId, int size) {
		return qNotificationRepository.searchByUserIdAndType(receiverId, type, cursorId, size);
	}

	@Override
	public void save(Notification notification) {
		jpaNotificationRepository.save(notification);
	}

	@Override
	public Optional<Notification> findById(Long id) {
		return jpaNotificationRepository.findById(id);
	}

}
