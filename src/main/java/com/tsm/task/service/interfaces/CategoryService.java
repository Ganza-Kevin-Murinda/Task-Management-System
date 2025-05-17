package com.tsm.task.service.interfaces;

import com.tsm.task.model.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {
    boolean createCategory(Category category);
    Category getCategoryById(int id) throws SQLException;
    List<Category> getAllCategories();
    boolean updateCategory(Category category);
    boolean deleteCategory(int id);
}
