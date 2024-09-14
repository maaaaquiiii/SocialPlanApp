package org.example;


import org.example.view.ActivityView;
import org.example.view.MenuView;
import org.example.view.PlanView;
import org.example.view.UserView;

import java.util.InputMismatchException;

public class SocialPlanManagement {
    public static void main(String[] args) {
        MenuView menuView = new MenuView();
        UserView userView = new UserView();
        ActivityView activityView = new ActivityView();
        PlanView planView = new PlanView();
        boolean isRegistered = false;

        menuView.startMenu();
        int startOption = menuView.optionSelect();
        while (startOption != 0) {
            try {
                switch (startOption) {
                    case 0 -> System.exit(0);
                    case 1 -> {
                        userView.register();
                        isRegistered = true;
                    }
                    case 2 -> {
                        if (isRegistered) {
                            if (userView.loginUser()) {
                                menuView.loginMenu();
                                int optionLogged = menuView.optionSelect();
                                while (optionLogged != 0) {
                                    if (!isRegistered) {
                                        System.out.println("You need to register first!");
                                        break;
                                    }
                                    try {
                                        switch (optionLogged) {
                                            case 1 -> userView.logoutUser();
                                            case 2 -> activityView.createActivity();
                                            case 3 -> activityView.showAllActivities();
                                            case 4 -> planView.createPlan();
                                            case 5 -> planView.addActivityToPlan();
                                            case 6 -> planView.deleteActivityToPlan();
                                            case 7 -> planView.joinPlan();
                                            case 8 -> planView.leavePlan();
                                            case 9 -> planView.ratePlan();
                                            case 10 -> planView.listUserPlans();
                                            case 11 -> planView.viewTotalDurationAndCost();
                                            case 12 -> planView.listPlansSortedByDate();
                                            case 13 -> planView.listPlansSortedByPunctuation();
                                            case 14 -> planView.searchActivityTheatre();
                                            case 15 -> planView.searchActivityCinema();
                                            case 16 -> planView.showCheapestPlan();
                                            case 17 -> planView.showAllPlans();
                                            default -> System.out.println("Incorrect option");
                                        }
                                        if (optionLogged == 1) {
                                            break;
                                        }
                                    } catch (InputMismatchException e) {
                                        System.out.println("This is happening here: " + e.getMessage());
                                    }
                                    menuView.loginMenu();
                                    optionLogged = menuView.optionSelect();
                                }
                            } else {
                                System.out.println("Login failed. Please try again.");
                            }
                        } else {
                            System.out.println("You need to register first!");
                        }
                    }
                    default -> System.out.println("Incorrect option");
                }
                menuView.startMenu();
                startOption = menuView.optionSelect();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}