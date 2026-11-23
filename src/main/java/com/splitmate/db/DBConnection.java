
package com.splitmate.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Project Name : SplitMate - Group Budget Planner
 * Module       : Database Connection (Shared)
 * Date         : 17-11-2026
 * Evaluation   : 1
 */

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/splitmate_db";

    private static final String USER = "root";

    public static Connection getConnection(String password)
            throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                "MySQL JDBC driver not found. Check Maven dependencies.",
                e
            );
        }

        return DriverManager.getConnection(
                URL, USER, password
        );
    }
}
