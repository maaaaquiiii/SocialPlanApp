package org.example.model;


import static org.example.controller.ActivityController.isYoungCinema;
import static org.example.controller.UserController.getLoggedUser;


public class Cinema extends Activity {
    private static double YOUNG_DISCOUNT = 0.5;

    public Cinema(String name, String description, double cost, int duration, int capacity, String type) {
        super(name, description, cost, duration, capacity, type);
    }

    public static double getYoungDiscount() {
        return YOUNG_DISCOUNT;
    }

    @Override
    public double applyDiscount() {
        if (isYoungCinema(getLoggedUser().getAge())) {
            return getCost() * getYoungDiscount();
        }
        return getCost();
    }
}
