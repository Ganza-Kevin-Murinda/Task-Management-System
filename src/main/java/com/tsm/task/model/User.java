package com.tsm.task.model;

import java.time.LocalDate;

public class User {
    private int userId;
    private String username;
    private String full_name;
    private String email;
    private String department;
    private LocalDate joinDate;

    public User() {}

    public User(int userId, String username, String full_name, String email, String department, LocalDate joinDate) {
        this.userId = userId;
        this.username = username;
        this.full_name = full_name;
        this.email = email;
        this.department = department;
        this.joinDate = joinDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public String toString() {
        return "User{id=" + userId + ", username='" + username + "', full_name='" + full_name + "', email='" + email + "', department='" + department + "', joinDate=" + joinDate + "}";
    }
}
