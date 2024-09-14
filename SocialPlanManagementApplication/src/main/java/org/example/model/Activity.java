package org.example.model;



public class Activity {
    private static int nextID = 0;
    private int id;
    private String name;
    private String description;
    private double cost;
    private int duration;
    private int capacity;
    private String type;

    public Activity(String name, String description, double cost, int duration, int capacity, String type) {
        this.id = nextID++;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.duration = duration;
        this.capacity = capacity;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    public int getDuration() {
        return duration;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double applyDiscount() {
        return cost;
    }
}
