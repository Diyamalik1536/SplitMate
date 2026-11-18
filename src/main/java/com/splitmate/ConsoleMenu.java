
package com.splitmate;

import com.splitmate.dao.GroupDAO;
import com.splitmate.service.GroupService;

import java.math.BigDecimal;
import java.util.Scanner;

/*
 * Project Name : SplitMate - Group Budget Planner
 * Module       : Console Menu
 * Developed By : Diya
 * Date         : 18-11-2026
 * Evaluation   : 1
 */

public class ConsoleMenu {

    private final GroupService groupService = new GroupService();
    private final GroupDAO groupDAO = new GroupDAO();

    /*
     * New Feature  : Interactive Group Management Menu
     * Developed By : Diya
     * Date         : 18-11-2026
     * Description  : Allows users to create groups,
     *                view groups and update budgets.
     */

    public void showMenu(String password) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("\n========== SPLITMATE ==========");
            System.out.println("1. Create Group");
            System.out.println("2. View All Groups");
            System.out.println("3. Update Group Budget");
            System.out.println("4. Exit");
            System.out.println("===============================");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    System.out.print("Enter group name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter total budget: ");
                    String budgetInput = scanner.nextLine();

                    try {
                        BigDecimal budget = new BigDecimal(budgetInput);

                        boolean created = groupService.createGroup(
                                name, budget, password);

                        if (created) {
                            System.out.println("Group created successfully!");
                        } else {
                            System.out.println("Group creation failed.");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid budget! Enter a number.");
                    }
                    break;

                case "2":
                    System.out.println("\n--- All Groups ---");
                    groupDAO.viewGroups(password);
                    break;

                case "3":
                    System.out.print("Enter group ID: ");
                    String idInput = scanner.nextLine();

                    System.out.print("Enter new budget: ");
                    String newBudgetInput = scanner.nextLine();

                    try {
                        int groupId = Integer.parseInt(idInput);
                        BigDecimal newBudget = new BigDecimal(newBudgetInput);

                        boolean updated = groupService.updateGroupBudget(
                                groupId, newBudget, password);

                        if (updated) {
                            System.out.println("Budget updated successfully!");
                        } else {
                            System.out.println("Budget update failed.");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Enter valid numbers.");
                    }
                    break;

                case "4":
                    System.out.println("Thank you for using SplitMate!");
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
