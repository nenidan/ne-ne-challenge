package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLRestriction;

import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.persistence.entity.embedded.AuditInfo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

    public ProfileEntity(AccountEntity account, Long id, String nickname, LocalDate birth, String bio,
        LocalDateTime createdAt, LocalDateTime updatedAt,
        LocalDateTime deletedAt) {
        super(createdAt, updatedAt, deletedAt);

        this.account = account;
        this.id = id;
        this.nickname = nickname;
        this.birth = birth;
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
