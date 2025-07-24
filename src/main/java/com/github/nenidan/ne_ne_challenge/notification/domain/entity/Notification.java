package com.github.nenidan.ne_ne_challenge.notification.domain.entity;

import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;
import com.github.nenidan.ne_ne_challenge.notification.domain.vo.ReceiverInfo;
import com.github.nenidan.ne_ne_challenge.notification.domain.vo.SenderInfo;

import jakarta.persistence.Embedded;
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
public class Notification extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String content;

	@Enumerated(EnumType.STRING)
	private NotificationType type;

	@Setter
	private boolean isRead;

	@Embedded
	private ReceiverInfo receiver;

	@Embedded
	private SenderInfo sender;

	public Notification(String title, String content, NotificationType type, Long receiverId, Long senderId) {
		this.title = title;
		this.content = content;
		this.type = type;
		this.receiver = new ReceiverInfo(receiverId);
		this.sender = new SenderInfo(senderId);
	}
}