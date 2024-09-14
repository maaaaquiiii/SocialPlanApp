package org.example.view;

import org.example.controller.UserController;

import java.util.InputMismatchException;

public class UserView extends View {
    private UserController userController;

    public UserView() {
        this.userController = new UserController();
    }
    public void register() {
        System.out.println("--- User Registration ---");
        try {
            // consuming the leftover newline character.
            scanner.nextLine();
            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            System.out.print("Enter age: ");
            int age = scanner.nextInt();

            System.out.print("Enter phone: ");
            int phone = scanner.nextInt();

            System.out.print("Enter password: ");
            String password = scanner.next();

            userController.register(name, age, phone, password);
            System.out.println("User registered successfully!");
        } catch (InputMismatchException e) {
            System.out.println("Error:  Mismatched input");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public boolean loginUser() {
        System.out.println("\n--- User Login ---");
        try {
            // consuming the leftover newline character.
            scanner.nextLine();
            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.next();

            return userController.login(name, password);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public void logoutUser() {
        System.out.println("\n--- Logout User ---");
        userController.logout();
        System.out.println("User logged out successfully!");
    }

}
