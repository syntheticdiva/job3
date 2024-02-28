package com.example.job3.service;

import com.example.job3.dto.CreateUserDto;
import com.example.job3.dto.UpdateUserDto;
import com.example.job3.dto.UserDto;
import com.example.job3.entity.UserEntity;
import com.example.job3.repository.UserRepository;
import com.example.job3.utils.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserEntity> getUuidFromUserDto(UUID uuid) {
        var s = userRepository.findById(uuid);
        return s;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(CreateUserDto request) {
        userRepository.save(UserEntity.builder()
                .uuid(UUID.randomUUID())
                .name(request.getName())
                .surname(request.getSurname())
                .age(request.getAge())
                .build());
    }

//    public UserDto getUserById(UUID uuid) {
//        var userOptional = userRepository.findById(uuid);
//        return UserMapper.INSTANCE.toDtoWithUuid();
//    }

    public boolean deleteUser(UUID userDto) {
        Optional<UserEntity> userOptional = userRepository.findById(userDto);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return true;
        } else {
            return false;
        }
    }

    public UserDto updateUser(UpdateUserDto userDto) {
        Optional<UserEntity> userOptional = userRepository.findById(userDto.getId());
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