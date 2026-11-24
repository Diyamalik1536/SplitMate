
package com.splitmate.demo;

import com.splitmate.service.MemberService;
import com.splitmate.model.User;

import java.sql.SQLException;
import java.util.Scanner;

public class MemberManagementDemo {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter MySQL password: ");
        String password = sc.nextLine();

        MemberService service = new MemberService(password);

        try {

            // 1. Register users (reuse if they already exist)
            int user1 = getOrCreateUser(service, "Pranjal", "pranjal@gmail.com");
            int user2 = getOrCreateUser(service, "Diya", "diya@gmail.com");
            int user3 = getOrCreateUser(service, "Dhani", "dhani@gmail.com");
            int user4 = getOrCreateUser(service, "Gitanshi", "gitanshi@gmail.com");

            // 2. Display registered users
            System.out.println("\n--- Registered Users ---");

            for (User user : service.getAllUsers()) {
                System.out.println(user);
            }

            // 3. Add all four users to Manali Trip
            int groupId = 1; // Replace with actual Manali Trip group ID

            System.out.println("\n--- Adding Members ---");

            addMemberIfAbsent(service, groupId, user1);
            addMemberIfAbsent(service, groupId, user2);
            addMemberIfAbsent(service, groupId, user3);
            addMemberIfAbsent(service, groupId, user4);

            // 4. Remove one member (Gitanshi)
            System.out.println("\n--- Removing Member ---");

            if (service.isMember(groupId, user4)) {
                service.removeMemberFromGroup(groupId, user4);
                System.out.println("Gitanshi removed from Manali Trip.");
            } else {
                System.out.println("Gitanshi is already removed.");
            }

            // 5. Verify remaining members
            System.out.println("\n--- Demo Completed Successfully ---");

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }

    // Reuse an existing user or register a new one
    private static int getOrCreateUser(
            MemberService service, String name, String email)
            throws SQLException {

        for (User user : service.getAllUsers()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                System.out.println("User already exists: " + email);
                return user.getUserId();
            }
        }

        int userId = service.registerUser(name, email);
        System.out.println("Registered: " + email);

        return userId;
    }

    // Add user only if they are not already a member
    private static void addMemberIfAbsent(
            MemberService service, int groupId, int userId)
            throws SQLException {

        if (service.isMember(groupId, userId)) {
            System.out.println("User " + userId + " is already a member.");
        } else {
            service.addMemberToGroup(groupId, userId);
            System.out.println("User " + userId + " added to group.");
        }
    }
}
