package com.tsm.task.service.impl;

import com.tsm.task.dao.interfaces.CategoryDAO;
import com.tsm.task.model.Category;
import com.tsm.task.service.interfaces.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = LogManager.getLogger(CategoryServiceImpl.class);
    private final CategoryDAO CategoryDAO;

    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.CategoryDAO = categoryDAO;
    }

    public void validateCategory(Category category){
        if (category.getName() == null || category.getName().isEmpty()) {
            logger.warn("Validation failed: Category name is required.");
            return;
        }
        if (category.getDescription() == null || category.getDescription().isEmpty()) {
            logger.warn("Validation failed: Category description is required.");
            return;
        }
        if (category.getColor() == null || category.getColor().isEmpty()) {
            logger.warn("Validation failed: Category color is required.");
        }

    }

    @Override
    public boolean createCategory(Category category) {
        //validate category
        validateCategory(category);
        try {
            logger.info("Category created: {}", category.getName());
            return CategoryDAO.createCategory(category);
        } catch (Exception e) {
            logger.error("Error while creating category: {}", category.getName(), e);
            return false;
        }
    }

    @Override
    public Category getCategoryById(int id) {
        try {
            Category category = CategoryDAO.getCategoryById(id);
            logger.info("Fetched {} category.", category.getName());
            return category;
        } catch (Exception e) {
            logger.error("Failed to fetch category", e);
            return null;
        }
    }

    @Override
    public List<Category> getAllCategories() {
        try {
            List<Category> categories = CategoryDAO.getAllCategories();
            logger.info("Fetched {} categories.", categories.size());
            return categories;
        } catch (Exception e) {
            logger.error("Failed to fetch categories", e);
            return null;
        }
    }

    @Override
    public boolean updateCategory(Category category) {
        //validate category
        validateCategory(category);
        try {
            logger.info("Category updated: {}", category.getName());
            return CategoryDAO.updateCategory(category);
        } catch (Exception e) {
            logger.error("Error while creating category: {}", category.getName(), e);
            return false;
        }
    }

    @Override
    public boolean deleteCategory(int id) {
        logger.debug("Attempting to delete category with ID: {}", id);
        try {
            logger.info("Category deleted with ID: {}", id);
            return CategoryDAO.deleteCategory(id);
        } catch (Exception e) {
            logger.error("Failed to delete category with ID: {}", id, e);
            return false;
        }
    }
}
