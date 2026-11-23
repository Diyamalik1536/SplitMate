
package com.splitmate;

import com.splitmate.dao.GroupDAO;
import com.splitmate.service.GroupService;
import com.splitmate.service.ExpenseService;
import com.splitmate.service.BudgetService;
import com.splitmate.service.BudgetAlert;
import com.splitmate.service.CategoryReport;
import com.splitmate.split.EqualSplit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Project Name : SplitMate - Group Budget Planner
 * Module       : Console Menu
 * Developed By : Diya
 * Date         : 18-11-2026
 * Evaluation   : 1
 *
 * Integration:
 * Group Management - Diya
 * Expense Management & Equal Split - Dhani
 * Budget Analytics, Alerts & Category Report - Gitanshi
 */

public class ConsoleMenu {

    private final GroupService groupService = new GroupService();
    private final GroupDAO groupDAO = new GroupDAO();
    private final ExpenseService expenseService = new ExpenseService();

    private final BudgetService budgetService = new BudgetService();
    private final BudgetAlert budgetAlert = new BudgetAlert();
    private final CategoryReport categoryReport = new CategoryReport();

    public void showMenu(String password) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("\n========== SPLITMATE ==========");
            System.out.println("1. Create Group");
            System.out.println("2. View All Groups");
            System.out.println("3. Update Group Budget");
            System.out.println("4. Add Expense");
            System.out.println("5. View Group Expenses");
            System.out.println("6. Update Expense");
            System.out.println("7. Delete Expense");
            System.out.println("8. Equal Split");
            System.out.println("9. View Budget Summary");
            System.out.println("10. Check Budget Alerts");
            System.out.println("11. View Category Expense Report");
            System.out.println("12. Exit");
            System.out.println("===============================");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            try {

                switch (choice) {

                    case "1": {
                        System.out.print("Enter group name: ");
                        String name = scanner.nextLine();

                        System.out.print("Enter total budget: ");
                        BigDecimal budget =
                                new BigDecimal(scanner.nextLine().trim());

                        boolean created = groupService.createGroup(
                                name, budget, password);

                        System.out.println(created
                                ? "Group created successfully!"
                                : "Group creation failed.");
                        break;
                    }

                    case "2": {
                        System.out.println("\n--- All Groups ---");
                        groupDAO.viewGroups(password);
                        break;
                    }

                    case "3": {
                        System.out.print("Enter group ID: ");
                        int groupId =
                                Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("Enter new budget: ");
                        BigDecimal newBudget =
                                new BigDecimal(scanner.nextLine().trim());

                        boolean updated = groupService.updateGroupBudget(
                                groupId, newBudget, password);

                        System.out.println(updated
                                ? "Budget updated successfully!"
                                : "Budget update failed.");
                        break;
                    }

                    case "4": {
                        System.out.print("Enter group ID: ");
                        int groupId =
                                Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("Enter description: ");
                        String description = scanner.nextLine();

                        System.out.print(
                                "Enter category (Food/Travel/Hotel/Shopping/Other): ");
                        String category = scanner.nextLine();

                        System.out.print("Enter amount: ");
                        BigDecimal amount =
                                new BigDecimal(scanner.nextLine().trim());

                        System.out.print("Enter paid by user ID: ");
                        int paidByUserId =
                                Integer.parseInt(scanner.nextLine().trim());

                        boolean added = expenseService.addExpense(
                                groupId, description, category,
                                amount, paidByUserId, password);

                        System.out.println(added
                                ? "Expense added successfully!"
                                : "Expense addition failed.");
                        break;
                    }

                    case "5": {
                        System.out.print("Enter group ID: ");
                        int groupId =
                                Integer.parseInt(scanner.nextLine().trim());

                        System.out.println("\n--- Group Expenses ---");
                        expenseService.viewExpenses(groupId, password);
                        break;
                    }

                    case "6": {
                        System.out.print("Enter expense ID: ");
                        int expenseId =
                                Integer.parseInt(scanner.nextLine().trim());

                        System.out.print("Enter new description: ");
                        String description = scanner.nextLine();

                        System.out.print(
                                "Enter new category (Food/Travel/Hotel/Shopping/Other): ");
                        String category = scanner.nextLine();

                        System.out.print("Enter new amount: ");
                        BigDecimal amount =
                                new BigDecimal(scanner.nextLine().trim());

                        boolean updated = expenseService.updateExpense(
                                expenseId, description, category,
                                amount, password);

                        System.out.println(updated
                                ? "Expense updated successfully!"
                                : "Expense update failed.");
                        break;
                    }

                    case "7": {
                        System.out.print("Enter expense ID to delete: ");
                        int expenseId =
                                Integer.parseInt(scanner.nextLine().trim());

                        System.out.print(
                                "Are you sure you want to delete this expense? (yes/no): ");
                        String confirmation = scanner.nextLine().trim();

                        if (!confirmation.equalsIgnoreCase("yes")) {
                            System.out.println("Deletion cancelled.");
                            break;
                        }

                        boolean deleted = expenseService.deleteExpense(
                                expenseId, password);

                        System.out.println(deleted
                                ? "Expense deleted successfully!"
                                : "Expense deletion failed.");
                        break;
                    }

                    case "8": {
                        System.out.print("Enter total expense amount: ");
                        BigDecimal totalAmount =
                                new BigDecimal(scanner.nextLine().trim());

                        System.out.print("Enter number of members: ");
                        int count =
                                Integer.parseInt(scanner.nextLine().trim());

                        if (count <= 0) {
                            System.out.println(
                                    "Number of members must be greater than zero!");
                            break;
                        }

                        List<Integer> memberIds = new ArrayList<>();

                        for (int i = 1; i <= count; i++) {
                            System.out.print("Enter member " + i + " ID: ");
                            int memberId =
                                    Integer.parseInt(scanner.nextLine().trim());

                            memberIds.add(memberId);
                        }

                        EqualSplit.displayShares(totalAmount, memberIds);
                        break;
                    }

                    case "9": {
                        System.out.print("Enter group ID: ");
                        int groupId =
                                Integer.parseInt(scanner.nextLine().trim());

                        budgetService.displayBudgetSummary(groupId, password);
                        break;
                    }

                    case "10": {
                        System.out.print("Enter group ID: ");
                        int groupId =
                                Integer.parseInt(scanner.nextLine().trim());

                        budgetAlert.checkBudgetAlert(groupId, password);
                        break;
                    }

                    case "11": {
                        System.out.print("Enter group ID: ");
                        int groupId =
                                Integer.parseInt(scanner.nextLine().trim());

                        categoryReport.displayCategoryReport(groupId, password);
                        break;
                    }

                    case "12": {
                        System.out.println(
                                "Thank you for using SplitMate!");
                        return;
                    }

                    default: {
                        System.out.println("Invalid choice! Try again.");
                    }
                }

            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid input! Please enter valid numbers.");

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
