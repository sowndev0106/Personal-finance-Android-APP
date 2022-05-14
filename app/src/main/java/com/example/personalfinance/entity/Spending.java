package com.example.personalfinance.entity;

public class Spending {
    private String id;
    private String type;
    private String img;
    private String note;
    private double money;

    public Spending() {
    }

    public Spending(String id, String type, String img, String note, double money) {
        this.id = id;
        this.type = type;
        this.img = img;
        this.note = note;
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
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
