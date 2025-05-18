package com.tsm.task.factory;

import com.tsm.task.dao.impl.CategoryDAOImpl;
import com.tsm.task.dao.impl.TaskDAOImpl;
import com.tsm.task.dao.impl.UserDAOImpl;
import com.tsm.task.service.impl.CategoryServiceImpl;
import com.tsm.task.service.impl.TaskServiceImpl;
import com.tsm.task.service.impl.UserServiceImpl;
import com.tsm.task.service.interfaces.CategoryService;
import com.tsm.task.service.interfaces.TaskService;
import com.tsm.task.service.interfaces.UserService;

public class ServiceFactory {

    private static final TaskService taskService = new TaskServiceImpl(new TaskDAOImpl());
    private static final UserService userService = new UserServiceImpl(new UserDAOImpl());
    private static final CategoryService categoryService = new CategoryServiceImpl(new CategoryDAOImpl());

    private ServiceFactory() {
        // prevent instantiation
    }

    public static TaskService getTaskService() {
        return taskService;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static CategoryService getCategoryService() {
        return categoryService;
    }
}
