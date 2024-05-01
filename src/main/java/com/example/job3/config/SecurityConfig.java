package com.example.job3.config;

import com.example.job3.entity.Role;
import com.example.job3.repository.RoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final RoleRepository roleRepository;

    public SecurityConfig(UserDetailsService userDetailsService, RoleRepository roleRepository) {
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(requests -> requests
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(httpBasic -> httpBasic.disable());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public void initRoles(RoleRepository roleRepository) {
        List<String> rolesToCreate = List.of("ROLE_USER", "ROLE_ADMIN");
        List<Role> existingRoles = roleRepository.findAll();
        List<Role> rolesToSave = rolesToCreate.stream()
                .filter(roleName -> existingRoles.stream()
                        .noneMatch(role -> role.getRoleName().equals(roleName)))
                .map(roleName -> new Role(UUID.randomUUID(), roleName))
                .collect(Collectors.toList());
        if (!rolesToSave.isEmpty()) {
            roleRepository.saveAll(rolesToSave);
        }
    }


}
