package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private int age;
    private int phone;
    private String password;
    private List<Plan> plans;

    public User(String name, int age, int phone, String password) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.password = password;
        this.plans = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    public void addPlan(Plan newPlan) {
        plans.add(newPlan);
    }

    public void deletePlan(Plan planDeleted) {
        plans = plans.stream()
                .filter(plans -> planDeleted.getId() != planDeleted.getId())
                .toList();
    }
}
