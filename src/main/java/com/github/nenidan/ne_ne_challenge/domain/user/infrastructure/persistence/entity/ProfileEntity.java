package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity;

import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.embedded.AuditInfo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Table(name = "profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLRestriction("deleted_at IS NULL")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileEntity extends AuditInfo {

    @Id
    private Long id; // AccountEntity 의 id와 동일한 값

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id") // FK 이면서 PK
    private AccountEntity account;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private LocalDate birth;

    private String bio;

    private Long imageId;

    public static ProfileEntity of(AccountEntity account, String nickname, LocalDate birth, String bio) {
        return new ProfileEntity(
                account.getId() != null ? account.getId() : null,
                account,
                nickname,
                birth,
                bio,
                null
        );
    }

    public void updateProfile(ProfileEntity profileEntity) {
        this.nickname = profileEntity.getNickname() != null ? profileEntity.getNickname() : this.nickname;
        this.birth = profileEntity.getBirth() != null ? profileEntity.getBirth() : this.birth;
        this.bio = profileEntity.getBio() != null ? profileEntity.getBio() : this.bio;
    }
}
