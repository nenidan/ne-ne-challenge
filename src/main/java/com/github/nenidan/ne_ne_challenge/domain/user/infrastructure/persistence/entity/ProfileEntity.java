package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.type.Sex;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;

import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.embedded.AuditInfo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLRestriction("deleted_at IS NULL")
public class ProfileEntity extends AuditInfo {

    @Id
    private Long id; // AccountEntity 의 id와 동일한 값

    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @MapsId
    @JoinColumn(name = "id") // FK 이면서 PK
    private AccountEntity account;

    @Column(nullable = false, unique = true, length = 20)
    private String nickname;

    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private Sex sex;

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

    public ProfileEntity(AccountEntity account, Long id, String nickname, LocalDate birth, String sex, String bio,
        LocalDateTime createdAt, LocalDateTime updatedAt,
        LocalDateTime deletedAt) {
        super(createdAt, updatedAt, deletedAt);

        this.account = account;
        this.id = id;
        this.nickname = nickname;
        this.birth = birth;
        this.sex = Sex.of(sex);
        this.bio = bio;
    }

    public ProfileEntity(Long id, AccountEntity account, String nickname, LocalDate birth, String bio, Long imageId) {
        this.id = id;
        this.account = account;
        this.nickname = nickname;
        this.birth = birth;
        this.bio = bio;
        this.imageId = imageId;
    }
}
