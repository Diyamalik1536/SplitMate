package com.splitmate.dao;

import com.splitmate.db.DBConnection;
import com.splitmate.model.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {

    // Add expense
    public boolean addExpense(Expense expense, String password) {

        String sql = "INSERT INTO expenses (group_id, description, category, amount, paid_by_user_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection(password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, expense.getGroupId());
            ps.setString(2, expense.getDescription());
            ps.setString(3, expense.getCategory());
            ps.setBigDecimal(4, expense.getAmount());
            ps.setInt(5, expense.getPaidByUserId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding expense: " + e.getMessage());
            return false;
        }
    }

    // Update expense
    public boolean updateExpense(Expense expense, String password) {

        String sql = "UPDATE expenses SET description = ?, category = ?, amount = ? WHERE expense_id = ?";

        try (Connection con = DBConnection.getConnection(password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, expense.getDescription());
            ps.setString(2, expense.getCategory());
            ps.setBigDecimal(3, expense.getAmount());
            ps.setInt(4, expense.getExpenseId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating expense: " + e.getMessage());
            return false;
        }
    }

    // Delete expense
    public boolean deleteExpense(int expenseId, String password) {

        String sql = "DELETE FROM expenses WHERE expense_id = ?";

        try (Connection con = DBConnection.getConnection(password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, expenseId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting expense: " + e.getMessage());
            return false;
        }
    }

    // Get expenses of a group
    public List<Expense> getExpensesByGroup(int groupId, String password) {

        List<Expense> expenses = new ArrayList<>();

        String sql = "SELECT * FROM expenses WHERE group_id = ?";

        try (Connection con = DBConnection.getConnection(password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, groupId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Expense expense = new Expense(
                        rs.getInt("expense_id"),
                        rs.getInt("group_id"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getBigDecimal("amount"),
                        rs.getInt("paid_by_user_id")
                    );

                    expenses.add(expense);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error viewing expenses: " + e.getMessage());
        }

        return expenses;
    }

    // Display group expenses
    public void viewExpenses(int groupId, String password) {

        List<Expense> expenses = getExpensesByGroup(groupId, password);

        if (expenses.isEmpty()) {
            System.out.println("No expenses found for this group.");
        } else {
            for (Expense expense : expenses) {
                expense.displayExpense();
            }
        }
    }
}
