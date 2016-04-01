package com.univer.oop.lab_7.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Comparator;
import java.util.Scanner;

public class ToyDriver implements Serializable{
    private ObservableList<Toy> toys;

    public ToyDriver() {
        toys = FXCollections.observableArrayList();
    }

    public ToyDriver(ObservableList<Toy> toys) {
        this.toys = toys;
    }

    public ToyDriver(File file) throws FileNotFoundException {
        toys = FXCollections.observableArrayList();
        Scanner input = new Scanner(file);
        while (input.hasNext()){
            toys.add(new Toy(input.nextLine()));
        }
        input.close();
        sort();
    }

    public ObservableList<Toy> getToys() {
        return toys;
    }

    public void setToys(ObservableList<Toy> toys) {
        this.toys = toys;
    }

    public void toFile(File file) throws FileNotFoundException {
        PrintStream output = new PrintStream(new FileOutputStream(file, false));
        output.print("");

        output = new PrintStream(new FileOutputStream(file, true));
        for (Toy toy:toys){
            output.append(toy.toString());
        }
        output.close();
    }

    public void sort(){
        toys.sort(new Comparator<Toy>() {
            @Override
            public int compare(Toy o1, Toy o2) {
                return (int)(o1.getPrice() - o2.getPrice());
            }
        });
    }
}
