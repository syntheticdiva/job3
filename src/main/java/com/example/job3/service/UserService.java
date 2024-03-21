package com.example.job3.service;

import com.example.job3.dto.user.CreateUserDto;
import com.example.job3.dto.user.UpdateUserDto;
import com.example.job3.dto.user.UserDto;
import com.example.job3.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<UserEntity> getUuidFromUserDto(UUID uuid);

    List<UserDto> getAllUsers();

    void createUser(CreateUserDto userDto);

    boolean deleteUser(UUID userDto);

    UserDto updateUser(UpdateUserDto userDto);
}
