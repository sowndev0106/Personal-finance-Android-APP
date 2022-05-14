package com.example.personalfinance.entity;

import java.util.List;

public class User {
    private String id;
    private List<MonthOfYear>  monthOfYears;

    public User() {
    }

    public User(String id, List<MonthOfYear> monthOfYears) {
        this.id = id;
        this.monthOfYears = monthOfYears;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MonthOfYear> getMonthOfYears() {
        return monthOfYears;
    }

    public void setMonthOfYears(List<MonthOfYear> monthOfYears) {
        this.monthOfYears = monthOfYears;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", monthOfYears=" + monthOfYears +
                '}';
    }
}
