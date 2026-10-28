package com.Project.kharchaTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ExpenseDto {
    private Long expenseId;
    private String title;
    private BigDecimal amount;
    private LocalDate date;
    private UserDto user;
    private CategoryDto category;
}
