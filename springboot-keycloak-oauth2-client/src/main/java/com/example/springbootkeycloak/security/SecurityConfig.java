package com.example.springbootkeycloak.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Order
    public SecurityFilterChain indexFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/", "/error")
                .authorizeHttpRequests(auth -> auth
                        .anyRequest()
                        .anonymous()
                );
        return http.build();
    }

//    @Bean
//    public SecurityFilterChain testFilterChain(HttpSecurity http) throws Exception {
//        http
//                .securityMatcher("/test/**")
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().authenticated()
//                );
//        http.oauth2Login();
//        return http.build();
//    }

}
