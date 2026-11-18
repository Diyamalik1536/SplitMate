
package com.splitmate.model;
import java.math.BigDecimal;

/*
 * Project Name : SplitMate - Group Budget Planner
 * Module       : Group Management
 * Developed By : Diya
 * Date         : 17-11-2026
 */

public class Group {

    private int groupId;
    private String groupName;
    private BigDecimal totalBudget;

    public Group(int groupId, String groupName,
                 BigDecimal totalBudget) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.totalBudget = totalBudget;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public BigDecimal getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(BigDecimal totalBudget) {
        this.totalBudget = totalBudget;
    }

    public void displayGroupDetails() {
        System.out.println("Group ID: " + groupId);
        System.out.println("Group Name: " + groupName);
        System.out.println("Total Budget: Rs. " + totalBudget);
    }
}
