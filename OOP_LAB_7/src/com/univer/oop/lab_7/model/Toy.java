package com.univer.oop.lab_7.model;

import java.io.Serializable;

public class Toy implements Serializable {
    private String name;
    private double price;
    private int amount;
    private Age age;

    public Toy() {
        name = "NONE";
        price = 0;
        amount = 0;
        age = new Age();
    }

    public Toy(String name, double price, int amount, Age age) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.age = age;
    }

    public Toy(String name, double price, int amount, int start, int end) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.age = new Age(start, end);
    }

    public Toy(String characteristic){
        String[] values = characteristic.split(" ");
        name = values[0];
        price = Double.parseDouble(values[1]);
        amount = Integer.parseInt(values[2]);
        age = new Age(Integer.parseInt(values[3]), Integer.parseInt(values[4]));
    }

    @Override
    public String toString() {
        return name + " " + price + " " + amount + " " + age.getStart() + " " + age.getEnd() + "\n";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

}
