package com.Project.kharchaTracker.service;

import com.Project.kharchaTracker.dto.CategoryDto;
import com.Project.kharchaTracker.dto.CreateExpenseDto;
import com.Project.kharchaTracker.dto.ExpenseDto;
import com.Project.kharchaTracker.dto.UserDto;
import com.Project.kharchaTracker.entities.Category;
import com.Project.kharchaTracker.entities.Expense;
import com.Project.kharchaTracker.entities.User;
import com.Project.kharchaTracker.repository.CategoryRepository;
import com.Project.kharchaTracker.repository.ExpenseRepository;
import com.Project.kharchaTracker.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ExpenseDto createExpense(String userId, CreateExpenseDto createExpenseDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found with ID : " + userId
        ));

        Category category = categoryRepository.findById(createExpenseDto.getCategoryId()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Category not found with ID : " + createExpenseDto.getCategoryId()
        ));

        Expense expense = new Expense();
        expense.setTitle(createExpenseDto.getTitle());
        expense.setAmount(createExpenseDto.getAmount());
        expense.setDate(createExpenseDto.getDate());

        expense.setUser(user);
        expense.setCategory(category);

        Expense savedExpense = expenseRepository.save(expense);
        UserDto savedUser = new UserDto(savedExpense.getUser().getUserId(),
                savedExpense.getUser().getName(), savedExpense.getUser().getEmail());

        CategoryDto savedCategory = new CategoryDto(savedExpense.getCategory().getCategoryId(),
                savedExpense.getCategory().getCategoryName());

        return new ExpenseDto(savedExpense.getExpenseID(), savedExpense.getTitle(), savedExpense.getAmount(),
                savedExpense.getDate(), savedUser, savedCategory);
    }
}
