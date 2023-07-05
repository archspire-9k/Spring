package com.example.springbootkeycloak.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    public static final String ADMIN = "admin";
    public static final String USER = "user";
    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers(HttpMethod.GET, "/test/anonymous","/test/anonymous/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/test/admin","/test/admin/**")
                        .hasRole(ADMIN)
                        .requestMatchers(HttpMethod.GET, "/test/user", "/test/user/**")
                        .hasAnyRole(ADMIN, USER)
                        .anyRequest()
                        .authenticated()
        );
        httpSecurity.oauth2ResourceServer( oauth2 ->
                oauth2.jwt( jwt ->
                jwt.jwtAuthenticationConverter(jwtAuthConverter))
        );
        httpSecurity.sessionManagement( session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        httpSecurity.cors(AbstractHttpConfigurer::disable);
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

}
