
package com.splitmate;

import com.splitmate.dao.ExpenseDAO;
import com.splitmate.model.Expense;
import com.splitmate.service.BudgetAlert;
import com.splitmate.service.BudgetService;
import com.splitmate.service.CategoryReport;
import com.splitmate.service.ExpenseService;

import java.io.Console;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class BudgetTest {

    public static void main(String[] args) {

        Console console = System.console();

        if (console == null) {
            System.out.println("Please run from PowerShell.");
            return;
        }

        char[] chars = console.readPassword("Enter MySQL password: ");
        if (chars == null) {
            System.out.println("Password input cancelled.");
            return;
        }

        String password = new String(chars);
        Arrays.fill(chars, '\0');

        int groupId = 3; // College Trip
        int userId = 1;  // Temporary test user

        BudgetService budgetService = new BudgetService();
        BudgetAlert budgetAlert = new BudgetAlert();
        CategoryReport categoryReport = new CategoryReport();
        ExpenseService expenseService = new ExpenseService();
        ExpenseDAO expenseDAO = new ExpenseDAO();

        String marker = "GITANSHI_BUDGET_TEST_";
        BigDecimal expectedBudget = new BigDecimal("15000.00");

        try {
            // Safety check: do not touch existing expenses
            List<Expense> existing =
                    expenseDAO.getExpensesByGroup(groupId, password);

            if (!existing.isEmpty()) {
                System.out.println(
                    "STOP: Group already contains expenses.");
                return;
            }

            BigDecimal budget =
                    budgetService.getTotalBudget(groupId, password);

            if (budget.compareTo(expectedBudget) != 0) {
                System.out.println(
                    "STOP: Expected budget Rs. 15000.00, found Rs. "
                    + budget);
                return;
            }

            System.out.println("\n=== TEST 1: EMPTY BUDGET ===");
            budgetService.displayBudgetSummary(groupId, password);
            budgetAlert.checkBudgetAlert(groupId, password);
            categoryReport.displayCategoryReport(groupId, password);

            System.out.println("\n=== TEST 2: 80% WARNING ===");

            boolean added1 = expenseService.addExpense(
                    groupId, marker + "FOOD", "Food",
                    new BigDecimal("8000.00"), userId, password);

            boolean added2 = expenseService.addExpense(
                    groupId, marker + "TRAVEL", "Travel",
                    new BigDecimal("4000.00"), userId, password);

            if (!added1 || !added2) {
                System.out.println("Expense insertion failed.");
                return;
            }

            budgetService.displayBudgetSummary(groupId, password);
            budgetAlert.checkBudgetAlert(groupId, password);
            categoryReport.displayCategoryReport(groupId, password);

            System.out.println("\n=== TEST 3: BUDGET EXCEEDED ===");

            boolean added3 = expenseService.addExpense(
                    groupId, marker + "HOTEL", "Hotel",
                    new BigDecimal("4000.00"), userId, password);

            if (!added3) {
                System.out.println("Expense insertion failed.");
                return;
            }

            budgetService.displayBudgetSummary(groupId, password);
            budgetAlert.checkBudgetAlert(groupId, password);
            categoryReport.displayCategoryReport(groupId, password);

        } finally {

            // Clean up only our temporary test expenses
            List<Expense> expenses =
                    expenseDAO.getExpensesByGroup(groupId, password);

            for (Expense expense : expenses) {
                if (expense.getDescription() != null
                        && expense.getDescription().startsWith(marker)) {

                    boolean deleted = expenseService.deleteExpense(
                            expense.getExpenseId(), password);

                    System.out.println(
                        "Test expense " + expense.getExpenseId()
                        + " cleanup: " + deleted);
                }
            }

            System.out.println("\n=== FINAL BUDGET SUMMARY ===");
            budgetService.displayBudgetSummary(groupId, password);
        }
    }
}
