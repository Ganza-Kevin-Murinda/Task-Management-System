package com.tsm.task.dao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

class DBUtilTest {
    @Test
    void testDatabaseConnectionIsSuccessful() throws SQLException {
        Connection connection = DBUtil.getConnection();
        assertNotNull(connection, "Connection should not be null");
    }

    @Test
    void testDatabaseConnectionIsClosedProperly() {
        try (Connection connection = DBUtil.getConnection()) {
            assertNotNull(connection);
            assertTrue(connection.isValid(2), "Connection should be valid");
        } catch (Exception e) {
            fail("Exception occurred during DB connection test: " + e.getMessage());
        }
    }

    @Test
    void testQueryExecution() {
        try (Connection connection = DBUtil.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT 1")) {

            assertTrue(rs.next(), "ResultSet should have at least one row");
            int result = rs.getInt(1);
            assertEquals(1, result, "Expected result is 1");

        } catch (Exception e) {
            fail("Exception during query execution test: " + e.getMessage());
        }
    }

}