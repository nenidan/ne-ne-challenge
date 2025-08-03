package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity;

import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.embedded.AuditInfo;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.type.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Table(name = "account")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public static AccountEntity of(Long id, String email, String password, String kakaoId, String naverId, String googleId) {
        return new AccountEntity(
                id,
                email,
                password,
                Role.USER,
                kakaoId,
                naverId,
                googleId
        );
    }
}
