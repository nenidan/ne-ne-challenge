package com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.repository.jpa;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationLog;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationStatus;

@Repository
public interface JpaNotificationLogRepository extends JpaRepository<NotificationLog, Long> {
	List<NotificationLog> findByStatusAndCreatedAtAfter(NotificationStatus status, LocalDateTime limit);
}
