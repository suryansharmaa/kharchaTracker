package com.Project.kharchaTracker.repository;

import com.Project.kharchaTracker.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
