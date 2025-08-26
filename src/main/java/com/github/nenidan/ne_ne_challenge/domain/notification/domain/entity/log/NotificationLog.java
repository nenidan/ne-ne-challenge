package com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log;

import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.fcm.Platform;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationLog extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long userId;

	private String title;
	private String content;

	@Enumerated(EnumType.STRING)
	private Platform platform;

	@Setter
	@Enumerated(EnumType.STRING)
	private NotificationStatus status;

	private int retryCount = 0;

	public NotificationLog(Long userId, String title, String content, Platform platform, NotificationStatus status) {
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.platform = platform;
		this.status = status;
	}

	public void success() {
		this.status = NotificationStatus.SUCCESS;
	}

	public void increaseRetryCount() {
		retryCount++;
	}
}
