
package com.splitmate.service;

import com.splitmate.db.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryReport {

    // Display total expenses for each category in a group
    public void displayCategoryReport(int groupId, String password) {

        String sql = "SELECT category, SUM(amount) AS total "
                   + "FROM expenses WHERE group_id = ? "
                   + "GROUP BY category ORDER BY category";

        try (Connection con = DBConnection.getConnection(password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, groupId);

            try (ResultSet rs = ps.executeQuery()) {

                System.out.println("\n----- Category Expense Report -----");

                boolean found = false;

                while (rs.next()) {
                    found = true;

                    String category = rs.getString("category");
                    BigDecimal total = rs.getBigDecimal("total");

                    System.out.println(category + ": Rs. " + total);
                }

                if (!found) {
                    System.out.println("No expenses found for this group.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error generating category report: "
                    + e.getMessage());
        }
    }
}
