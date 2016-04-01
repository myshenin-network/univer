package com.univer.oop.lab_7.controller;

import com.univer.oop.lab_7.model.ACTION;
import com.univer.oop.lab_7.model.SaveBoolean;
import com.univer.oop.lab_7.model.Toy;
import com.univer.oop.lab_7.model.ToyDriver;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class MainController {
    private Stage primaryStage;
    private FileChooser chooser;
    private Alert saveAlert;
    private ButtonType ok;
    private ButtonType no;
    private ButtonType cancel;
    private FXMLLoader recordLoader;
    private Alert info;
    private Stage filterStage;
    private FXMLLoader filterLoader;


    @FXML
    private TableView<Toy> contentTable;
    @FXML
    private TableColumn<Toy, String> nameColumn;
    @FXML
    private TableColumn<Toy, Integer> amountColumn;
    @FXML
    private TableColumn<Toy, Double> priceColumn;
    @FXML
    private TableColumn<Toy, String> ageColumn;
    @FXML
    private CheckMenuItem filter;

    private ToyDriver toyDriver;
    private SaveBoolean saveBoolean;
    private Stage recordStage;
    private RecordController recordController;
    private FilterController filterController;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void launch() throws IOException {
        toyDriver = new ToyDriver();
        saveBoolean = new SaveBoolean(true);

        chooser = new FileChooser();
        chooser.setInitialDirectory(new File("D:\\univer\\OOP_LAB_7\\src\\com\\univer\\oop\\lab_7\\res\\"));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text File", "*.txt"));

        saveAlert = new Alert(Alert.AlertType.CONFIRMATION);
        saveAlert.setTitle("Ви не виконали зберігання");
        saveAlert.setHeaderText("Ви не виконали зберігання!");
        saveAlert.setContentText("Зберігти?");
        ok = new ButtonType("Зберігти");
        no = new ButtonType("Ні");
        cancel = new ButtonType("Відмінити");
        saveAlert.getButtonTypes().setAll(ok, no, cancel);

        info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Лаборатона робота №7");
        info.setHeaderText("Лабораторна робота №7");
        info.setContentText("Предмет: Об\'єктно-орієнтоване програмування\n" +
                "Виконав: студент групи КН-26\n" +
                "\t\tМишенін А.С.\n" +
                "Перевірив: \n" +
                "асистент Дупак Б.П.\n" +
                "Рік: 2016");

        contentTable.prefHeightProperty().bind(primaryStage.heightProperty());
        contentTable.prefWidthProperty().bind(primaryStage.widthProperty());
        contentTable.setItems(toyDriver.getToys());

        nameColumn.prefWidthProperty().bind(contentTable.prefWidthProperty().multiply(0.5));
        amountColumn.prefWidthProperty().bind(contentTable.prefWidthProperty().multiply(0.1));
        priceColumn.prefWidthProperty().bind(contentTable.prefWidthProperty().multiply(0.2));
        ageColumn.prefWidthProperty().bind(contentTable.prefWidthProperty().multiply(0.2));

        nameColumn.setCellValueFactory(new PropertyValueFactory<Toy, String>("name"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<Toy, Integer>("amount"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Toy, Double>("price"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<Toy, String>("age"));

        recordLoader = new FXMLLoader(getClass().getResource("../view/record_scene.fxml"));
        recordStage = new Stage();
        recordStage.setScene(new Scene((Parent)recordLoader.load()));
        recordStage.initModality(Modality.APPLICATION_MODAL);
        recordStage.setResizable(false);
        recordController = recordLoader.getController();
        recordController.setToyDriver(toyDriver);
        recordController.setTableView(contentTable);
        recordController.setThisStage(recordStage);
        recordController.setSaveBoolean(saveBoolean);

        filterLoader = new FXMLLoader(getClass().getResource("../view/filter_scene.fxml"));
        filterStage = new Stage();
        filterStage.setTitle("Фільтр");
        filterStage.setScene(new Scene((Parent) filterLoader.load()));
        filterStage.initModality(Modality.APPLICATION_MODAL);
        filterStage.setResizable(false);
        filterController = filterLoader.getController();
        filterController.setToyDriver(toyDriver);
        filterController.setThisStage(filterStage);
        filterController.setTableView(contentTable);


        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    event.consume();
                    quit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void open() throws IOException {
        if(!saveBoolean.isSave()){
            Optional<ButtonType> result = saveAlert.showAndWait();
            if(result.get() == ok){
                save();
            } else if (result.get() == cancel){
                return;
            }
        }

        File base = chooser.showOpenDialog(primaryStage);
        if(base != null){
            toyDriver = new ToyDriver(base);
            recordController.setToyDriver(toyDriver);
            filterController.setToyDriver(toyDriver);
            contentTable.setItems(toyDriver.getToys());

            saveBoolean.setSave(true);
        }
    }

    public void save() throws IOException {
        File base = chooser.showSaveDialog(primaryStage);
        if(base != null){
            if(!base.exists()){
                base.createNewFile();
            }
            toyDriver.toFile(base);

            saveBoolean.setSave(true);
        }
    }

    public void quit() throws IOException {
        if(!saveBoolean.isSave()){
            Optional<ButtonType> result = saveAlert.showAndWait();
            if(result.get() == ok){
                save();
            } else if(result.get() == cancel){
                return;
            }
        }

        System.exit(0);
    }

    public void create() throws IOException {
        if(!saveBoolean.isSave()){
            Optional<ButtonType> result = saveAlert.showAndWait();
            if(result.get() == ok){
                save();
            } else if(result.get() == cancel){
                return;
            }
        }

        toyDriver.setToys(FXCollections.<Toy>observableArrayList());
        contentTable.setItems(toyDriver.getToys());

        saveBoolean.setSave(true);
    }

    public void addToy(){
        recordController.setAction(ACTION.ADD);
        recordController.make();
        recordStage.show();
    }

    public void updateToy(){
        recordController.setAction(ACTION.UPDATE);
        recordController.make();
        recordStage.show();
    }

    public  void deleteToy(){
        recordController.delete();
    }

    public void showInfo(){
        info.show();
    }

    public void executeFilter(){
        if(filter.isSelected()){
            filterStage.show();
        } else {
            contentTable.setItems(toyDriver.getToys());
        }
    }
}
