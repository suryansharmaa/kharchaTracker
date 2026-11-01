package com.Project.kharchaTracker.exception;

public class CategoryAlreadyExistsException extends ConflictException {
    public CategoryAlreadyExistsException() {
        super(ErrorCode.CATEGORY_ALREADY_EXISTS, "Category already exists.");
    }
}
