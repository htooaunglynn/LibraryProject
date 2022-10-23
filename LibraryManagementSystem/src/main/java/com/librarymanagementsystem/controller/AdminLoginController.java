package com.librarymanagementsystem.controller;

import com.librarymanagementsystem.LibraryApplication;
import com.librarymanagementsystem.model.Admin;
import com.librarymanagementsystem.model.DatabaseService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AdminLoginController {
    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("Library.fxml");
    }

    @FXML
    void btnLogin(ActionEvent event) throws IOException {

        var email = txtUserName.getText();
        var password = txtPassword.getText();

        if (email.equals("")) {
            LibraryApplication.showAlert(Alert.AlertType.ERROR, "Email is required");
            return;
        }

        if (password.equals("")) {
            LibraryApplication.showAlert(Alert.AlertType.ERROR, "Password is required");
            return;
        }

        Admin admin = DatabaseService.login(email, password);

        if (admin == null) {
            LibraryApplication.showAlert(Alert.AlertType.ERROR, "Authenication fail");
        } else {
            LibraryApplication.loginAdmin = admin;
            LibraryApplication.changleScence("Book.fxml");
        }

    }
}
