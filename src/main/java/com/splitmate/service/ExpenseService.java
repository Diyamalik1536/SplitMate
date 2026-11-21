package com.splitmate.service;

import com.splitmate.dao.ExpenseDAO;
import com.splitmate.model.Expense;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class ExpenseService {

    private final ExpenseDAO expenseDAO = new ExpenseDAO();

    // Available categories
    private final List<String> categories = Arrays.asList(
        "Food", "Travel", "Hotel", "Shopping", "Other"
    );

    // Add expense
    public boolean addExpense(int groupId, String description,
                              String category, BigDecimal amount,
                              int paidByUserId, String password) {

        if (groupId <= 0 || paidByUserId <= 0) {
            System.out.println("Invalid group or user ID!");
            return false;
        }

        if (description == null || description.trim().isEmpty()) {
            System.out.println("Description cannot be empty!");
            return false;
        }

        if (category == null || !categories.contains(category.trim())) {
            System.out.println("Invalid expense category!");
            return false;
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Amount must be greater than zero!");
            return false;
        }

        Expense expense = new Expense(0, groupId, description.trim(),
                category.trim(), amount, paidByUserId);

        return expenseDAO.addExpense(expense, password);
    }

    // Update expense
    public boolean updateExpense(int expenseId, String description,
                                 String category, BigDecimal amount,
                                 String password) {

        if (expenseId <= 0) {
            System.out.println("Invalid expense ID!");
            return false;
        }

        if (description == null || description.trim().isEmpty()) {
            System.out.println("Description cannot be empty!");
            return false;
        }

        if (category == null || !categories.contains(category.trim())) {
            System.out.println("Invalid category!");
            return false;
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Amount must be greater than zero!");
            return false;
        }

        Expense expense = new Expense(expenseId, 0, description.trim(),
                category.trim(), amount, 0);

        return expenseDAO.updateExpense(expense, password);
    }

    // Delete expense
    public boolean deleteExpense(int expenseId, String password) {

        if (expenseId <= 0) {
            System.out.println("Invalid expense ID!");
            return false;
        }

        return expenseDAO.deleteExpense(expenseId, password);
    }

    // View group expenses
    public void viewExpenses(int groupId, String password) {

        if (groupId <= 0) {
            System.out.println("Invalid group ID!");
            return;
        }

        expenseDAO.viewExpenses(groupId, password);
    }
}
