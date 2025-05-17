package com.tsm.task.dao.impl;

import com.tsm.task.dao.DBUtil;
import com.tsm.task.dao.interfaces.CategoryDAO;
import com.tsm.task.model.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {
    private static final Logger logger = LogManager.getLogger(CategoryDAOImpl.class);

    @Override
    public boolean createCategory(Category category) throws SQLException {
        String sql = "INSERT INTO category (name, color, description) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, category.getName());
                stmt.setString(2, category.getColor());
                stmt.setString(3, category.getDescription());

                int rows = stmt.executeUpdate();
                logger.info("Category created: {}", category.getName());
                return rows > 0;
            }
        } catch (SQLException e) {
            logger.error("Error creating category", e);
            throw e;
        }
    }

    @Override
    public Category getCategoryById(int id) throws SQLException {
        String sql = "SELECT * FROM category WHERE id = ?";
        try (Connection conn = DBUtil.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new Category(
                            rs.getInt("categoryId"),
                            rs.getString("name"),
                            rs.getString("color"),
                            rs.getString("description")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Category> getAllCategories() throws SQLException {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM category";
        try (Connection conn = DBUtil.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    list.add(new Category(
                            rs.getInt("categoryId"),
                            rs.getString("name"),
                            rs.getString("color"),
                            rs.getString("description")
                    ));
                }
            }
        }
        return list;
    }

    @Override
    public boolean updateCategory(Category category) throws SQLException {
        String sql = "UPDATE category SET name=?, color=?, description=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, category.getName());
                stmt.setString(2, category.getColor());
                stmt.setString(3, category.getDescription());
                stmt.setInt(4, category.getCategoryId());

                int rows = stmt.executeUpdate();
                logger.info("Category updated: {}", category.getName());
                return rows > 0;
            }
        }
    }

    @Override
    public boolean deleteCategory(int id) throws SQLException {
        String sql = "DELETE FROM category WHERE id=?";
        try (Connection conn = DBUtil.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                int rows = stmt.executeUpdate();
                logger.info("Category deleted with ID: {}", id);
                return rows > 0;
            }
        }
    }
}
