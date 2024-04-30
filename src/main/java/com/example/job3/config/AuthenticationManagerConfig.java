package com.example.job3.config;

import com.example.job3.service.impl.UserDetailsServiceImpl;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Configuration
public class AuthenticationManagerConfig {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationManagerConfig(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = (PasswordEncoder) SecurityConfig.passwordEncoder();
    }

}
