package com.github.nenidan.ne_ne_challenge.domain.user.domain.model;

import java.time.LocalDate;

import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.vo.AuditInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Profile {
    private final UserId id;
    private String nickname;
    private LocalDate birth;
    private String bio;
    private Long imageId;
    private final AuditInfo auditInfo;

    public static Profile of(String nickname, LocalDate birth, String bio) {
        return new Profile(
            null,
            nickname,
            birth,
            bio,
            null,
            null
        );
    }

    public static Profile of(String nickname, LocalDate birth, String bio, Long imageId) {
        return new Profile(
            null,
            nickname,
            birth,
            bio,
            imageId,
            null
        );
    }

    public void updateProfile(Profile profile) {
        this.nickname = profile.getNickname()!= null ? profile.getNickname() : this.nickname;
        this.birth = profile.getBirth()!= null ? profile.getBirth() : this.birth;
        this.bio = profile.getBio() != null ? profile.getBio() : this.bio;
    }
}
