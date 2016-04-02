package com.univer.num.lab_2;

import com.univer.num.lab_2.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("view/main_scene.fxml"));
        primaryStage.setTitle("Чисельні методи: лабораторна робота №2");
        primaryStage.setScene(new Scene((Parent) mainLoader.load()));
        primaryStage.show();

        MainController mainController = mainLoader.getController();
        mainController.setPrimaryStage(primaryStage);
        mainController.init();
    }
}
