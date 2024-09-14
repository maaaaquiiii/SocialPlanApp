package org.example.model;

import java.time.LocalDateTime;
import java.util.List;


public class Plan {
    private int id;
    private String name;
    private LocalDateTime dateTime;

    private String location;
    private int maxCapacity;
    private List<Activity> activities;
    private List<User> participants;
    private User owner; // the only one who can alter or eliminate
    private List<Integer> ratings;

    private Plan(Builder planBuilder) {
        this.id = planBuilder.id;
        this.name = planBuilder.name;
        this.dateTime = planBuilder.dateTime;
        this.location = planBuilder.location;
        this.maxCapacity = planBuilder.maxCapacity;
        this.activities = planBuilder.activities;
        this.participants = planBuilder.MaxParticipants;
        this.owner = planBuilder.owner;
        this.ratings = planBuilder.ratings;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getLocation() {
        return location;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public User getOwner() {
        return owner;
    }

    public List<Integer> getRating() {
        return ratings;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setRating(List<Integer> ratings) {
        this.ratings = ratings;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void deleteActivity(Activity activity) {
        activities.remove(activity);
    }

    public void addParticipants(User user) {
        participants.add(user);
    }

    public void leaveParticipant(User user) {
        participants.remove(user);
    }

    public void addRating(Integer rating) {
        ratings.add(rating);
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Owner: %s, Date and Time: %s, Location: %s, Max Capacity: %d, Ratings: %f",
                getId(), getName(), getOwner().getName(), getDateTime(), getLocation(), getMaxCapacity(), getRating());
    }

    public static class Builder {
        private static int idCounter = 1;
        private int id;
        private String name;
        private LocalDateTime dateTime;
        private String location;
        private int maxCapacity;
        private List<Activity> activities;
        private List<User> MaxParticipants;
        private User owner;
        private List<Integer> ratings;

        public Builder() {
            this.id = idCounter++;
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder dateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder maxCapacity(int maxCapacity) {
            this.maxCapacity = maxCapacity;
            return this;
        }

        public Builder activities(List<Activity> activities) {
            this.activities = activities;
            return this;
        }

        public Builder participants(List<User> participants) {
            this.MaxParticipants = participants;
            return this;
        }

        public Builder owner(User owner) {
            this.owner = owner;
            return this;
        }

        public Builder ratings(List<Integer> ratings) {
            this.ratings = ratings;
            return this;
        }

        public Plan build() {
            return new Plan(this);
        }
    }
}

