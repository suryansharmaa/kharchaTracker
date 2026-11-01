package com.Project.kharchaTracker.exception;

public class ExpenseNotFoundException extends ResourceNotFoundException {
    public ExpenseNotFoundException(Long expenseId) {
        super(ErrorCode.EXPENSE_NOT_FOUND, "Expense does not exist with Id : " + expenseId);
    }
}
