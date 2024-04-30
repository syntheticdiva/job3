package com.example.job3.config;

import com.example.job3.entity.UserEntity;
import com.example.job3.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasswordUpdater {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void updatePasswords() {
        Iterable<UserEntity> users = userRepository.findAll();
        for (UserEntity user : users) {
            String plainPassword = user.getEncodedPassword();
            String encodedPassword = PasswordEncoder.encodePassword(plainPassword);
            user.setEncodedPassword(encodedPassword);
        }
        userRepository.saveAll(users);
    }
}