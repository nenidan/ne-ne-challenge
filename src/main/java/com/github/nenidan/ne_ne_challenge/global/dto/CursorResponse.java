package com.github.nenidan.ne_ne_challenge.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CursorResponse<T> {
    private List<T> content;
    private Long nextCursor;
    private boolean hasNext;

    public static <T> CursorResponse<T> of(List<T> content, Long nextCursor, boolean hasNext) {
        return new CursorResponse<>(content, nextCursor, hasNext);
    }
}