package com.example.job3.controller;

import com.example.job3.dto.basket.BasketDto;
import com.example.job3.dto.user.CreateUserDto;
import com.example.job3.dto.user.UpdateUserDto;
import com.example.job3.dto.user.UserDto;
import com.example.job3.entity.BasketEntity;
import com.example.job3.entity.UserEntity;
import com.example.job3.service.UserService;
import com.example.job3.service.impl.UserServiceImpl;
import com.example.job3.utils.ModelConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;
    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }
    @GetMapping("/{id}/uuid")
    public ResponseEntity<UserDto> getUserUuid(@PathVariable("id") UUID userId) {
        Optional<UserEntity> userEntityOptional = userService.getUuidFromUserDto(userId);
//        if (userEntityOptional.isPresent()) {
//            var userDto = ModelConverter.toUserDto(userEntityOptional.get());
//            return ResponseEntity.ok(userDto);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            if (userEntity.getBasket() != null) {
                var userDto = ModelConverter.toUserDto(userEntity);
                return ResponseEntity.ok(userDto);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }
    @PostMapping("/create")
    public ResponseEntity<Void> createUser(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("age") Short age,
            @RequestParam ("basketId") UUID basketId) {

        CreateUserDto userDto = CreateUserDto.builder()
                .name(name)
                .surname(surname)
                .age(age)
                .basketId(basketId)
                .build();
        userService.createUser(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable ("uuid") UUID uuid,
            @RequestParam ("name") String name,
            @RequestParam ("surname") String surname,
            @RequestParam ("age") Short age) {
    UpdateUserDto userDto = UpdateUserDto.builder()
                .uuid(uuid)
                .name(name)
                .surname(surname)
                .age(age)
                .build();
        UserDto updateUser = userService.updateUser(userDto);
        if (updateUser != null) {
            return ResponseEntity.ok(updateUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable UUID uuid) {
        boolean deleted = userService.deleteUser(uuid);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}