package com.tsm.task.model;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class Task {
    private int taskId;
    private String title;
    private String description;
    private LocalDate dueDate;
    private EStatus status;
    private EPriority priority;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdatedDate;
    private int userId;
    private int categoryId;

    public Task() {}

    public Task(int taskId, String title, String description, LocalDate dueDate, EStatus status,
                EPriority priority, LocalDateTime creationDate, LocalDateTime lastUpdatedDate,
                int userId, int categoryId) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
        this.creationDate = creationDate;
        this.lastUpdatedDate = lastUpdatedDate;
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public EPriority getPriority() {
        return priority;
    }

    public void setPriority(EPriority priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Task{id=" + taskId + ", title='" + title + "', description='" + description + "', creationDate='" + creationDate + "', status=" + status +
                ", priority='" + priority + "', dueDate=" + dueDate + ", lastUpdatedDate='" + lastUpdatedDate + "', userId=" + userId + ", categoryId=" + categoryId + "}";
    }
}
