package com.tsm.task.service.interfaces;

import com.tsm.task.model.User;

import java.util.List;

public interface UserService {
    boolean createUser(User user);
    List<User> getAllUsers();
}