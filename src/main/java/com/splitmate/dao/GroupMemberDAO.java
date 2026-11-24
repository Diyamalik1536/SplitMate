package com.splitmate.dao;

import com.splitmate.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GroupMemberDAO {

    private final String dbPassword;

    public GroupMemberDAO(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    // Add a user to a group
    public boolean addMember(int groupId, int userId) throws SQLException {

        String sql = "INSERT INTO group_members (group_id, user_id) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection(dbPassword);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, groupId);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;
        }
    }

    // Remove a member from a group
    public boolean removeMember(int groupId, int userId) throws SQLException {

        String sql = "DELETE FROM group_members WHERE group_id = ? AND user_id = ?";

        try (Connection con = DBConnection.getConnection(dbPassword);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, groupId);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;
        }
    }

    // Check whether a user belongs to a group
    public boolean isMember(int groupId, int userId) throws SQLException {

        String sql = "SELECT 1 FROM group_members WHERE group_id = ? AND user_id = ?";

        try (Connection con = DBConnection.getConnection(dbPassword);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, groupId);
            ps.setInt(2, userId);   

            try (var rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}