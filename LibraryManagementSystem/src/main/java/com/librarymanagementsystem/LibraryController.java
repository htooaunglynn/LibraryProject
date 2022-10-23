package com.librarymanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class LibraryController {
    @FXML
    void btnAdmin(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("AdminLogin.fxml");
    }

    @FXML
    void btnStudent(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("StudentRegister.fxml");
    }
}