package com.univer.oop.lab_7;

import com.univer.oop.lab_7.controller.MainController;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/main_scene.fxml"));
        Parent root = loader.load();

        MainController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        controller.launch();

        primaryStage.setTitle("Лабораторна работа №7");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
