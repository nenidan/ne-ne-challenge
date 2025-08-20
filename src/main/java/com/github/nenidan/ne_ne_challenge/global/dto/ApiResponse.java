package com.github.nenidan.ne_ne_challenge.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    @Schema(description = "응답 메시지", example = "응답 메시지 입니다.")
    private final String message;

    private final T data;

    private final LocalDateTime timestamp;

    private ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(HttpStatus code, String message, T data) {
        return ResponseEntity.status(code).body(new ApiResponse<>(message, data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(HttpStatus code, String message) {
        return ResponseEntity.status(code).body(new ApiResponse<>(message, null));
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(HttpStatus code, String message, T data, HttpHeaders headers) {
        return ResponseEntity.status(code).headers(headers).body(new ApiResponse<>(message, data));
    }
}
