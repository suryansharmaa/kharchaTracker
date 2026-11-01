package com.Project.kharchaTracker.controller;

import com.Project.kharchaTracker.dto.CreateUserDto;
import com.Project.kharchaTracker.dto.UserDto;
import com.Project.kharchaTracker.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
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
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
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
    public ResponseEntity<UserDto> updateUser(@PathVariable String userId, @Valid @RequestBody CreateUserDto updateUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId, updateUserDto));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> patchUser(@PathVariable String userId, @Valid @RequestBody CreateUserDto patchUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.patchUser(userId, patchUserDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/page")
    public ResponseEntity<List<UserDto>> getAllUsersPaginated(@RequestParam @Min(value = 0, message = "page must be 0 or more") int page,
                                                              @RequestParam @Min(value = 5, message = "pageSize must be at least 5")
                                                                            @Max(value = 50, message = "pageSize can't exceed 50") int pageSize,
                                                              @RequestParam(defaultValue = "name")
                                                                            @Pattern(regexp = "name|email", message = "sortBy must be name or email") String sortBy,
                                                              @RequestParam(defaultValue = "asc")
                                                                            @Pattern(regexp = "(?i)(asc|desc)", message = "direction must be asc or desc") String direction) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsersPaginated(page, pageSize, sortBy, direction));
    }
}
