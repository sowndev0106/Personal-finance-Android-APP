package com.example.personalfinance.entity;

import java.util.List;

public class MonthOfYear {
    private int year;
    private int month;
    private List<DateOfMonth> dateOfMonths;

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

    @Override
    public String toString() {
        return "MonthOfYear{" +
                "year=" + year +
                ", month=" + month +
                ", dateOfMonths=" + dateOfMonths +
                '}';
    }
}
