package com.example.springbootkeycloak.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/anonymous")
    public ResponseEntity<String> getAnonymous() {
        return null;
    }
    @GetMapping("/admin")
    public ResponseEntity<String> getAdmin() {
        return null;
    }
    @GetMapping("/user")
    public ResponseEntity<String> getUser() {
        return null;
    }
}
