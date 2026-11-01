package com.Project.kharchaTracker.exception;

public class EmailAlreadyExistsException extends ConflictException {
    public EmailAlreadyExistsException() {
        super(ErrorCode.EMAIL_ALREADY_EXISTS, "Email already exists.");
    }
}
