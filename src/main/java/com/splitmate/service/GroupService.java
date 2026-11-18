
package com.splitmate.service;

import com.splitmate.dao.GroupDAO;
import com.splitmate.model.Group;

import java.math.BigDecimal;

/*
 * Project Name : SplitMate - Group Budget Planner
 * Module       : Group Management Service
 * Developed By : Diya
 * Date         : 17-11-2026
 * Evaluation   : 1
 */

public class GroupService {

    private GroupDAO groupDAO = new GroupDAO();

    // Method 1: Create a new group
    public boolean createGroup(String name,
                               BigDecimal budget,
                               String password) {

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Group name cannot be empty!");
            return false;
        }

        if (budget == null ||
                budget.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Budget must be greater than zero!");
            return false;
        }

        Group group = new Group(0, name.trim(), budget);

        return groupDAO.addGroup(group, password);
    }

    /*
     * New Feature  : Group Budget Update Validation
     * Developed By : Diya
     * Date         : 18-11-2026
     * Description  : Validates group ID and new budget
     *                before updating the database.
     */

    // Method 2: Validate and update group budget
    public boolean updateGroupBudget(int groupId,
                                     BigDecimal newBudget,
                                     String password) {

        if (groupId <= 0) {
            System.out.println("Invalid group ID!");
            return false;
        }

        if (newBudget == null ||
                newBudget.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Budget must be greater than zero!");
            return false;
        }

        return groupDAO.updateBudget(groupId, newBudget, password);
    }
}
