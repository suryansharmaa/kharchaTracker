package com.Project.kharchaTracker.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CreateExpenseDto {

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 100, message = "Title must be between 1-100 characters")
    private String title;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Amount allows up to 10 digits and 2 decimals")
    private BigDecimal amount;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Category id is required")
    private Long categoryId;
}
