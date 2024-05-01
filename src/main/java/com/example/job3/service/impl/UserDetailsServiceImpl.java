package com.example.job3.service.impl;


import com.example.job3.entity.Role;
import com.example.job3.entity.UserEntity;
import com.example.job3.repository.RoleRepository;
import com.example.job3.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService, ApplicationContextAware {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private ApplicationContext applicationContext;

    public UserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName))
                .collect(Collectors.toList());

        return new User(user.getUsername(), user.getEncodedPassword(), authorities);
    }



}
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        log.info("Loading user by username: {}", username);
//        if (!userRepository.existsByUsername(username)) {
//            log.warn("User not found with username: {}", username);
//            throw new UsernameNotFoundException("User not found");
//        }
//        UserEntity userEntity = userRepository.findByUsername(username);
//        log.info("Loaded user: {}", userEntity);
//        if (userEntity == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        List<GrantedAuthority> authorities = userEntity.getRoles().stream()
//                .map(roleEntity -> new SimpleGrantedAuthority("ROLE_" + roleEntity.getName()))
//                .collect(Collectors.toList());
//        return new User(userEntity.getUsername(), userEntity.getEncodedPassword(), authorities);
//    }
