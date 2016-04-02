package com.univer.oop.lab_2;

public class MegaCity extends City {

    public MegaCity(String name, int population, int speed_limit) {
        super(name, population);
        this.speed_limit = speed_limit;
    }

    public int getSpeed_limit() {
        return speed_limit;
    }

    public void setSpeed_limit(int speed_limit) {
        this.speed_limit = speed_limit;
    }

    private int speed_limit;
}
