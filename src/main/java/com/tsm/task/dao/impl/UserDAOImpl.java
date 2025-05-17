package com.tsm.task.dao.impl;

import com.tsm.task.dao.DBUtil;
import com.tsm.task.dao.interfaces.UserDAO;
import com.tsm.task.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    private static final Logger logger = LogManager.getLogger(UserDAOImpl.class);

    @Override
    public boolean createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (full_name, username, email, department, join_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, user.getFull_name());
                stmt.setString(2, user.getUsername());
                stmt.setString(3, user.getEmail());
                stmt.setString(4, user.getDepartment());
                stmt.setObject(5, user.getJoinDate());

                int rows = stmt.executeUpdate();
                logger.info("User created: {}", user.getUsername());
                return rows > 0;
            }
        } catch (SQLException e) {
            logger.error("Error creating user", e);
            throw e;
        }
    }
}
