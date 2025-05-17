package com.tsm.task.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBUtil {
    private static final Dotenv dotenv = Dotenv.load();
    private static final Logger logger = LogManager.getLogger(DBUtil.class);
    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        try {
            logger.debug("Opening DB connection");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            logger.error("Failed to connect to database", e);
            return null;
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                logger.debug("DB connection closed");
            } catch (SQLException e) {
                logger.error("Error closing DB connection", e);
            }
        }
    }
}

