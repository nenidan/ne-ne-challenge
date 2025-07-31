package com.github.nenidan.ne_ne_challenge.domain.notification.domain.repository;

import java.util.Optional;

import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.Notification;

public interface NotificationRepository extends QNotificationRepository {

	void save(Notification notification);

	Optional<Notification> findById(Long id);
}
