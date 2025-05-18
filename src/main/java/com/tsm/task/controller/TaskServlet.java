package com.tsm.task.controller;

import com.tsm.task.factory.ServiceFactory;
import com.tsm.task.model.Task;
import com.tsm.task.service.interfaces.TaskService;
import com.tsm.task.model.EPriority;
import com.tsm.task.model.EStatus;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "TaskServlet", urlPatterns = {"/tasks", "/tasks/*"})
public class TaskServlet extends AbstractController {
    private final TaskService taskService;

    public TaskServlet() {
        this.taskService = ServiceFactory.getTaskService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // Handle listing tasks with optional filters
            handleListTasks(req, resp);
        } else {
            // Fetch specific task by ID
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                Task task = taskService.getTaskById(id);
                if (task != null) {
                    writeJson(resp, task);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    writeJson(resp, new ErrorResponse("Task not found"));
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                writeJson(resp, new ErrorResponse("Invalid task ID"));
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Task task = readJson(req, Task.class);
            boolean created = taskService.createTask(task);
            if (created) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                writeJson(resp, task);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                writeJson(resp, new ErrorResponse("Failed to create task"));
            }
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeJson(resp, new ErrorResponse(e.getMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            Task task = readJson(req, Task.class);
            task.setTaskId(id);
            boolean updated = taskService.updateTask(task);
            if (updated) {
                writeJson(resp, task);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                writeJson(resp, new ErrorResponse("Task not found or not updated"));
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeJson(resp, new ErrorResponse("Invalid task ID"));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            boolean deleted = taskService.deleteTask(id);
            if (deleted) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                writeJson(resp, new ErrorResponse("Task not found"));
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeJson(resp, new ErrorResponse("Invalid task ID"));
        }
    }

    private void handleListTasks(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String statusParam = req.getParameter("status");
        String priorityParam = req.getParameter("priority");
        String categoryId = req.getParameter("categoryId");
        String userId = req.getParameter("userId");
        String sortBy = req.getParameter("sortBy");

        List<Task> tasks;
        try {
            if (statusParam != null) {
                tasks = taskService.getTasksByStatus(EStatus.valueOf(statusParam.toUpperCase()), sortBy);
            } else if (priorityParam != null) {
                tasks = taskService.getTasksByPriority(EPriority.valueOf(priorityParam.toUpperCase()), sortBy);
            } else if (categoryId != null) {
                tasks = taskService.getTasksByCategory(Integer.parseInt(categoryId), sortBy);
            } else if (userId != null) {
                tasks = taskService.getTasksByUser(Integer.parseInt(userId), sortBy);
            } else {
                tasks = taskService.getAllTasks();
            }
            writeJson(resp, tasks);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeJson(resp, new ErrorResponse("Invalid filter parameters"));
        }
    }
}

