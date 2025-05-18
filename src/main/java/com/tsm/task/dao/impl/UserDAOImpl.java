package com.tsm.task.dao.impl;

import com.tsm.task.dao.DBUtil;
import com.tsm.task.dao.interfaces.UserDAO;
import com.tsm.task.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DBUtil.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setFull_name(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setDepartment(rs.getString("department"));
                    user.setJoinDate(rs.getDate("join_date").toLocalDate());
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            logger.error("Error creating category", e);
        }

        return users;
    }
}
