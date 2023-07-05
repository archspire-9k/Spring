package com.example.demo.security;

import com.example.demo.KeycloakLogoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
        http.authorizeHttpRequests(auth ->
                {
                    auth.requestMatchers("/").permitAll();
                    auth.requestMatchers("/logout").permitAll();
                    auth.requestMatchers("/customers*")
                            .hasRole("user")
                            .anyRequest()
                            .authenticated();
                }
        );
        http.oauth2Login(Customizer.withDefaults())
                .logout(logout ->
                        logout.addLogoutHandler(keycloakLogoutHandler)
                                .logoutSuccessUrl("/")
                );
        http.oauth2ResourceServer(oauth2ResourceServer ->
                oauth2ResourceServer.jwt(jwt ->
                        jwt.jwtAuthenticationConverter(jwtAuthConverter))
        );
        http.sessionManagement( mnt -> mnt.sessionCreationPolicy(SessionCreationPolicy.STATELESS) );
        http.cors(cors -> cors.configurationSource(corsConfigurationSource("http://localhost:8081")))
        http.csrf(csrf -> csrf.disable());


        // Return 401 (unauthorized) instead of 302 (redirect to login) when
        // authorization is missing or invalid
        http.exceptionHandling(eh -> eh.authenticationEntryPoint((request, response, authException) -> {
            response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Restricted Content\"");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }));
        return http.build();
    }


}
