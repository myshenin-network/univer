package com.univer.num.lab_2.model;

import java.io.Serializable;

public class Formula implements Serializable{
    private double begin;
    private double end;
    private String expression;

    public Formula() {
        begin = 0;
        end = 0;
        expression = "NONE";
    }

    public Formula(double begin, double end, String expression) {
        this.begin = begin;
        this.end = end;
        this.expression = expression;
    }

    public double getBegin() {
        return begin;
    }

    public void setBegin(double begin) {
        this.begin = begin;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Formula{" +
                "begin=" + begin +
                ", end=" + end +
                ", expression='" + expression + '\'' +
                '}';
    }
}
