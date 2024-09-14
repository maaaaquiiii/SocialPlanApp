package org.example.model;


import static org.example.controller.ActivityController.isRetired;
import static org.example.controller.ActivityController.isYoungTheatre;
import static org.example.controller.UserController.getLoggedUser;

public class Theatre extends Activity {
    private static double YOUNG_DISCOUNT = 0.5;
    private static final double RETIRED_DISCOUNT = 0.3;

    public Theatre(String name, String description, double cost, int duration, int capacity, String type) {
        super(name, description, cost, duration,capacity, type);
    }


    public static double getYoungDiscount() {
        return YOUNG_DISCOUNT;
    }

    public static double getRetiredDiscount() {
        return RETIRED_DISCOUNT;
    }

    @Override
    public double applyDiscount() {
        if (isRetired(getLoggedUser().getAge())) {
            return getCost() * getRetiredDiscount();
        } else if (isYoungTheatre(getLoggedUser().getAge())) {
            return getCost() * getYoungDiscount();
        }
        return getCost();
    }
}
