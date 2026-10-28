package com.Project.kharchaTracker.controller;

import com.Project.kharchaTracker.dto.CreateUserDto;
import com.Project.kharchaTracker.dto.UserDto;
import com.Project.kharchaTracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(createUserDto));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String userId, @RequestBody CreateUserDto updateUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId, updateUserDto));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> patchUser(@PathVariable String userId, @RequestBody CreateUserDto patchUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.patchUser(userId, patchUserDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/page")
    public ResponseEntity<List<UserDto>> getAllUsersPaginated(@RequestParam int page,
                                                              @RequestParam int pageSize,
                                                              @RequestParam String sortBy,
                                                              @RequestParam String direction) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsersPaginated(page, pageSize, sortBy, direction));
    }
}
