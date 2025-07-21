package com.github.nenidan.ne_ne_challenge.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.nenidan.ne_ne_challenge.domain.notification.enttiy.Notification;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
