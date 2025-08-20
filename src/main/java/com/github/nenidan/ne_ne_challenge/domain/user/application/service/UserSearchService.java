package com.github.nenidan.ne_ne_challenge.domain.user.application.service;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.UserResult;

public interface UserSearchService {

    List<UserResult> findByKeywordWithCursor(String keyword, String cursor, int size);
}
