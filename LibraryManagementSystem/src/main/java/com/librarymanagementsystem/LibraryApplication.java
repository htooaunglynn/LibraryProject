package com.librarymanagementsystem;

import com.librarymanagementsystem.model.Admin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class LibraryApplication extends Application {

    private static Stage originalState;

    public static Admin loginAdmin = new Admin();
    

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("Library.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Library Management System");
        stage.show();
        stage.setResizable(false);
        stage.setScene(scene);
        originalState = stage;
    }

    public static void changleScence(String inputFile) throws IOException {
        Parent root = FXMLLoader.load(LibraryApplication.class.getResource(inputFile));

        Scene scene = new Scene(root);

        originalState.hide();

        originalState.setScene(scene);

        originalState.show();
    }

    public static void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Message");
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}