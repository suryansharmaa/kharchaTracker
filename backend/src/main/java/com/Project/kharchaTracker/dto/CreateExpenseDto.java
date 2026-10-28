package com.Project.kharchaTracker.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CreateExpenseDto {

    private String title;

    private BigDecimal amount;
    private LocalDate date;
    private Long categoryId;
}
