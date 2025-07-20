package com.github.nenidan.ne_ne_challenge.domain.user.dto.request;

import com.github.nenidan.ne_ne_challenge.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class JoinRequest {

    private String email;

    private String password;

    private String nickname;

    private LocalDate birth;

    private String bio;


    public User toEntity() {
        return new User(email, password, nickname, birth, bio);
    }
}
