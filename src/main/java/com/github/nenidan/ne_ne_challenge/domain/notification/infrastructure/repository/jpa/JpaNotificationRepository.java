package com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.Notification;

public interface JpaNotificationRepository extends JpaRepository<Notification, Long> {
}
