package com.Project.kharchaTracker.exception;

import lombok.Getter;

public abstract class ResourceNotFoundException extends RuntimeException {

    @Getter
    private final ErrorCode code;

    protected ResourceNotFoundException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }
}