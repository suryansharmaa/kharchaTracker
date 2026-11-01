package com.Project.kharchaTracker.repository;

import com.Project.kharchaTracker.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserUserId(String userId);
    Optional<Expense> findByExpenseIdAndUserUserId(Long expenseId, String userId);
}
