
package com.splitmate.service;

import java.math.BigDecimal;

/*
 * Project Name : SplitMate - Group Budget Planner
 * Module       : Budget tracking and reports
 * Developed By : Gitanshi
 * Date         : 22-11-2026
 */

public class BudgetAlert {

    private final BudgetService budgetService = new BudgetService();

    // Check whether expenses are close to the group budget
    public void checkBudgetAlert(int groupId, String password) {

        BigDecimal totalBudget =
                budgetService.getTotalBudget(groupId, password);

        BigDecimal totalExpenses =
                budgetService.getTotalExpenses(groupId, password);

        if (totalBudget.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("No valid budget set for this group.");
            return;
        }

        BigDecimal percentage = totalExpenses
                .multiply(BigDecimal.valueOf(100))
                .divide(totalBudget, 2, java.math.RoundingMode.HALF_UP);

        System.out.println("\n----- Budget Alert -----");

        if (totalExpenses.compareTo(totalBudget) > 0) {
            System.out.println("ALERT: Group budget exceeded!");
        } else if (percentage.compareTo(BigDecimal.valueOf(80)) >= 0) {
            System.out.println("WARNING: 80% or more of the budget has been used!");
        } else {
            System.out.println("Budget is under control.");
        }

        System.out.println("Budget used: " + percentage + "%");
    }
}
