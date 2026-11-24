package com.splitmate.service;

import com.splitmate.dao.UserDAO;
import com.splitmate.dao.GroupMemberDAO;
import com.splitmate.model.User;

import java.sql.SQLException;
import java.util.List;

public class MemberService {

    private final UserDAO userDAO;
    private final GroupMemberDAO groupMemberDAO;

    public MemberService(String dbPassword) {
        userDAO = new UserDAO(dbPassword);
        groupMemberDAO = new GroupMemberDAO(dbPassword);
    }

    // Register a new user
    public int registerUser(String name, String email) throws SQLException {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }

        if (userDAO.emailExists(email)) {
            throw new IllegalArgumentException("Email already registered.");
        }

        User user = new User(name.trim(), email.trim());
        return userDAO.addUser(user);
    }

    // Get user by ID
    public User getUser(int userId) throws SQLException {
        return userDAO.getUserById(userId);
    }

    // Get all registered users
    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }

    // Add user to a group
    public boolean addMemberToGroup(int groupId, int userId) throws SQLException {

        if (groupId <= 0 || userId <= 0) {
            throw new IllegalArgumentException("Invalid group ID or user ID.");
        }

        if (userDAO.getUserById(userId) == null) {
            throw new IllegalArgumentException("User does not exist.");
        }

        if (groupMemberDAO.isMember(groupId, userId)) {
            throw new IllegalArgumentException("User is already a member.");
        }

        return groupMemberDAO.addMember(groupId, userId);
    }

    // Remove user from a group
    public boolean removeMemberFromGroup(int groupId, int userId) throws SQLException {

        if (groupId <= 0 || userId <= 0) {
            throw new IllegalArgumentException("Invalid group ID or user ID.");
        }

        return groupMemberDAO.removeMember(groupId, userId);
    }

    // Check membership
    public boolean isMember(int groupId, int userId) throws SQLException {
        return groupMemberDAO.isMember(groupId, userId);
    }
}