package com.tsm.task.dao.interfaces;

import com.tsm.task.model.EPriority;
import com.tsm.task.model.EStatus;
import com.tsm.task.model.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskDAO {
    boolean createTask(Task task) throws SQLException;

    Task getTaskById(int id) throws SQLException;

    List<Task> getTasksByUserId(int userId) throws SQLException;

    List<Task> getAllTasks() throws SQLException;

    boolean updateTask(Task task) throws SQLException;

    boolean deleteTask(int id) throws SQLException;

    List<Task> findTasksByStatus(EStatus status, String sortBy) throws SQLException;

    List<Task> findTasksByPriority(EPriority priority, String sortBy) throws SQLException;

    List<Task> findTasksByCategory(int categoryId, String sortBy) throws SQLException;

    List<Task> findTasksByUser(int userId, String sortBy) throws SQLException;
}
