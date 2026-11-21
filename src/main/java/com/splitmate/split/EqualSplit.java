package com.splitmate.split;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EqualSplit {

    // Calculate equal shares
    public static Map<Integer, BigDecimal> calculateShares(
            BigDecimal totalAmount, List<Integer> memberIds) {

        if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero!");
        }

        if (memberIds == null || memberIds.isEmpty()) {
            throw new IllegalArgumentException("No group members found!");
        }

        // Check member IDs
        for (int i = 0; i < memberIds.size(); i++) {

            if (memberIds.get(i) == null || memberIds.get(i) <= 0) {
                throw new IllegalArgumentException("Invalid member ID!");
            }

            for (int j = i + 1; j < memberIds.size(); j++) {
                if (memberIds.get(i).equals(memberIds.get(j))) {
                    throw new IllegalArgumentException("Duplicate member IDs found!");
                }
            }
        }

        long totalPaise;

        try {
            totalPaise = totalAmount.movePointRight(2).longValueExact();
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException(
                "Amount cannot have more than two decimal places!");
        }

        long equalShare = totalPaise / memberIds.size();
        long remainingPaise = totalPaise % memberIds.size();

        Map<Integer, BigDecimal> shares = new LinkedHashMap<>();

        for (int i = 0; i < memberIds.size(); i++) {

            long share = equalShare;

            if (i < remainingPaise) {
                share++;
            }

            shares.put(memberIds.get(i), BigDecimal.valueOf(share, 2));
        }

        return shares;
    }

    // Display shares
    public static void displayShares(BigDecimal totalAmount,
                                     List<Integer> memberIds) {

        Map<Integer, BigDecimal> shares = calculateShares(totalAmount, memberIds);

        System.out.println("\n===== EQUAL EXPENSE SPLIT =====");

        for (Map.Entry<Integer, BigDecimal> entry : shares.entrySet()) {
            System.out.println("Member " + entry.getKey()
                    + " pays Rs. " + entry.getValue());
        }

        System.out.println("-------------------------------");
        System.out.println("Total Expense: Rs. " + totalAmount);
        System.out.println("Total Members: " + memberIds.size());
    }
}
