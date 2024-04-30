package com.example.job3.controller;

import com.example.job3.service.impl.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class LoginController {

    private UserDetailsServiceImpl userDetailsService;

    public void UserController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public LoginController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDetails> getUserByUsername(@PathVariable String username) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return ResponseEntity.ok(userDetails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}