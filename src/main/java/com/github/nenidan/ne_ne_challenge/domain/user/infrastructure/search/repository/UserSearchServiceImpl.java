package com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.search.repository;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.nenidan.ne_ne_challenge.domain.user.application.dto.UserResult;
import com.github.nenidan.ne_ne_challenge.domain.user.application.service.UserSearchService;
import com.github.nenidan.ne_ne_challenge.domain.user.infrastructure.search.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSearchServiceImpl implements UserSearchService {

    private final CustomUserDocumentRepository customUserDocumentRepository;

    @Override
    public List<UserResult> findByKeywordWithCursor(String keyword, String cursor, int size) {
        return customUserDocumentRepository.findByKeywordWithCursor(keyword, cursor, size)
                .stream().map(UserMapper::toDto).toList();
    }

}
