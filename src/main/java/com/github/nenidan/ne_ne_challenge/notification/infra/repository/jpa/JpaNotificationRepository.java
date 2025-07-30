package com.github.nenidan.ne_ne_challenge.notification.infra.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.nenidan.ne_ne_challenge.notification.domain.entity.Notification;
import com.github.nenidan.ne_ne_challenge.notification.domain.repository.QNotificationRepository;

public interface JpaNotificationRepository extends JpaRepository<Notification, Long>, QNotificationRepository {
}
