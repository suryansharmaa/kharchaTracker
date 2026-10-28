package com.Project.kharchaTracker.controller;

import com.Project.kharchaTracker.dto.CreateExpenseDto;
import com.Project.kharchaTracker.dto.ExpenseDto;
import com.Project.kharchaTracker.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{userId}/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<ExpenseDto> createExpense(@PathVariable String userId, @RequestBody CreateExpenseDto createExpenseDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.createExpense(userId, createExpenseDto));
    }
}
