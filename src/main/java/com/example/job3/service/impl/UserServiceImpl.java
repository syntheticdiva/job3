package com.example.job3.service.impl;

import com.example.job3.dto.user.CreateUserDto;
import com.example.job3.dto.user.UpdateUserDto;
import com.example.job3.dto.user.UserDto;
import com.example.job3.entity.BasketEntity;
import com.example.job3.entity.Role;
import com.example.job3.entity.UserEntity;
import com.example.job3.repository.BasketRepository;

import com.example.job3.repository.RoleRepository;
import com.example.job3.repository.UserRepository;
import com.example.job3.service.UserService;
import com.example.job3.utils.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final BasketRepository basketRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, BasketRepository basketRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.basketRepository = basketRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public Optional<UserEntity> getUuidFromUserDto(UUID uuid) {
        return userRepository.findById(uuid);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(ModelConverter::toUserDto)
                .collect(Collectors.toList());

    }
    @Override
    public void createUser(CreateUserDto createUserDto) {
        BasketEntity basketEntity = basketRepository.findById(createUserDto.getBasketId()).orElse(null);
        if (basketEntity != null) {
        UserEntity userEntity = UserEntity.builder()
                .uuid(UUID.randomUUID())
                .name(createUserDto.getName())
                .surname(createUserDto.getSurname())
                .age(createUserDto.getAge())
                .createdAt(Instant.now())
                .basket(basketEntity)
                .build();
        userRepository.save(userEntity);}
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
    public UserEntity createUser(UserEntity user, Role.RoleName roleName) {
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role name: " + roleName));

        user.setEncodedPassword(passwordEncoder.encode(user.getEncodedPassword()));
        user.getRoles().add(role);

        return userRepository.save(user);
    }
}