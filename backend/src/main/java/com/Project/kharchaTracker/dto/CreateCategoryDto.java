package com.Project.kharchaTracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryDto {

    @NotBlank(message = "Category name is required")
    @Size(min = 1, max = 50, message = "Category name must be between 1-50 characters")
    private String categoryName;
}
