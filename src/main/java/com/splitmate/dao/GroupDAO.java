
package com.splitmate.dao;

import com.splitmate.db.DBConnection;
import com.splitmate.model.Group;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/*
 * Project Name : SplitMate - Group Budget Planner
 * Module       : Group Management DAO
 * Developed By : Diya
 * Date         : 17-11-2026
 * Evaluation   : 1
 */

public class GroupDAO {

    // Method 1: Add a new group to MySQL
    public boolean addGroup(Group group, String password) {

        String sql = "INSERT INTO `groups` (group_name, total_budget) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection(password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, group.getGroupName());
            ps.setBigDecimal(2, group.getTotalBudget());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error adding group: " + e.getMessage());
            return false;
        }
    }

    // Method 2: View all groups from MySQL
    public void viewGroups(String password) {

        String sql = "SELECT * FROM `groups`";

        try (Connection con = DBConnection.getConnection(password);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.println("--------------------");
                System.out.println("Group ID: " +
                        rs.getInt("group_id"));
                System.out.println("Group Name: " +
                        rs.getString("group_name"));
                System.out.println("Budget: Rs. " +
                        rs.getBigDecimal("total_budget"));
            }

            if (!found) {
                System.out.println("No groups found.");
            }

        } catch (SQLException e) {
            System.out.println("Error viewing groups: "
                    + e.getMessage());
        }
    }

    /*
     * New Feature  : Update Group Budget
     * Developed By : Diya
     * Date         : 18-11-2026
     * Description  : Updates the budget of an existing
     *                group using its group ID.
     */

    // Method 3: Update an existing group's budget
    public boolean updateBudget(int groupId,
                                BigDecimal newBudget,
                                String password) {

        String sql = "UPDATE `groups` SET total_budget = ? WHERE group_id = ?";

        try (Connection con = DBConnection.getConnection(password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBigDecimal(1, newBudget);
            ps.setInt(2, groupId);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error updating budget: "
                    + e.getMessage());
            return false;
        }
    }
}
