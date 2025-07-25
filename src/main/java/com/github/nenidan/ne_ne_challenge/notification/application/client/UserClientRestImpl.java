package com.github.nenidan.ne_ne_challenge.notification.application.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserException;
import com.github.nenidan.ne_ne_challenge.dto.UserResponse;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserClientRestImpl implements UserClient {

	private final RestClient restClient = RestClient.builder().build();

	@Override
	public UserResponse findUserById(Long id) {
		try {
			ParameterizedTypeReference<ApiResponse<UserResponse>> responseType = new ParameterizedTypeReference<>() {};
			ApiResponse<UserResponse> apiResponse = restClient.get()
				.uri("http://localhost:8080/api/profiles/{userId}", id)
				// .headers(header -> header.setBearerAuth(token))
				.retrieve()
				.body(responseType);

			if (apiResponse == null || apiResponse.getData() == null) {
				log.warn("UserService 응답이 null입니다. id={}", id);
				throw new UserException(UserErrorCode.USER_NOT_FOUND);
			}

			return apiResponse.getData();

		} catch (HttpClientErrorException e) {
			log.error("UserService 클라이언트 오류 - Status: {}, Body: {}", e.getStatusCode(), e.getResponseBodyAsString());
			throw new UserException(UserErrorCode.USER_NOT_FOUND);
		} catch (HttpServerErrorException e) {
			log.error("UserService 서버 오류 - Status: {}, Body: {}", e.getStatusCode(), e.getResponseBodyAsString());
			throw new RuntimeException("User service 내부 오류 발생");
		} catch (RestClientException e) {
			log.error("UserService RestClient 통신 오류 - 메시지: {}", e.getMessage());
			throw new RuntimeException("User 서비스 통신 실패");
		} catch (Exception e) {
			log.error("UserService 알 수 없는 예외 발생", e);
			throw new RuntimeException("알 수 없는 오류 발생");
		}
	}
}
