package com.example.springbootkeycloak.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/anonymous")
    public ResponseEntity<String> getAnonymous() {
        return ResponseEntity.ok("Hello Anonymous");
    }
    @GetMapping("/admin")
    public ResponseEntity<String> getAdmin(@AuthenticationPrincipal OAuth2User principal) {
        String userName = principal.getAttribute("name");
        String userEmail = principal.getAttribute("email");
        return ResponseEntity.ok("Hello Admin \nUser Name : " + userName + "\nUser Email : " + userEmail);
    }
    @GetMapping("/user")
    public ResponseEntity<String> getUser(@AuthenticationPrincipal OAuth2User principal) {
        String userName = principal.getAttribute("name");
        String userEmail = principal.getAttribute("email");
        return ResponseEntity.ok("Hello User \nUser Name : " + userName + "\nUser Email : " + userEmail);
    }
}
