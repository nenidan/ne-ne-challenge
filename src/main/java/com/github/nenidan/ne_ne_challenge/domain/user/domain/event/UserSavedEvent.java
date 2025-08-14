package com.github.nenidan.ne_ne_challenge.domain.user.domain.event;

import com.github.nenidan.ne_ne_challenge.domain.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSavedEvent {

    private final User user;
}
