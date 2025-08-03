package com.github.nenidan.ne_ne_challenge.global.dto;

import java.util.List;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CursorResponse<T, S> {
    private List<T> content;
    private S nextCursor;
    private boolean hasNext;

    public static <T, S> CursorResponse<T, S> of(List<T> content, S nextCursor, boolean hasNext) {
        return new CursorResponse<>(content, nextCursor, hasNext);
    }

    /**
     * 기존 of에서 nextCursor와 hasNext를 알아서 넣어준다.
     * 
     * @param resultList: 커서 기준으로 정렬된 리스트, size보다 1 크게 조회한 값이어야 함
     * @param getCursorFieldFunction: 데이터 T에서 커서 필드 S를 꺼낼 방법
     * @param size: 페이징 크기  
     * @return 커서 방식 페이징 결과
     * @param <T> 내용의 데이터 타입
     * @param <S> 커서의 정렬 기준이 되는 필드
     */
    public static <T, S> CursorResponse<T, S> of(List<T> resultList, Function<T, S> getCursorFieldFunction, int size) {
        boolean hasNext = resultList.size() > size;
        List<T> content = hasNext ? resultList.subList(0, size) : resultList;
        S nextCursor = hasNext ? getCursorFieldFunction.apply(resultList.get(size)) : null;

        return new CursorResponse<>(content, nextCursor, hasNext);
    }
}