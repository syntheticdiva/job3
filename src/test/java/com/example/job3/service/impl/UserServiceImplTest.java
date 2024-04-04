package com.example.job3.service.impl;

import com.example.job3.service.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.example.job3.dto.user.CreateUserDto;
import com.example.job3.dto.user.UpdateUserDto;
import com.example.job3.dto.user.UserDto;
import com.example.job3.entity.BasketEntity;
import com.example.job3.entity.UserEntity;
import com.example.job3.repository.BasketRepository;
import com.example.job3.repository.UserRepository;
import com.example.job3.utils.ModelConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BasketRepository basketRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, basketRepository);
    }

    @Test
    void testGetAllUsers_ReturnsAllUsers() {
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(createMockUserEntity(UUID.randomUUID(), "John", "Doe", (short) 25));
        userEntities.add(createMockUserEntity(UUID.randomUUID(), "Jane", "Smith", (short) 30));

        when(userRepository.findAll()).thenReturn(userEntities);

        List<UserDto> result = userService.getAllUsers();

        assertEquals(userEntities.size(), result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUuidFromUserDto_ExistingUser_ReturnsUserEntity() {
        UUID userId = UUID.randomUUID();
        UserEntity userEntity = createMockUserEntity(userId, "John", "Doe", (short) 25);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        Optional<UserEntity> result = userService.getUuidFromUserDto(userId);

        assertTrue(result.isPresent());
        assertEquals(userEntity.getUuid(), result.get().getUuid());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetUuidFromUserDto_NonExistingUser_ReturnsEmptyOptional() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<UserEntity> result = userService.getUuidFromUserDto(userId);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testCreateUser_ValidCreateUserDto_CreatesUserEntity() {
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setName("John");
        createUserDto.setSurname("Doe");
        createUserDto.setAge((short) 25);

        BasketEntity basketEntity = new BasketEntity();
        basketEntity.setUuid(UUID.randomUUID()); // Исправлен тип на UUID

        when(basketRepository.findById(any())).thenReturn(Optional.of(basketEntity));

        userService.createUser(createUserDto);

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testDeleteUser_ExistingUser_ReturnsTrue() {
        UUID userId = UUID.randomUUID();
        UserEntity userEntity = createMockUserEntity(userId, "John", "Doe", (short) 25);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        boolean result = userService.deleteUser(userId);

        assertTrue(result);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).delete(userEntity);
    }

    @Test
    void testDeleteUser_NonExistingUser_ReturnsFalse() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        boolean result = userService.deleteUser(userId);

        assertFalse(result);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).delete(any(UserEntity.class));
    }

    @Test
    void testUpdateUser_ExistingUser_ReturnsUpdatedUserDto() {
        UUID userId = UUID.randomUUID();
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setUuid(userId);
        updateUserDto.setName("Updated Name");

        UserEntity existingUserEntity = createMockUserEntity(userId, "John", "Doe", (short) 25);
        UserEntity updatedUserEntity = createMockUserEntity(userId, updateUserDto.getName(), "Doe", (short) 25);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUserEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUserEntity);

        UserDto result = userService.updateUser(updateUserDto);

        assertNotNull(result);
        assertEquals(updatedUserEntity.getUuid(), result.getUuid());
        assertEquals(updatedUserEntity.getName(), result.getName());
        assertEquals(updatedUserEntity.getSurname(), result.getSurname());
        assertEquals(updatedUserEntity.getAge(), result.getAge());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testUpdateUser_NonExistingUser_ReturnsNull() {
        UUID userId = UUID.randomUUID();
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setUuid(userId);
        updateUserDto.setName("Updated Name");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserDto result = userService.updateUser(updateUserDto);

        assertNull(result);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    private UserEntity createMockUserEntity(UUID uuid, String name, String surname, Short age) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUuid(uuid);
        userEntity.setName(name);
        userEntity.setSurname(surname);
        userEntity.setAge(age); // Исправлен тип на Short
        userEntity.setCreatedAt(Instant.now());
        userEntity.setUpdatedAt(Instant.now());
        return userEntity;
    }

}
