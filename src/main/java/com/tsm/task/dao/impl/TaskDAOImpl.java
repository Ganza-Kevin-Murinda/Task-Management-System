package com.tsm.task.dao.impl;

import com.tsm.task.dao.DBUtil;
import com.tsm.task.dao.interfaces.TaskDAO;
import com.tsm.task.model.EPriority;
import com.tsm.task.model.EStatus;
import com.tsm.task.model.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskDAOImpl implements TaskDAO {
    private static final Logger logger = LogManager.getLogger(TaskDAOImpl.class);

    @Override
    public boolean createTask(Task task) throws SQLException {
        String sql = "INSERT INTO task (title, description, due_date, status, priority, creation_date, last_updated_date, user_id, category_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, task.getTitle());
                stmt.setString(2, task.getDescription());
                stmt.setObject(3, task.getDueDate());
                stmt.setString(4, task.getStatus().name());
                stmt.setString(5, task.getPriority().name());
                stmt.setObject(6, task.getCreationDate());
                stmt.setObject(7, task.getLastUpdatedDate());
                stmt.setInt(8, task.getUserId());
                stmt.setInt(9, task.getCategoryId());

                int rows = stmt.executeUpdate();
                logger.info("Task created: {}", task.getTitle());
                return rows > 0;
            }
        } catch (SQLException e) {
            logger.error("Error creating task", e);
            throw e;
        }
    }

    @Override
    public Task getTaskById(int id) throws SQLException {
        String sql = "SELECT * FROM task WHERE id=?";
        try (Connection conn = DBUtil.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return mapRowToTask(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Task> getTasksByUserId(int userId) throws SQLException {
        List<Task> list = new ArrayList<>();
        String sql = "SELECT * FROM task WHERE user_id=?";
        try (Connection conn = DBUtil.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    list.add(mapRowToTask(rs));
                }
            }
        }
        return list;
    }

    @Override
    public List<Task> getAllTasks() throws SQLException {
        List<Task> list = new ArrayList<>();
        String sql = "SELECT * FROM task";
        try (Connection conn = DBUtil.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    list.add(mapRowToTask(rs));
                }
            }
        }
        return list;
    }

    @Override
    public boolean updateTask(Task task) throws SQLException {
        String sql = "UPDATE task SET title=?, description=?, due_date=?, status=?, priority=?, last_updated_date=?, user_id=?, category_id=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, task.getTitle());
                stmt.setString(2, task.getDescription());
                stmt.setObject(3, task.getDueDate());
                stmt.setString(4, task.getStatus().name());
                stmt.setString(5, task.getPriority().name());
                stmt.setObject(6, task.getLastUpdatedDate());
                stmt.setInt(7, task.getUserId());
                stmt.setInt(8, task.getCategoryId());
                stmt.setInt(9, task.getTaskId());

                int rows = stmt.executeUpdate();
                logger.info("Task updated: {}", task.getTaskId());
                return rows > 0;
            }
        }
    }

    @Override
    public boolean deleteTask(int id) throws SQLException {
        String sql = "DELETE FROM task WHERE id=?";
        try (Connection conn = DBUtil.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                int rows = stmt.executeUpdate();
                logger.info("Task deleted with ID: {}", id);
                return rows > 0;
            }
        }
    }

    @Override
    public List<Task> findTasksByStatus(EStatus status, String sortBy) throws SQLException {
        String sql = "SELECT * FROM task WHERE status=? ORDER BY " + sortBy;
        return getFilteredTasks(sql, status.name());
    }

    @Override
    public List<Task> findTasksByPriority(EPriority priority, String sortBy) throws SQLException {
        String sql = "SELECT * FROM task WHERE priority=? ORDER BY " + sortBy;
        return getFilteredTasks(sql, priority.name());
    }

    @Override
    public List<Task> findTasksByCategory(int categoryId, String sortBy) throws SQLException {
        String sql = "SELECT * FROM task WHERE category_id=? ORDER BY " + sortBy;
        return getFilteredTasks(sql, categoryId);
    }

    @Override
    public List<Task> findTasksByUser(int userId, String sortBy) throws SQLException {
        String sql = "SELECT * FROM task WHERE user_id=? ORDER BY " + sortBy;
        return getFilteredTasks(sql, userId);
    }

    private List<Task> getFilteredTasks(String sql, Object param) throws SQLException {
        List<Task> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setObject(1, param);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    list.add(mapRowToTask(rs));
                }
            }
        }
        return list;
    }

    private Task mapRowToTask(ResultSet rs) throws SQLException {
        return new Task(
                rs.getInt("task_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getObject("due_date", LocalDate.class),
                EStatus.valueOf(rs.getString("status")),
                EPriority.valueOf(rs.getString("priority")),
                rs.getObject("creation_date", LocalDateTime.class),
                rs.getObject("last_updated_date", LocalDateTime.class),
                rs.getInt("user_id"),
                rs.getInt("category_id")
        );
    }
}
