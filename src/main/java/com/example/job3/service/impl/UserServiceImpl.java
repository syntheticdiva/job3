package com.example.job3.service.impl;

import com.example.job3.dto.user.CreateUserDto;
import com.example.job3.dto.user.UpdateUserDto;
import com.example.job3.dto.user.UserDto;
import com.example.job3.entity.UserEntity;
import com.example.job3.repository.UserRepository;
import com.example.job3.service.UserService;
import com.example.job3.utils.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }
@Override
    public Optional<UserEntity> getUuidFromUserDto(UUID uuid) {
        return userRepository.findById(uuid);
    }
@Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
@Override
    public void createUser(CreateUserDto request) {
        userRepository.save(UserEntity.builder()
                .uuid(UUID.randomUUID())
                .name(request.getName())
                .surname(request.getSurname())
                .age(request.getAge())
                .createdAt(Instant.now())
                .build());
    }
@Override
    public boolean deleteUser(UUID userDto) {
        Optional<UserEntity> userOptional = userRepository.findById(userDto);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return true;
        } else {
            return false;
        }
    }
@Override
    public UserDto updateUser(UpdateUserDto userDto) {
        Optional<UserEntity> userOptional = userRepository.findById(userDto.getUuid());
        if (userOptional.isPresent()) {
            UserEntity userEntity = userOptional.get();
            userEntity.setName(userDto.getName());
            userEntity.setSurname(userDto.getSurname());
            userEntity.setAge(userDto.getAge());
            userEntity.setUpdatedAt(Instant.now());
            UserEntity updatedUser = userRepository.save(userEntity);
            return ModelConverter.toUserDto(updatedUser);
        } else {
            return null;
        }
    }
}
