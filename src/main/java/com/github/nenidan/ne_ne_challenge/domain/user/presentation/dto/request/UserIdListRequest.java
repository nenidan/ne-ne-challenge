package com.github.nenidan.ne_ne_challenge.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserIdListRequest {

    @NotNull
    private List<Long> userIdList;
}
