package com.example.job3.controller;

import com.example.job3.entity.BasketEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.example.job3.dto.user.CreateUserDto;
import com.example.job3.dto.user.UpdateUserDto;
import com.example.job3.dto.user.UserDto;
import com.example.job3.entity.UserEntity;
import com.example.job3.service.impl.UserServiceImpl;
import com.example.job3.utils.ModelConverter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    public UserControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserUuid_UserExistsWithBasket() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UserEntity userEntity = new UserEntity();
        userEntity.setBasket(new BasketEntity()); // Замените на ваш класс BasketEntity
        Optional<UserEntity> userEntityOptional = Optional.of(userEntity);

        when(userService.getUuidFromUserDto(userId)).thenReturn(userEntityOptional);

        // Act
        ResponseEntity<UserDto> response = userController.getUserUuid(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).getUuidFromUserDto(userId);
    }

    @Test
    void testGetUserUuid_UserDoesNotExist() {
        // Arrange
        UUID userId = UUID.randomUUID();
        Optional<UserEntity> userEntityOptional = Optional.empty();

        when(userService.getUuidFromUserDto(userId)).thenReturn(userEntityOptional);

        // Act
        ResponseEntity<UserDto> response = userController.getUserUuid(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).getUuidFromUserDto(userId);
    }

    @Test
    void testGetUserUuid_UserExistsWithoutBasket() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UserEntity userEntity = new UserEntity();
        Optional<UserEntity> userEntityOptional = Optional.of(userEntity);

        when(userService.getUuidFromUserDto(userId)).thenReturn(userEntityOptional);

        // Act
        ResponseEntity<UserDto> response = userController.getUserUuid(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).getUuidFromUserDto(userId);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<UserDto> expectedUsers = new ArrayList<>();
        // Здесь добавьте логику для заполнения expectedUsers с ожидаемыми значениями

        when(userService.getAllUsers()).thenReturn(expectedUsers);

        // Act
        List<UserDto> actualUsers = userController.getAllUsers();

        // Assert
        assertEquals(expectedUsers, actualUsers);
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testCreateUser() {
        // Arrange
        String name = "John";
        String surname = "Doe";
        Short age = 25;
        UUID basketId = UUID.randomUUID();

        CreateUserDto userDto = CreateUserDto.builder()
                .name(name)
                .surname(surname)
                .age(age)
                .basketId(basketId)
                .build();


        ResponseEntity<Void> response = userController.createUser(name, surname, age, basketId);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(userService, times(1)).createUser(userDto);
    }

    @Test
    void testUpdateUser_UserExists() {
        // Arrange
        UUID userId = UUID.randomUUID();
        String name = "John";
        String surname = "Doe";
        Short age = 25;

        UpdateUserDto userDto = UpdateUserDto.builder()
                .uuid(userId)
                .name(name)
                .surname(surname)
                .age(age)
                .build();

        UserDto expectedUser = new UserDto();
        // Здесь добавьте логику для заполнения expectedUser с ожидаемыми значениями

        when(userService.updateUser(userDto)).thenReturn(expectedUser);

        // Act
        ResponseEntity<UserDto> response = userController.updateUser(userId, name, surname, age);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUser, response.getBody());
        verify(userService, times(1)).updateUser(userDto);
    }

    @Test
    void testUpdateUser_UserDoesNotExist() {
        // Arrange
        UUID userId = UUID.randomUUID();
        String name = "John";
        String surname = "Doe";
        Short age = 25;

        UpdateUserDto userDto = UpdateUserDto.builder()
                .uuid(userId)
                .name(name)
                .surname(surname)
                .age(age)
                .build();

        when(userService.updateUser(userDto)).thenReturn(null);

        // Act
        ResponseEntity<UserDto> response = userController.updateUser(userId, name, surname, age);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).updateUser(userDto);
    }

    @Test
    void testDeleteUser_UserExists() {
        // Arrange
        UUID userId = UUID.randomUUID();

        when(userService.deleteUser(userId)).thenReturn(true);

        // Act
        ResponseEntity<UserDto> response = userController.deleteUser(userId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void testDeleteUser_UserDoesNotExist() {
        // Arrange
        UUID userId = UUID.randomUUID();

        when(userService.deleteUser(userId)).thenReturn(false);

        // Act
        ResponseEntity<UserDto> response = userController.deleteUser(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).deleteUser(userId);
    }
}
