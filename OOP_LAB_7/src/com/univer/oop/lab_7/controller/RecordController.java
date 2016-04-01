package com.univer.oop.lab_7.controller;

import com.univer.oop.lab_7.model.ACTION;
import com.univer.oop.lab_7.model.SaveBoolean;
import com.univer.oop.lab_7.model.Toy;
import com.univer.oop.lab_7.model.ToyDriver;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RecordController {
    private ACTION action;
    private ToyDriver toyDriver;
    private TableView<Toy> tableView;
    private Stage thisStage;
    private SaveBoolean saveBoolean;

    @FXML
    private TextField nameText;
    @FXML
    private TextField priceText;
    @FXML
    private TextField amountText;
    @FXML
    private TextField startText;
    @FXML
    private TextField endText;
    @FXML
    private Button executeButton;

    public ACTION getAction() {
        return action;
    }

    public void setAction(ACTION action) {
        this.action = action;
    }

    public ToyDriver getToyDriver() {
        return toyDriver;
    }

    public void setToyDriver(ToyDriver toyDriver) {
        this.toyDriver = toyDriver;
    }

    public TableView<Toy> getTableView() {
        return tableView;
    }

    public void setTableView(TableView<Toy> tableView) {
        this.tableView = tableView;
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public SaveBoolean getSaveBoolean() {
        return saveBoolean;
    }

    public void setSaveBoolean(SaveBoolean saveBoolean) {
        this.saveBoolean = saveBoolean;
    }

    public void execute(){
        if(action == ACTION.ADD){
            toyDriver.getToys().add(new Toy(nameText.getText(), Double.parseDouble(priceText.getText()),
                    Integer.parseInt(amountText.getText()),
                    Integer.parseInt(startText.getText()),
                    Integer.parseInt(endText.getText())));
            saveBoolean.setSave(false);

            thisStage.close();
        } else {
            toyDriver.getToys().set(tableView.getSelectionModel().getFocusedIndex(), new Toy(nameText.getText(), Double.parseDouble(priceText.getText()),
                    Integer.parseInt(amountText.getText()),
                    Integer.parseInt(startText.getText()),
                    Integer.parseInt(endText.getText())));
            saveBoolean.setSave(false);
            tableView.getSelectionModel().clearSelection();
            thisStage.close();
        }

        toyDriver.sort();
    }

    public void delete(){
        toyDriver.getToys().remove(tableView.getSelectionModel().getFocusedIndex());
        tableView.getSelectionModel().clearSelection();
        toyDriver.sort();
    }
    public void cancel(){
        nameText.setText("");
        priceText.setText("");
        amountText.setText("");
        startText.setText("");
        endText.setText("");

        tableView.getSelectionModel().clearSelection();
        thisStage.close();
    }

    public void make(){
        thisStage.setTitle(action == ACTION.ADD ? "Додати": "Змінити");
        executeButton.setText(action == ACTION.ADD ? "Додати": "Змінити");

        if(action == ACTION.ADD){
            nameText.setText("");
            priceText.setText("");
            amountText.setText("");
            startText.setText("");
            endText.setText("");
        } else {
            nameText.setText(tableView.getSelectionModel().getSelectedItem().getName());
            priceText.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getPrice()));
            amountText.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getAmount()));
            startText.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getAge().getStart()));
            endText.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getAge().getEnd()));
        }

    }
}
