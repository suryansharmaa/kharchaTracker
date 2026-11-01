package com.Project.kharchaTracker.exception;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String userId) {
        super(ErrorCode.USER_NOT_FOUND, "User does not exist with Id : "+ userId);
    }
}
