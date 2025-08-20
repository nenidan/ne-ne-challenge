package com.github.nenidan.ne_ne_challenge.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {

		Server localServer = new Server().url("https://localhost:8080/");
		Server prodServer = new Server().url("https://3.36.220.104:8080/");

		Info info = new Info()
			.title("Swagger API")       // API 문서 제목
			.version("v3.0.0")          // API 문서 버전
			.description("스웨거 API"); // API 문서 설명

		return new OpenAPI()
			.info(info)
			.servers(List.of(localServer, prodServer));
	}

}