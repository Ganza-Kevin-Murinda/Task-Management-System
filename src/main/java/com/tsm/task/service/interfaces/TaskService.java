package com.tsm.task.service.interfaces;

import com.tsm.task.model.EPriority;
import com.tsm.task.model.EStatus;
import com.tsm.task.model.Task;

import java.util.List;

public interface TaskService {
    boolean createTask(Task task);

    Task getTaskById(int id);

    List<Task> getTasksByUserId(int userId);

    List<Task> getAllTasks();

    boolean updateTask(Task task);

    boolean deleteTask(int id);

    // Filtering methods
    List<Task> getTasksByStatus(EStatus status, String sortBy);

    List<Task> getTasksByPriority(EPriority priority, String sortBy);

    List<Task> getTasksByCategory(int categoryId, String sortBy);

    List<Task> getTasksByUser(int userId, String sortBy);
}
