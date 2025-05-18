package com.tsm.task.service.impl;

import com.tsm.task.dao.interfaces.TaskDAO;
import com.tsm.task.model.EPriority;
import com.tsm.task.model.EStatus;
import com.tsm.task.model.Task;
import com.tsm.task.service.interfaces.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class TaskServiceImpl implements TaskService {
    private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);
    private final TaskDAO taskDAO;

    public TaskServiceImpl(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Override
    public boolean createTask(Task task) {
        try {
            validateTask(task);
            logger.debug("Creating task: {}", task);
            return taskDAO.createTask(task);
        } catch (IllegalArgumentException e) {
            logger.warn("Validation failed during task creation: {}", e.getMessage());
        } catch (SQLException e) {
            logger.error("SQL error creating task", e);
        }
        return false;
    }

    @Override
    public Task getTaskById(int id) {
        try {
            logger.debug("Fetching task by ID: {}", id);
            return taskDAO.getTaskById(id);
        } catch (SQLException e) {
            logger.error("SQL error fetching task by ID: {}", id, e);
        }
        return null;
    }

    @Override
    public List<Task> getTasksByUserId(int userId) {
        try {
            logger.debug("Fetching tasks for user ID: {}", userId);
            return taskDAO.getTasksByUserId(userId);
        } catch (SQLException e) {
            logger.error("SQL error fetching tasks for user ID: {}", userId, e);
        }
        return null;
    }

    @Override
    public List<Task> getAllTasks() {
        try {
            logger.debug("Fetching all tasks");
            return taskDAO.getAllTasks();
        } catch (SQLException e) {
            logger.error("SQL error fetching all tasks", e);
        }
        return null;
    }

    @Override
    public boolean updateTask(Task task) {
        try {
            validateTask(task);
            logger.debug("Updating task: {}", task);
            return taskDAO.updateTask(task);
        } catch (IllegalArgumentException e) {
            logger.warn("Validation failed during task update: {}", e.getMessage());
        } catch (SQLException e) {
            logger.error("SQL error updating task", e);
        }
        return false;
    }

    @Override
    public boolean deleteTask(int id) {
        try {
            logger.debug("Deleting task with ID: {}", id);
            return taskDAO.deleteTask(id);
        } catch (SQLException e) {
            logger.error("SQL error deleting task with ID: {}", id, e);
        }
        return false;
    }

    @Override
    public List<Task> getTasksByStatus(EStatus status, String sortBy) {
        try {
            logger.debug("Fetching tasks by status: {} sorted by: {}", status, sortBy);
            return taskDAO.findTasksByStatus(status, sortBy);
        } catch (SQLException e) {
            logger.error("SQL error fetching tasks by status", e);
        }
        return null;
    }

    @Override
    public List<Task> getTasksByPriority(EPriority priority, String sortBy) {
        try {
            logger.debug("Fetching tasks by priority: {} sorted by: {}", priority, sortBy);
            return taskDAO.findTasksByPriority(priority, sortBy);
        } catch (SQLException e) {
            logger.error("SQL error fetching tasks by priority", e);
        }
        return null;
    }

    @Override
    public List<Task> getTasksByCategory(int categoryId, String sortBy) {
        try {
            logger.debug("Fetching tasks by category ID: {} sorted by: {}", categoryId, sortBy);
            return taskDAO.findTasksByCategory(categoryId, sortBy);
        } catch (SQLException e) {
            logger.error("SQL error fetching tasks by category", e);
        }
        return null;
    }

    @Override
    public List<Task> getTasksByUser(int userId, String sortBy) {
        try {
            logger.debug("Fetching tasks by user ID: {} sorted by: {}", userId, sortBy);
            return taskDAO.findTasksByUser(userId, sortBy);
        } catch (SQLException e) {
            logger.error("SQL error fetching tasks by user", e);
        }
        return null;
    }

    private void validateTask(Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be null or empty");
        }
        if (task.getDueDate() == null) {
            throw new IllegalArgumentException("Due date is required");
        }
        if (task.getStatus() == null) {
            throw new IllegalArgumentException("Task status must be specified");
        }
        if (task.getPriority() == null) {
            throw new IllegalArgumentException("Task priority must be specified");
        }
        if (task.getUserId() <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (task.getCategoryId() <= 0) {
            throw new IllegalArgumentException("Invalid category ID");
        }
    }
}
