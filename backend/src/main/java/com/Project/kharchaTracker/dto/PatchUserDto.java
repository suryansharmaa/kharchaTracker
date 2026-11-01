package com.Project.kharchaTracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PatchUserDto {

    @Size(min = 4, max = 100, message = "Name must be at most 100 characters")
    private String name;

    @Email(message = "Email must be a valid email address")
    @Size(max = 254, message = "Email must be at most 254 characters")
    private String email;
}
