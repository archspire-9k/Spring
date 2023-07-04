package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;


@Configuration
@EnableWebSecurity
class SecurityConfig {

    private final KeycloakLogoutHandler keycloakLogoutHandler;
    private final JwtAuthConverter jwtAuthConverter;
    SecurityConfig(KeycloakLogoutHandler keycloakLogoutHandler, JwtAuthConverter jwtAuthConverter) {
        this.keycloakLogoutHandler = keycloakLogoutHandler;
        this.jwtAuthConverter = jwtAuthConverter;
    }
    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                {
                    auth.requestMatchers("/customers*")
                            .permitAll()
                            .anyRequest()
                            .hasRole("user");

                }
        );
        http.oauth2Login(Customizer.withDefaults())
                .logout(logout ->
                        logout.addLogoutHandler(keycloakLogoutHandler)
                                .logoutSuccessUrl("/"));
        http.oauth2ResourceServer(oauth2ResourceServer ->
        oauth2ResourceServer.jwt(Customizer.withDefaults())
        );
        return http.build();
    }
}
