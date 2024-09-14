package org.example.view;

import org.example.controller.PlanController;
import org.example.controller.UserController;
import org.example.model.Activity;
import org.example.model.Plan;
import org.example.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PlanView extends View {
    private static PlanController planController = new PlanController();

    public void createPlan() {
        System.out.println("\n--- Create Plan ---");
        try {
            // consuming the leftover newline character.
            scanner.nextLine();
            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Date and time (yyyy-MM-dd HH:mm): ");
            String dateTimeString = scanner.next() + " " + scanner.next();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

            scanner.nextLine();
            System.out.print("Location: ");
            String location = scanner.nextLine();

            System.out.print("Maximum capacity: ");
            int maxCapacity = scanner.nextInt();

            User owner = UserController.getLoggedUser();

            Plan plan = planController.createPlan(name, dateTime, location, maxCapacity, owner);
            System.out.println("Plan created successfully!");
            viewPlanDetails(plan);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void showAllPlans() {
        List<Plan> allPlans = planController.getAllPlans();
        System.out.println("All Plans:");
        for (Plan plan : allPlans) {
            viewPlanDetails(plan);
        }
    }

    private void viewPlanDetails(Plan plan) {
        System.out.print("Plan Details:\t");
        System.out.print("ID: " + plan.getId());
        System.out.print("\t Name: " + plan.getName());
        System.out.print("\t Owner: " + plan.getOwner().getName());
        System.out.print("\t Date and Time: " + plan.getDateTime());
        System.out.print("\t Location: " + plan.getLocation());
        System.out.print("\t Max Capacity: " + plan.getMaxCapacity());
        System.out.println("\nParticipants:");
        if (plan.getParticipants().isEmpty()) {
            System.out.println("No participants joined yet.");
        } else {
            for (User participant : plan.getParticipants()) {
                System.out.println(participant.getName() + " ");
            }
        }
        System.out.println("Activities: ");
        if (plan.getActivities().isEmpty()) {
            System.out.println("No activities added yet.");
        } else {
            for (Activity activity : plan.getActivities()) {
                System.out.println(activity.getName() + " " + activity.getDescription());
            }
        }
        System.out.println("Ratings: ");
        if (plan.getRating().isEmpty()) {
            System.out.println("No rating yet");
        } else {
            System.out.println(planController.calculateAverage(plan.getId()));
        }
        System.out.println();
    }

    public void addActivityToPlan() {
        System.out.println("\n--- Add Activity to Plan ---");
        try {
            System.out.print("Enter plan ID: ");
            int planID = scanner.nextInt();
            System.out.print("Enter activity ID: ");
            int activityID = scanner.nextInt();

            planController.addActivity(planID, activityID);
            System.out.println("Plan successfully added!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteActivityToPlan() {
        System.out.println("\n--- Delete Activity to Plan ---");
        try {
            System.out.print("Enter plan ID: ");
            int planID = scanner.nextInt();
            System.out.print("Enter activity ID: ");
            int activityID = scanner.nextInt();

            planController.deleteActivity(planID, activityID);
            System.out.println("Plan successfully deleted!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public void joinPlan() {
        System.out.println("\n--- Join Plan ---");
        try {
            System.out.print("Enter plan ID to join: ");
            int planID = scanner.nextInt();

            planController.joinPlan(planID);
            System.out.println("Joined plan successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void leavePlan() {
        System.out.println("\n--- Leave Plan ---");
        try {
            listUserPlans();
            System.out.print("Enter plan ID to leave: ");
            int planID = scanner.nextInt();

            planController.leavePlan(planID);
            System.out.println("Left plan successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void ratePlan() {
        System.out.println("\n--- Rate Plan ---");
        try {
            listUserPlans();
            System.out.print("Enter plan ID to rate: ");
            int planID = scanner.nextInt();

            System.out.print("Enter rating (1-5): ");
            int rating = scanner.nextInt();

            planController.ratePlan(planID, rating);
            System.out.println("Plan rated successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public void listUserPlans() {
        System.out.println("\n--- User Plans ---");
        if(planController.listUserPlans().isEmpty()) {
            System.out.println("You have not signed up for any plan yet.");
        } else {
            for (Plan plan : planController.listUserPlans()) {
                System.out.println("ID: " + plan.getId() + ", Name: " + plan.getName() + ", Owner: " + plan.getOwner().getName());
            }
        }
    }

    public void viewTotalDurationAndCost() {
        System.out.println("\n--- View Total Duration and Cost of a Plan ---");
        try {
            listUserPlans();
            System.out.print("Enter plan ID to view details: ");
            int planID = scanner.nextInt();

            int totalDuration = planController.totalDuration(planID);
            double totalCost = planController.totalCost(planID);

            System.out.println("Total Duration: " + totalDuration + " minutes");
            System.out.println("Total Cost: $" + totalCost);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void listPlansSortedByDate() {
        System.out.println("\n--- List Plans Sorted by Date ---");
        List<Plan> sortedPlans = planController.listPlansSortedDate();
        for (Plan plan : sortedPlans) {
            System.out.println("ID: " + plan.getId() + ", Name: " + plan.getName() +
                    ", Owner: " + plan.getOwner().getName() + ", Date: " + plan.getDateTime());
        }
    }

    public void listPlansSortedByPunctuation() {
        System.out.println("\n--- List Plans Sorted by Punctuation ---");
        List<Plan> sortedPlans = planController.listPlansSortedPunctuation();
        for (Plan plan : sortedPlans) {
            System.out.println("ID: " + plan.getId() + ", Name: " + plan.getName() +
                    ", Owner: " + plan.getOwner().getName() + ", Average Rating: " + planController.calculateAverage(plan.getId()));
        }
    }

    public void searchActivityTheatre() {
        System.out.println("\n--- Search Plans with Theatre Activities ---");
        List<Plan> theatrePlans = planController.searchActivityTheatre();
        if (theatrePlans.isEmpty()) {
            System.out.println("No plans with Theatre activities found.");
        } else {
            for (Plan plan : theatrePlans) {
                System.out.println("ID: " + plan.getId() + ", Name: " + plan.getName() + ", Owner: " + plan.getOwner().getName());
            }
        }
    }

    public void searchActivityCinema() {
        System.out.println("\n--- Search Plans with Cinema Activities ---");
        if (planController.searchActivityCinema().isEmpty()) {
            System.out.println("No plans with Cinema activities found.");
        } else {
            for (Plan plan : planController.searchActivityCinema()) {
                System.out.println("ID: " + plan.getId() + ", Name: " + plan.getName() + ", Owner: " + plan.getOwner().getName());
            }
        }
    }

    public void showCheapestPlan() {
        System.out.println("\n--- Show Cheapest Plan ---");
        Plan cheapestPlan = planController.cheapestPlan();
        System.out.println(String.format("Cheapest Plan: ID - %d, Name - %s, Owner - %s, Cost - $%.2f",
                cheapestPlan.getId(), cheapestPlan.getName(),
                cheapestPlan.getOwner().getName(), planController.totalCost(cheapestPlan.getId())));
    }

}
