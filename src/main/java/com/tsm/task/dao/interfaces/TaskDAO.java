package com.tsm.task.dao.interfaces;

import com.tsm.task.model.Task;
import java.util.List;
import java.sql.SQLException;

public interface TaskDAO {
    boolean createTask(Task task) throws SQLException;

    Task getTaskById(int id) throws SQLException;

    List<Task> getTasksByUserId(int userId) throws SQLException;

    List<Task> getAllTasks() throws SQLException;

    boolean updateTask(Task task) throws SQLException;

    boolean deleteTask(int id) throws SQLException;
}
