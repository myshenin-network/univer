package com.univer.oop.lab_7.controller;

import com.univer.oop.lab_7.model.Toy;
import com.univer.oop.lab_7.model.ToyDriver;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.function.Predicate;

public class FilterController {
    private ToyDriver toyDriver;
    private Stage thisStage;
    private TableView<Toy> tableView;
    private FilteredList<Toy> toys;
    @FXML
    private TextField priceText;

    public ToyDriver getToyDriver() {
        return toyDriver;
    }

    public void setToyDriver(ToyDriver toyDriver) {
        this.toyDriver = toyDriver;
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public TableView<Toy> getTableView() {
        return tableView;
    }

    public void setTableView(TableView<Toy> tableView) {
        this.tableView = tableView;
    }

    public void execute(){
        toys = new FilteredList<Toy>(toyDriver.getToys(), new Predicate<Toy>() {
            @Override
            public boolean test(Toy toy) {
                if(toy.getPrice() <= Double.parseDouble(priceText.getText())) {
                    return true;
                }
                return false;
            }
        });
        priceText.setText("");
        tableView.setItems(toys);
        thisStage.close();
    }
    public void cancel(){
        priceText.setText("");
        thisStage.close();
    }
}
