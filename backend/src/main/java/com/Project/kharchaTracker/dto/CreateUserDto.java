package com.Project.kharchaTracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {

    @NotBlank @NotNull @Size(max = 8)
    private String name;

    @NotBlank @NotNull @Email
    private String email;
}
