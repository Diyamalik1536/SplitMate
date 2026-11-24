package com.splitmate;

import com.splitmate.dao.UserDAO;
import com.splitmate.model.User;
import com.splitmate.db.DBConnection;

import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.UUID;

public class UserTest {

    private static int passed = 0;
    private static int failed = 0;

    private static void check(String test, boolean result) {
        if (result) {
            System.out.println("[PASS] " + test);
            passed++;
        } else {
            System.out.println("[FAIL] " + test);
            failed++;
        }
    }

    public static void main(String[] args) {

        Console console = System.console();

        if (console == null) {
            System.out.println("Please run from terminal.");
            return;
        }

        String password = new String(
                console.readPassword("Enter MySQL password: ")
        );

        UserDAO userDAO = new UserDAO(password);

        String testEmail = "test_" + UUID.randomUUID()
                + "@example.com";

        int userId = -1;

        System.out.println("\n===== USER MANAGEMENT TEST =====");

        try {

            // Test 1: Add user
            User user = new User("Test User", testEmail);

            userId = userDAO.addUser(user);

            check("Add new user", userId > 0);

            // Test 2: Generated user ID
            check("Generated user ID",
                    userId > 0 && user.getUserId() == userId);

            // Test 3: Get user by ID
            User retrievedUser = userDAO.getUserById(userId);

            check("Get user by ID",
                    retrievedUser != null &&
                    retrievedUser.getUserId() == userId &&
                    retrievedUser.getName().equals("Test User") &&
                    retrievedUser.getEmail().equals(testEmail));

            System.out.println("Retrieved user: " + retrievedUser);

            // Test 4: Get all users
            List<User> users = userDAO.getAllUsers();

            boolean found = false;

            for (User u : users) {
                if (u.getUserId() == userId &&
                        u.getEmail().equals(testEmail)) {
                    found = true;
                }
            }

            check("Get all users", found);

            // Test 5: Existing email
            check("Existing email detection",
                    userDAO.emailExists(testEmail));

            // Test 6: Non-existing email
            check("Non-existing email detection",
                    !userDAO.emailExists(
                            UUID.randomUUID() + "@example.com"));

            // Test 7: Duplicate email
            boolean duplicateRejected = false;

            try {
                userDAO.addUser(
                        new User("Duplicate User", testEmail)
                );
            } catch (SQLIntegrityConstraintViolationException e) {
                duplicateRejected = true;
            }

            check("Duplicate email prevention",
                    duplicateRejected);

            // Test 8: Non-existing user ID
            check("Non-existing user ID",
                    userDAO.getUserById(-1) == null);

        } catch (SQLException e) {

            failed++;
            System.out.println("Database error: " + e.getMessage());

        } finally {

            // Remove temporary test records
            String sql = "DELETE FROM users WHERE email = ?";

            try (Connection con =
                         DBConnection.getConnection(password);
                 PreparedStatement ps =
                         con.prepareStatement(sql)) {

                ps.setString(1, testEmail);

                int rows = ps.executeUpdate();

                System.out.println(
                        "\nTest records removed: " + rows
                );

            } catch (SQLException e) {
                failed++;
                System.out.println(
                        "Cleanup error: " + e.getMessage()
                );
            }
        }

        System.out.println("\n===== TEST SUMMARY =====");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("========================");

        if (passed != 8 || failed != 0) {
            throw new AssertionError("User management tests failed.");
        }
    }
}