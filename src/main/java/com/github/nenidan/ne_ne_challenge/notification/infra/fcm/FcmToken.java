package com.github.nenidan.ne_ne_challenge.notification.infra.fcm;

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

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FcmToken extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long userId;

	private String token;

	@Enumerated(EnumType.STRING)
	private Platform platform;

	public FcmToken(Long userId, String token,Platform platform) {
		this.userId = userId;
		this.token = token;
		this.platform = platform;
	}

	public void updateToken(String token){
		this.token = token;
	}
}