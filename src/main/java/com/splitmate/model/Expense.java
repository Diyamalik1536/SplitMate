
package com.splitmate.model;

import java.math.BigDecimal;

public class Expense {

    private int expenseId;
    private int groupId;
    private String description;
    private String category;
    private BigDecimal amount;
    private int paidByUserId;

    // Constructor
    public Expense(int expenseId, int groupId, String description,
                   String category, BigDecimal amount, int paidByUserId) {

        this.expenseId = expenseId;
        this.groupId = groupId;
        this.description = description;
        this.category = category;
        this.amount = amount;
        this.paidByUserId = paidByUserId;
    }

    // Getters
    public int getExpenseId() {
        return expenseId;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int getPaidByUserId() {
        return paidByUserId;
    }

    // Setters
    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    // Display expense details
    public void displayExpense() {
        System.out.println("Expense ID: " + expenseId);
        System.out.println("Group ID: " + groupId);
        System.out.println("Description: " + description);
        System.out.println("Category: " + category);
        System.out.println("Amount: Rs. " + amount);
        System.out.println("Paid By User ID: " + paidByUserId);
    }
}
