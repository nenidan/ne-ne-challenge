package com.github.nenidan.ne_ne_challenge.notification.infra.repository.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.notification.domain.entity.Notification;
import com.github.nenidan.ne_ne_challenge.notification.domain.interfaces.NotificationRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class NotificationImpl implements NotificationRepository {
	private final JpaNotificationRepository jpaNotificationRepository;

	@Override
	public List<Notification> findAllByUserIdAndIsReadWithType(Long userId, boolean isRead, String type, Long cursorId,
		int size) {
		return jpaNotificationRepository.findAllByUserIdAndIsReadWithType(userId, isRead, type, cursorId, size);
	}

	@Override
	public List<Notification> findAllByUserIdWithType(Long userId, String type, Long cursorId, int size) {
		return jpaNotificationRepository.findAllByUserIdWithType(userId, type, cursorId, size);
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
