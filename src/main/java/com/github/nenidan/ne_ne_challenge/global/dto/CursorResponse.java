package com.github.nenidan.ne_ne_challenge.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CursorResponse<T, S> {
    private List<T> content;
    private S nextCursor;
    private boolean hasNext;

    public static <T, S> CursorResponse<T, S> of(List<T> content, S nextCursor, boolean hasNext) {
        return new CursorResponse<>(content, nextCursor, hasNext);
    }
}