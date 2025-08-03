package com.github.nenidan.ne_ne_challenge.global.config;

import com.github.nenidan.ne_ne_challenge.domain.user.application.service.JwtTokenProvider;
import com.github.nenidan.ne_ne_challenge.global.security.handler.CustomAccessDeniedHandler;
import com.github.nenidan.ne_ne_challenge.global.security.handler.CustomAuthenticationEntryPoint;
import com.github.nenidan.ne_ne_challenge.global.security.jwt.JwtFilter;
import com.github.nenidan.ne_ne_challenge.global.security.jwt.JwtTokenAccessService;
import com.github.nenidan.ne_ne_challenge.global.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import static com.github.nenidan.ne_ne_challenge.global.security.auth.Role.ADMIN;
import static com.github.nenidan.ne_ne_challenge.global.security.auth.Role.USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final JwtTokenAccessService jwtTokenAccessService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 접근 제어
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/health", "/error").permitAll()

                .requestMatchers(HttpMethod.POST, "/api/accounts", "/api/accounts/login").permitAll()

                // review
                .requestMatchers(HttpMethod.POST, "/api/products/*/reviews").hasRole(USER.name())
                .requestMatchers(HttpMethod.PATCH, "/api/products/*/reviews").permitAll()

                .requestMatchers("/internal/**").permitAll()

                // stock
                .requestMatchers(HttpMethod.PATCH, "/api/products/*/stocks").hasRole(ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/api/products/*/stocks").permitAll()

                // product
                .requestMatchers(HttpMethod.POST, "/api/products").hasRole(ADMIN.name())
                .requestMatchers(HttpMethod.GET, "/api/products/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/api/products/*").hasRole(ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/api/products/*").hasRole(ADMIN.name())

                // order
                .requestMatchers(HttpMethod.POST, "/api/orders").hasRole(USER.name())
                .requestMatchers(HttpMethod.PATCH, "/api/orders/**").hasRole(USER.name())
                .requestMatchers(HttpMethod.GET, "/api/orders/**").hasRole(USER.name())

                .requestMatchers("/api/**").authenticated()

                .anyRequest().denyAll()
            )

            // 필터 등록
            .addFilterBefore(new JwtFilter(jwtUtil, jwtTokenAccessService), UsernamePasswordAuthenticationFilter.class)

            .exceptionHandling(configurer ->
                configurer
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler)
            )

            .build();
    }
}
