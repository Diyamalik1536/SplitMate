
package com.splitmate.demo;

import com.splitmate.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentExpenseDemo {

    public static void main(String[] args) {

        // Enter your MySQL password when running the program
        String password = System.console() != null
                ? new String(System.console().readPassword("Enter MySQL password: "))
                : "";

        int groupId = 1;

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 3; i++) {
            final int expenseNumber = i;

            executor.submit(() -> {
                String sql = "INSERT INTO expenses "
                        + "(group_id, description, category, amount, paid_by_user_id) "
                        + "VALUES (?, ?, ?, ?, ?)";

                try (Connection con = DBConnection.getConnection(password);
                     PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setInt(1, groupId);
                    ps.setString(2, "Concurrent Expense " + expenseNumber);
                    ps.setString(3, "Other");
                    ps.setBigDecimal(4, new java.math.BigDecimal("100.00"));
                    ps.setInt(5, 1);

                    ps.executeUpdate();

                    System.out.println("Expense " + expenseNumber
                            + " added by " + Thread.currentThread().getName());

                } catch (SQLException e) {
                    System.out.println("Error adding expense "
                            + expenseNumber + ": " + e.getMessage());
                }
            });
        }

        executor.shutdown();
    }
}
