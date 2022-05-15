package com.example.personalfinance.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MonthOfYear implements Serializable, Comparable<MonthOfYear> {
    private int year;
    private int month;
    private List<DateOfMonth> dateOfMonths =  new ArrayList<>();

    public MonthOfYear() {
    }

    public MonthOfYear(int year, int month, List<DateOfMonth> dateOfMonths) {
        this.year = year;
        this.month = month;
        this.dateOfMonths = dateOfMonths;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<DateOfMonth> getDateOfMonths() {
        return dateOfMonths;
    }

    public void setDateOfMonths(List<DateOfMonth> dateOfMonths) {
        this.dateOfMonths = dateOfMonths;
    }

    public double totalMoneyInMonth(){
        double total = 0;
        for (DateOfMonth item : this.dateOfMonths){
            total += item.totalMoneyInDate();
        }
        return total;
    }

    public double totalInMoneyInMonth(){
        double total = 0;
        for (DateOfMonth item : this.dateOfMonths){
            total += item.totalInMoneyInDate();
        }
        return total;
    }

    public double totalOutMoneyInMonth(){
        double total = 0;
        for (DateOfMonth item : this.dateOfMonths){
            total += item.totalOutMoneyInDate();
        }
        return total;
    }

    @Override
    public String toString() {
        return "MonthOfYear{" +
                "year=" + year +
                ", month=" + month +
                ", dateOfMonths=" + dateOfMonths +
                '}';
    }

    @Override
    public int compareTo(MonthOfYear monthOfYear) {
        if (this.getYear()>monthOfYear.getYear()){
            return 1;
        }else if (this.getYear()<monthOfYear.getYear()){
            return -1;
        }else{
            return this.getMonth()>=monthOfYear.getMonth()?1:-1;
        }
    }
}
