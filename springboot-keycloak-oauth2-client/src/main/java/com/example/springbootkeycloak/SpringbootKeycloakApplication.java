package com.example.springbootkeycloak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@RestController
@SpringBootApplication
public class SpringbootKeycloakApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootKeycloakApplication.class, args);
    }

    @GetMapping("/user")
    public Map<String,Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
