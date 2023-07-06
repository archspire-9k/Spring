package com.example.springbootkeycloak.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final KeycloakLogoutHandler keycloakLogoutHandler;
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    public SecurityConfig(KeycloakLogoutHandler keycloakLogoutHandler) {
        this.keycloakLogoutHandler = keycloakLogoutHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/test/admin")
                        .hasRole("app_admin")
                        .requestMatchers("/test/user")
                        .hasAnyRole("app_admin", "app_user")
                        .anyRequest()
                        .permitAll()
                );
        http.oauth2Login(Customizer.withDefaults());
        http.logout(l -> l
                .addLogoutHandler(keycloakLogoutHandler)
                .logoutSuccessUrl("/")
        );
        return http.build();
    }
}
