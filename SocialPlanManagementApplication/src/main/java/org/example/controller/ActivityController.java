package org.example.controller;

import org.example.model.Activity;
import org.example.model.Cinema;
import org.example.model.Theatre;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class ActivityController {
    private static List<Activity> allActivities;
    private static final int MAX_AGE_CINEMA = 21;
    private static final int MIN_AGE_THEATRE = 65;
    private static final int MAX_AGE_THEATRE = 25;
    private static final String CINEMA = "Cinema";
    private static final String THEATRE = "Theatre";

    public ActivityController() {
        allActivities = new ArrayList<>();
    }

    public List<Activity> getAllActivities() {
        return allActivities ;
    }

    public Activity createActivity(String name, String description, double cost, int duration, int capacity, String type) {
        Activity newActivity = switch (type) {
            case THEATRE -> new Theatre(name, description, cost, duration, capacity, type);
            case CINEMA -> new Cinema(name, description, cost, duration, capacity, type);
            default -> new Activity(name, description, cost, duration, capacity, type);
        };
        allActivities.add(newActivity);

        return newActivity;
    }

    protected static Activity findActivity(int activityID) throws NoSuchElementException {
        for (Activity currentActivity : allActivities) {
            if (currentActivity.getId() == activityID) {
                return currentActivity;
            }
        }
        throw new NoSuchElementException("Activity with ID: " + activityID + " does not exist");
    }


    public static boolean isYoungCinema(int age) {
        return age < MAX_AGE_CINEMA;
    }


    public static boolean isYoungTheatre(int age) {
        return age < MAX_AGE_THEATRE;
    }

    public static boolean isRetired(int age) {
        return age > MIN_AGE_THEATRE;
    }


}
