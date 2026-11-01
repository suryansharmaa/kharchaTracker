package com.Project.kharchaTracker.exception;

import lombok.Getter;

public abstract class ConflictException extends RuntimeException {

    @Getter
    private final ErrorCode code;

    protected ConflictException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }
}
