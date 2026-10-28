package com.Project.kharchaTracker.controller;

import com.Project.kharchaTracker.dto.CreateExpenseDto;
import com.Project.kharchaTracker.dto.ExpenseDto;
import com.Project.kharchaTracker.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getAllExpenses(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(expenseService.getAllExpenses(userId));
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable String userId, @PathVariable Long expenseId,
                                                    @RequestBody CreateExpenseDto updateExpenseDto) {

        return ResponseEntity.status(HttpStatus.OK).body(expenseService.updateExpense(userId, expenseId, updateExpenseDto));
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpenseById(@PathVariable String userId, @PathVariable Long expenseId) {
        expenseService.deleteExpenseById(userId, expenseId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
