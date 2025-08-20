package com.github.nenidan.ne_ne_challenge.global.config;

import java.util.List;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@OpenAPIDefinition(
		security = {
				@SecurityRequirement(name = "Authorization"),
				@SecurityRequirement(name = "Refresh-Token")
		}
)
@SecurityScheme(
		name = "Refresh-Token",
		type = SecuritySchemeType.APIKEY,
		in = SecuritySchemeIn.HEADER,
		paramName = "Refresh-Token"
)
@SecurityScheme(
		name = "Authorization",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT"
)
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {

		Server localServer = new Server().url("http://localhost:8080/");
		Server prodServer = new Server().url("http://3.36.220.104:8080/");

		Info info = new Info()
			.title("Swagger API")       // API 문서 제목
			.version("v3.0.0")          // API 문서 버전
			.description("스웨거 API"); // API 문서 설명

		return new OpenAPI()
			.info(info)
			.servers(List.of(localServer, prodServer));
	}

}