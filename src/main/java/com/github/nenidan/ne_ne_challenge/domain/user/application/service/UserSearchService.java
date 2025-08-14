package com.github.nenidan.ne_ne_challenge.domain.user.application.service;

import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.UserResult;

import java.util.List;

public interface UserSearchService {

    List<UserResult> findByKeywordWithCursor(String keyword, String cursor, int size);
}
