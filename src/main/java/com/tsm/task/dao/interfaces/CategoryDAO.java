package com.tsm.task.dao.interfaces;

import com.tsm.task.model.Category;
import java.util.List;
import java.sql.SQLException;

public interface CategoryDAO {
    boolean createCategory(Category category) throws SQLException;

    Category getCategoryById(int id) throws SQLException;

    List<Category> getAllCategories() throws SQLException;

    boolean updateCategory(Category category) throws SQLException;

    boolean deleteCategory(int id) throws SQLException;
}
