
package com.splitmate.service;

import com.splitmate.db.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BudgetService {

    // Get the group's total budget
    public BigDecimal getTotalBudget(int groupId, String password) {

        String sql = "SELECT total_budget FROM `groups` WHERE group_id = ?";

        try (Connection con = DBConnection.getConnection(password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, groupId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("total_budget");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error getting total budget: " + e.getMessage());
        }

        return BigDecimal.ZERO;
    }

    // Calculate total expenses of a group
    public BigDecimal getTotalExpenses(int groupId, String password) {

        String sql = "SELECT COALESCE(SUM(amount), 0) AS total "
                   + "FROM expenses WHERE group_id = ?";

        try (Connection con = DBConnection.getConnection(password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, groupId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("total");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error getting total expenses: " + e.getMessage());
        }

        return BigDecimal.ZERO;
    }

    // Calculate remaining budget
    public BigDecimal getRemainingBudget(int groupId, String password) {

        BigDecimal totalBudget = getTotalBudget(groupId, password);
        BigDecimal totalExpenses = getTotalExpenses(groupId, password);

        return totalBudget.subtract(totalExpenses);
    }

    // Display budget summary
    public void displayBudgetSummary(int groupId, String password) {

        BigDecimal totalBudget = getTotalBudget(groupId, password);
        BigDecimal totalExpenses = getTotalExpenses(groupId, password);
        BigDecimal remainingBudget = totalBudget.subtract(totalExpenses);

        System.out.println("\n----- Budget Summary -----");
        System.out.println("Total Budget: Rs. " + totalBudget);
        System.out.println("Total Expenses: Rs. " + totalExpenses);
        System.out.println("Remaining Budget: Rs. " + remainingBudget);

        if (remainingBudget.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Warning: Budget exceeded!");
        }
    }
}
