package com.Project.kharchaTracker.exception;

public class CategoryNotFoundException extends ResourceNotFoundException {
    public CategoryNotFoundException(Long categoryId) {
        super(ErrorCode.CATEGORY_NOT_FOUND, "Category does not exist with Id : " + categoryId);
    }
}
