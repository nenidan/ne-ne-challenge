package com.github.nenidan.ne_ne_challenge.global.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PagedResponse<T> {
    private List<T> content;
    private long totalElements;
    private int totalPages;
    private int size;
    private long number;

    public static <T> PagedResponse<T> toPagedResponse(Page<T> page) {
        return new PagedResponse<>(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getSize(),
                page.getNumber() + 1L // 0-based index → 1-based index
        );
    }
}
