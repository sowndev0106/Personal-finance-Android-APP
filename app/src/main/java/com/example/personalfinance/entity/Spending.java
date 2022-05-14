package com.example.personalfinance.entity;

public class Spending {
    private long id;
    private String type;
    private int img;
    private String note;
    private double money;

    public Spending() {
    }

    public Spending(long id, String type, int img, String note, double money) {
        this.id = id;
        this.type = type;
        this.img = img;
        this.note = note;
        this.money = money;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Spending{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", img='" + img + '\'' +
                ", note='" + note + '\'' +
                ", money=" + money +
                '}';
    }
}
