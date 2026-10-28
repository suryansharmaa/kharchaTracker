package com.Project.kharchaTracker.repository;

import com.Project.kharchaTracker.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
