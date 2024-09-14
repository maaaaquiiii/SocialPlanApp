package org.example.view;

import org.example.controller.ActivityController;
import org.example.model.Activity;


public class ActivityView extends View {
    private ActivityController activityController;

    public ActivityView() {
        this.activityController = new ActivityController();
    }
    public void createActivity() {
        try {
            System.out.println("Enter the details of the new activity:");
            // consuming the leftover newline character.
            scanner.nextLine();
            System.out.print("Name: ");
            String name = scanner.nextLine();
            // consuming the leftover newline character.
           // scanner.nextLine();
            System.out.print("Description: ");
            String description = scanner.nextLine();

            System.out.print("Cost of the activity (use ',' for decimals): ");
            double cost = scanner.nextDouble();
            System.out.print("Duration (in minutes): ");
            int duration = scanner.nextInt();
            System.out.print("Capacity: ");
            int capacity = scanner.nextInt();
            String type = selectActivityType();

            Activity activity = activityController.createActivity(name, description, cost, duration, capacity, type);

            System.out.println("Activity created successfully.");

            showActivityDetails(activity);
        } catch (Exception e) {
            System.out.println("Error creating activity: " + e.getMessage());
        }
    }

    private String selectActivityType() {
        System.out.println("Choose the type of activity (just number):");
        System.out.println("1. Theatre");
        System.out.println("2. Cinema");
        System.out.println("3. Generic");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                return "THEATRE";
            case 2:
                return "CINEMA";
            default:
                return "GENERIC";
        }
    }

    public void showAllActivities() {
        System.out.println("All Activities:");
        activityController.getAllActivities().forEach(this::showActivityDetails);
    }

    private void showActivityDetails(Activity activity) {
        System.out.printf("ID: %s\tName: %s\tDescription: %s\tCost: %s\tDuration: %s minutes\tCapacity: %s\tType: %s%n",
                activity.getId(), activity.getName(), activity.getDescription(),
                activity.getCost(), activity.getDuration(), activity.getCapacity(), activity.getType());
    }
}
