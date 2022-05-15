package com.example.personalfinance.entity;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String userName;
    private List<MonthOfYear>  monthOfYears;

    public User() {
    }

    public User(String userName, List<MonthOfYear> monthOfYears) {
        this.userName = userName;
        this.monthOfYears = monthOfYears;
    }


    public List<MonthOfYear> getMonthOfYears() {
        return monthOfYears;
    }

    public void setMonthOfYears(List<MonthOfYear> monthOfYears) {
        this.monthOfYears = monthOfYears;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double totalMoney(){
        double total = 0;
        for (MonthOfYear item: this.getMonthOfYears()){
            total += item.totalMoneyInMonth();
        }
        return total;
    }


    @Override
    public String toString() {
        return "User{" +
                ", monthOfYears=" + monthOfYears +
                '}';
    }
}
