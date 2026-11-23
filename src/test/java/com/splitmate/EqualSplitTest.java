
package com.splitmate;

import com.splitmate.split.EqualSplit;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EqualSplitTest {

    public static void main(String[] args) {

        List<Integer> members = Arrays.asList(1, 2, 3);

        System.out.println("=== TEST 1: EQUAL DIVISION ===");

        Map<Integer, BigDecimal> shares =
                EqualSplit.calculateShares(
                        new BigDecimal("900.00"), members);

        EqualSplit.displayShares(
                new BigDecimal("900.00"), members);

        check(shares, members, "300.00", "300.00", "300.00");

        System.out.println("\n=== TEST 2: ROUNDING ===");

        shares = EqualSplit.calculateShares(
                new BigDecimal("100.00"), members);

        EqualSplit.displayShares(
                new BigDecimal("100.00"), members);

        check(shares, members, "33.34", "33.33", "33.33");

        System.out.println("\n=== TEST 3: INVALID AMOUNT ===");

        try {
            EqualSplit.calculateShares(
                    new BigDecimal("-50.00"), members);

            throw new AssertionError(
                    "FAIL: Negative amount was accepted");

        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }

        System.out.println("\n=== TEST 4: DUPLICATE MEMBERS ===");

        try {
            EqualSplit.calculateShares(
                    new BigDecimal("100.00"),
                    Arrays.asList(1, 1, 2));

            throw new AssertionError(
                    "FAIL: Duplicate members were accepted");

        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }

        System.out.println("\nALL EQUAL SPLIT TESTS PASSED!");
    }

    private static void check(
            Map<Integer, BigDecimal> shares,
            List<Integer> members,
            String... expected) {

        BigDecimal total = BigDecimal.ZERO;

        for (int i = 0; i < members.size(); i++) {

            BigDecimal actual = shares.get(members.get(i));
            BigDecimal wanted = new BigDecimal(expected[i]);

            if (actual == null || actual.compareTo(wanted) != 0) {
                throw new AssertionError(
                        "FAIL: Member " + members.get(i)
                        + " expected " + wanted
                        + " but got " + actual);
            }

            total = total.add(actual);
        }

        System.out.println("PASS: All member shares are correct.");
        System.out.println("Verified total: Rs. " + total);
    }
}
