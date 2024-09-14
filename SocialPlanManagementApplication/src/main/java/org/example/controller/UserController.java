package org.example.controller;

import org.example.model.Activity;
import org.example.model.Plan;
import org.example.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserController {
    private Map<String, User> userMap;
    private static User loggedUser;
    private static final int MIN_AGE = 14;
    private static final int MAX_AGE = 100;
    private static final int MIN_LENGTH = 3;


    public UserController() {
        this.userMap = new HashMap<>();
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    public void register(String name, int age, int phone, String password) throws Exception {
        nameCheck(name);
        ageCheck(age);
        phoneCheck(phone);
        passwordLengthCheck(password);
        userMap.put(name, new User(name, age, phone, password));
    }

    public boolean login(String name, String password) throws  Exception {
        boolean loginCheck = false;
        if (userMap.containsKey(name)) {
            User user = userMap.get(name);
            if (user.getPassword().equals(password)) {
                if (user.getPassword().equals(password)) {
                    loggedUser = user;
                    System.out.println("User log in successfully");
                    loginCheck = true;
                }
            } else {
                throw new Exception("Incorrect password.");
            }
        } else {
            throw new Exception("User not found.");
        }
        return loginCheck;
    }


    public void logout() {
        loggedUser = null;
    }

    public static void joinPlan(Plan plan) {
        // We create a copy the plan, so we can apply discounts safely without updating the list of all plans
        // This way discounts are specific to the user joining the plan
        loggedUser.addPlan(copyPlan(plan));
    }

    private static Plan copyPlan(Plan plan) {
        return new Plan.Builder()
                .id(plan.getId())
                .owner(getLoggedUser())
                .name(plan.getName())
                .dateTime(plan.getDateTime())
                .location(plan.getLocation())
                .maxCapacity(plan.getMaxCapacity())
                .build();
    }


    private static List<Activity> copyActivityList(List<Activity> activities) {
        List<Activity> copiedList = new ArrayList<>(activities.size());
        activities.forEach(activity ->
                copiedList.add(new Activity(
                        activity.getName(),
                        activity.getDescription(),
                        activity.applyDiscount(),
                        activity.getDuration(),
                        activity.getCapacity(),
                        activity.getType())
                ));
        return copiedList;
    }


    public static void leavePlan(Plan plan) {
        loggedUser.deletePlan(plan);
    }

    private void nameCheck(String name) throws Exception {
        if (userMap.containsKey(name)) {
            throw new Exception("Existing user name.");
        }
    }

    private void phoneCheck(int phone) throws Exception {
        for(User user : userMap.values()) {
            if (user.getPhone() == phone) {
                throw new Exception("Existing user phone.");
            }
        }
    }

    private void ageCheck(int age) throws Exception {
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new Exception("The age must be between " + MIN_AGE + " and " + MAX_AGE + " years.");
        }
    }

    private void passwordLengthCheck(String password) throws Exception {
        if (password.length() < MIN_LENGTH) {
            throw new Exception("Password must be longer than 3 characters.");
        }
    }
}
