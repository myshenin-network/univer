package com.univer.oop.lab_7.model;

import java.io.Serializable;

public class Age implements Serializable{
    private int start;
    private int end;

    public Age() {
        start = 0;
        end = 99;
    }

    public Age(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return start + " - " + end;
    }
}
