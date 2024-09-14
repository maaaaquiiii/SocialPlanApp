package org.example.controller;

import org.example.model.Activity;
import org.example.model.Plan;
import org.example.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.example.controller.UserController.getLoggedUser;

public class PlanController {
    private static final int TRAVEL_TIME = 20;
    private List<Plan> allPlans;
    private ActivityController activityController;
    private static final String CINEMA = "CINEMA";
    private static final String THEATRE = "THEATRE";


    public PlanController() {
        this.activityController = new ActivityController();
        this.allPlans = new ArrayList<>();
    }

    public List<Plan> getAllPlans() {
        return allPlans;
    }

    public void setAllPlans(List<Plan> allPlans) {
        this.allPlans = allPlans;
    }

    public Plan createPlan(String name, LocalDateTime dateTime, String location, Integer maxCapacity, User owner) {
        if (owner == null) {
            throw new IllegalArgumentException("Owner cannot be null");
        }
        Plan newPlan = new Plan.Builder()
                .id(allPlans.size())
                .owner(owner)
                .name(name)
                .dateTime(dateTime)
                .location(location)
                .maxCapacity(maxCapacity)
                .activities(new ArrayList<>())
                .participants(new ArrayList<>())
                .ratings(new ArrayList<>())
                .build();
        allPlans.add(newPlan);
        return newPlan;
    }

    public void addActivity(int planID, int activityID) throws Exception {
        Plan plan = findPlan(planID);
        int idx = allPlans.indexOf(plan);
        Activity activity = activityController.findActivity(activityID);
        if (!getLoggedUser().equals(plan.getOwner())) {
            throw new Exception("You cannot add activities to the plan because he is not the owner");
        }
        if (plan.getMaxCapacity() > activity.getCapacity()) {
            throw new Exception("Plan capacity exceeds activity capacity");
        }
        plan.addActivity(activity);
        allPlans.set(idx, plan);
    }

    public void deleteActivity(int planID, int activityID) throws Exception {
        Plan plan = findPlan(planID);
        Activity activity = activityController.findActivity(activityID);
        if (!getLoggedUser().equals(plan.getOwner())) {
            throw new Exception("You cannot delete activities to the plan because he is not the owner");
        }
        plan.deleteActivity(activity);
    }

    public Plan findPlan(int planID) throws NoSuchElementException {
        for (Plan currentPlan : allPlans) {
            if (currentPlan.getId() == planID) {
                return currentPlan;
            }
        }
        throw new NoSuchElementException("Plan with ID: " + planID + " does not exist");
    }

    public void joinPlan(int planID) throws Exception {
        Plan plan = findPlan(planID);
        for (User participant : plan.getParticipants()) {
            if(participant.equals(getLoggedUser())) {
                throw new Exception("The same plan cannot be subscribed twice.");
            }
        }
        if (plan.getParticipants().size() < plan.getMaxCapacity()) {
            plan.addParticipants(getLoggedUser());
            UserController.joinPlan(plan);
        } else {
            throw new Exception("The number of places has been exceeded.");
        }
    }

    public void leavePlan(int planID) throws Exception {
        Plan plan = findPlan(planID);
        if (plan.getOwner().equals(getLoggedUser())) {
            throw new Exception("You are the owner of the plan, you should delete the plan instead.");
        }
        if (plan.getParticipants().contains(getLoggedUser())){
            plan.leaveParticipant(getLoggedUser());
            UserController.leavePlan(plan);
        } else {
            throw new Exception("You cannot leave the plan because you are not in it.");
        }
    }

    public List<Plan> listPlansSortedDate() {
        return allPlans
                .stream()
                .sorted(Comparator.comparing(Plan::getDateTime))
                .toList();
    }


    public void ratePlan(int planID, Integer rating) throws Exception {
        Plan plan = findPlan(planID);

        if(!plan.getParticipants().contains(getLoggedUser())) {
            throw new Exception("You have not participated in this plan");
        }
        if(plan.getDateTime().isAfter(plan.getDateTime())) {
            throw new Exception("The plan has not yet been carried out");
        }
        plan.addRating(rating);
    }

    public int calculateAverage(int planID) {
        Plan plan = findPlan(planID);
        int total = 0;
        if(plan.getRating().isEmpty()) {
            total = 0;
        } else {
            for (Integer integer : plan.getRating()) {
                total += integer.intValue();
            }
            total /= plan.getRating().size();
        }
        return total;
    }


    public List<Plan> listPlansSortedPunctuation() {
        return allPlans
                .stream()
                .sorted(Comparator.comparingInt(plan -> calculateAverage(plan.getId())))
                .toList();
    }

    public List<Plan> listPlans() {
        return allPlans;
    }

    public List<Plan> listUserPlans() {
        return getLoggedUser().getPlans();
    }

    public int totalDuration(int planID) {
        Plan plan = findPlan(planID);
        int totalTime = 0;

        for(Activity activity : plan.getActivities()) {
            totalTime += activity.getDuration() + TRAVEL_TIME;
        }
        // a TRAVEL_TIME is subtracted, because when the loop is traversed, one more TRAVEL_TIME is added.
        totalTime -= TRAVEL_TIME;

        return totalTime;
    }

    public double totalCost(int planID) {
        Plan plan = findPlan(planID);
        double totalCost = 0.0;

        for(Activity activity : plan.getActivities()) {
            totalCost += activity.getCost();
        }

        return totalCost;
    }

    public List<Plan> searchActivityTheatre() {
        List<Plan> searchPlans = new ArrayList<>();
        for(Plan searchPlan : allPlans) {
            for(Activity searchActivity : searchPlan.getActivities()) {
                if(searchActivity.getType().equals(THEATRE)) {
                    searchPlans.add(searchPlan);
                    break;
                }
            }
        }
        return searchPlans;
    }

    public List<Plan> searchActivityCinema() {
        List<Plan> searchPlans = new ArrayList<>();
        for(Plan searchPlan : allPlans) {
            for(Activity searchActivity : searchPlan.getActivities()) {
                if(searchActivity.getType().equals(CINEMA)) {
                    searchPlans.add(searchPlan);
                    break;
                }
            }
        }
        return searchPlans;
    }

    public Plan cheapestPlan() {
        return allPlans
                .stream()
                .min(Comparator.comparingDouble(plan -> totalCost(plan.getId())))
                .orElseThrow();
    }

}
