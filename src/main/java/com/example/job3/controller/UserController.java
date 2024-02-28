package com.example.job3.controller;

import com.example.job3.dto.CreateUserDto;
import com.example.job3.dto.UpdateUserDto;
import com.example.job3.dto.UserDto;
import com.example.job3.entity.UserEntity;
import com.example.job3.service.UserService;
import com.example.job3.utils.ModelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
//    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
//        this.userMapper = userMapper;
    }

    @GetMapping("/{id}/uuid")
    public ResponseEntity<UserDto> getUserUuid(@PathVariable("id") UUID userId) {
        var userEntityOptional = userService.getUuidFromUserDto(userId);
        if (userEntityOptional.isPresent()) {
            UserDto userDto = ModelConverter.toUserDto(userEntityOptional.get());
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/all")
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto userDto) {
        userService.createUser(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @PutMapping("/update/{uuid}")
    public ResponseEntity<UserDto> updateUser(@PathVariable UUID uuid, @RequestBody UpdateUserDto userDto){
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