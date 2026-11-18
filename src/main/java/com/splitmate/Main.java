
package com.splitmate;

import java.io.Console;
import java.util.Arrays;

/*
 * Project Name : SplitMate - Group Budget Planner
 * Module       : Application Entry Point
 * Developed By : Diya
 * Date         : 17-11-2026
 * Evaluation   : 1
 */

public class Main {

    public static void main(String[] args) {

        Console console = System.console();

        if (console == null) {
            System.out.println("Please run from terminal.");
            return;
        }

        char[] passwordChars =
                console.readPassword("Enter MySQL password: ");

        if (passwordChars == null) {
            return;
        }

        String password = new String(passwordChars);
        Arrays.fill(passwordChars, '\0');

        /*
         * New Feature  : Console Menu Integration
         * Developed By : Diya
         * Date         : 18-11-2026
         * Description  : Connects Main.java to ConsoleMenu.java.
         */

        ConsoleMenu menu = new ConsoleMenu();
        menu.showMenu(password);
    }
}
