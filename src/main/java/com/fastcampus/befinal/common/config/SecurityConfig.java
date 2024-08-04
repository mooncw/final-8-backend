package com.fastcampus.befinal.common.config;

import com.fastcampus.befinal.common.filter.JwtAuthenticationFilter;
import com.fastcampus.befinal.domain.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtService jwtService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(configurer -> configurer.disable());
        http.authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry
            .requestMatchers("/**").permitAll());
        http.addFilterBefore(new JwtAuthenticationFilter(jwtService), BasicAuthenticationFilter.class);
        http.sessionManagement(configurer -> configurer
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
