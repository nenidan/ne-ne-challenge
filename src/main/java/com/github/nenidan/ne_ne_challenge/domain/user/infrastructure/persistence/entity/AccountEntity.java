package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLRestriction;

import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.embedded.AuditInfo;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.type.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "account")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String kakaoId;

    @Column(unique = true)
    private String naverId;

    @Column(unique = true)
    private String googleId;

    public AccountEntity(Long id, String email, String password, Role role, String kakaoId,
        String naverId, String googleId, LocalDateTime createdAt, LocalDateTime updatedAt,
        LocalDateTime deletedAt) {
        super(createdAt, updatedAt, deletedAt);
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.kakaoId = kakaoId;
        this.naverId = naverId;
        this.googleId = googleId;
    }

    public AccountEntity(Long id, String email, String password, Role role, String kakaoId, String naverId,
        String googleId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.kakaoId = kakaoId;
        this.naverId = naverId;
        this.googleId = googleId;
    }

    public static AccountEntity of(Long id, String email, String password, Role role, String kakaoId, String naverId, String googleId) {
        return new AccountEntity(
                id,
                email,
                password,
                role,
                kakaoId,
                naverId,
                googleId
        );
    }
}
