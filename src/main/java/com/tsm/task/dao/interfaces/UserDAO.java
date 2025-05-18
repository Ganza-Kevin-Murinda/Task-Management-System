package com.tsm.task.dao.interfaces;

import com.tsm.task.model.User;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    boolean createUser(User user) throws SQLException;
    List<User> getAllUsers();
}
