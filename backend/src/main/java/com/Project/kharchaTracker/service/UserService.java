package com.Project.kharchaTracker.service;

import com.Project.kharchaTracker.dto.CreateUserDto;
import com.Project.kharchaTracker.dto.UserDto;
import com.Project.kharchaTracker.entities.User;
import com.Project.kharchaTracker.exception.UserNotFoundException;
import com.Project.kharchaTracker.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDto createUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setName(createUserDto.getName());
        user.setEmail(createUserDto.getEmail());

        User savedUser = userRepository.save(user);
        return new UserDto(savedUser.getUserId(), savedUser.getName(), savedUser.getEmail());
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtoList = new ArrayList<>();

        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            userDtoList.add(new UserDto(user.getUserId(), user.getName(), user.getEmail()));
        }

        return userDtoList;
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new UserNotFoundException("User not found with Id : "+ userId));
        return new UserDto(user.getUserId(), user.getName(), user.getEmail());
    }

    @Transactional
    public UserDto updateUser(String userId, CreateUserDto updateUserDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found with ID : " + userId
        ));

        user.setName(updateUserDto.getName());
        user.setEmail(updateUserDto.getEmail());

        return new UserDto(user.getUserId(), user.getName(), user.getEmail());
    }

    @Transactional
    public UserDto patchUser(String userId, CreateUserDto patchUserDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found with ID : " + userId
        ));

        if (patchUserDto.getEmail() != null) user.setEmail(patchUserDto.getEmail());
        if (patchUserDto.getName() != null) user.setName(patchUserDto.getName());

        return new UserDto(user.getUserId(), user.getName(), user.getEmail());
    }

    @Transactional
    public void deleteUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found with ID : " + userId
        ));

        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsersPaginated(int page, int pageSize, String sortBy, String direction) {
        Sort sort;
        sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserDto> userDtoList = new ArrayList<>();
        for(User user : userPage) {
            userDtoList.add(new UserDto(user.getUserId(), user.getName(), user.getEmail()));
        }

        return userDtoList;
    }
}
