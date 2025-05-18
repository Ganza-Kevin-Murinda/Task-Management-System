package com.tsm.task.service.impl;

import com.tsm.task.dao.interfaces.UserDAO;
import com.tsm.task.model.User;
import com.tsm.task.service.interfaces.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void validateUser(User user) {

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            logger.warn("Validation failed: Username is required.");
            return;
        }
        if (user.getFull_name() == null || user.getFull_name().isEmpty()) {
            logger.warn("Validation failed: Full Name is required.");
            return;
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            logger.warn("Validation failed: Email is required.");
            return;
        }
        if (user.getDepartment() == null || user.getDepartment().isEmpty()) {
            logger.warn("Validation failed: Department is required.");
            return;
        }
        if (user.getJoinDate() == null) {
            logger.warn("Validation failed: JoinDate is required.");
        }
    }

    @Override
    public boolean createUser(User user) {
        // validate user
        validateUser(user);
        try {
            logger.info("User created successfully: {}", user.getUsername());
            return userDAO.createUser(user);
        } catch (Exception e) {
            logger.error("Failed to create user: {}", user.getUsername(), e);
            return false;
        }

    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}
