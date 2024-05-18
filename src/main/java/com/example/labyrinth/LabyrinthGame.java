package com.example.labyrinth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LabyrinthGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LabyrinthGame.class.getResource("hello-view.fxml"));
        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root, 660, 487); // Set the size according to your FXML layout
        stage.setTitle("Labyrinth Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
