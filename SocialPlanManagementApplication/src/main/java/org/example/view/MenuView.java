package org.example.view;

public class MenuView  extends View {

    public void startMenu() {
        System.out.println("Welcome to Social Plan Manager");
        System.out.println("0. Exit");
        System.out.println("1. Register");
        System.out.println("2. Login");
    }

    public void loginMenu() {
        System.out.println("What you want to do?");
        System.out.println("0. Exit");
        System.out.println("1. Logout");
        System.out.println("2. Create Activity");
        System.out.println("3. Show All Activities");
        System.out.println("4. Create Plan");
        System.out.println("5. Add Activity to Plan");
        System.out.println("6. Delete Activity to Plan");
        System.out.println("7. Join Plan");
        System.out.println("8. Leave Plan");
        System.out.println("9. Rate Plan");
        System.out.println("10. List User Plans");
        System.out.println("11. View Total Duration and Cost of a Plan");
        System.out.println("12. List Plans Sorted by Date");
        System.out.println("13. List Plans Sorted by Punctuation");
        System.out.println("14. Search Plans with Theatre");
        System.out.println("15. Search Plans with Cinema");
        System.out.println("16. Find Cheapest Plan");
        System.out.println("17.Show All Plans");
    }

    public int optionSelect() {
        System.out.println("Select the option you wish to do (just number): ");
        return scanner.nextInt();
    }

}
