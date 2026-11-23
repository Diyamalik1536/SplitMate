
package com.splitmate;

import com.splitmate.dao.ExpenseDAO;
import com.splitmate.model.Expense;
import com.splitmate.service.ExpenseService;

import java.io.Console;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class ExpenseTest {

    public static void main(String[] args) {

        Console console = System.console();

        if (console == null) {
            System.out.println("Please run from PowerShell.");
            return;
        }

        char[] passwordChars =
                console.readPassword("Enter MySQL password: ");

        String password = new String(passwordChars);
        Arrays.fill(passwordChars, '\0');

        ExpenseService service = new ExpenseService();
        ExpenseDAO dao = new ExpenseDAO();

        int groupId = 3; // College Trip
        int paidByUserId = 1; // Temporary test user

        String description = "Dhani Expense Test";

        System.out.println("\n--- ADD EXPENSE ---");

        boolean added = service.addExpense(
                groupId,
                description,
                "Food",
                new BigDecimal("500.00"),
                paidByUserId,
                password
        );

        System.out.println("Add result: " + added);

        if (!added) {
            System.out.println("Test stopped.");
            return;
        }

        System.out.println("\n--- VIEW EXPENSES ---");
        service.viewExpenses(groupId, password);

        List<Expense> expenses =
                dao.getExpensesByGroup(groupId, password);

        Expense testExpense = null;

        for (Expense expense : expenses) {
            if (description.equals(expense.getDescription())
                    && expense.getPaidByUserId() == paidByUserId
                    && expense.getAmount().compareTo(
                            new BigDecimal("500.00")) == 0) {

                if (testExpense == null
                        || expense.getExpenseId()
                        > testExpense.getExpenseId()) {
                    testExpense = expense;
                }
            }
        }

        if (testExpense == null) {
            System.out.println("Test expense not found.");
            return;
        }

        int expenseId = testExpense.getExpenseId();

        System.out.println("\n--- UPDATE EXPENSE ---");

        boolean updated = service.updateExpense(
                expenseId,
                "Dhani Updated Test",
                "Travel",
                new BigDecimal("700.00"),
                password
        );

        System.out.println("Update result: " + updated);

        System.out.println("\n--- VIEW AFTER UPDATE ---");
        service.viewExpenses(groupId, password);

        System.out.println("\n--- DELETE TEST EXPENSE ---");

        boolean deleted =
                service.deleteExpense(expenseId, password);

        System.out.println("Delete result: " + deleted);

        System.out.println("\n--- FINAL VIEW ---");
        service.viewExpenses(groupId, password);
    }
}
