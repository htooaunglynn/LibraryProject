package com.librarymanagementsystem.controller;

import com.librarymanagementsystem.LibraryApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookController {

    @FXML
    void btnAuthor(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("Author.fxml");
    }

    @FXML
    void btnCategory(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("Category.fxml");
    }

    @FXML
    void btnAdd(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("BookAdd.fxml");
    }

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("AdminLogin.fxml");
    }

    @FXML
    void btnEditDelete(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("BookEditDelete.fxml");
    }

    @FXML
    void btnList(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("BookList.fxml");
    }

    @FXML
    void btnSearch(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("BookSearch.fxml");
    }

    @FXML
    void btnAdmin(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("AdminCRUD.fxml");
    }

    @FXML
    void btnStudent(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("StudentCRUD.fxml");
    }

    @FXML
    void btnTransaction(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("Transaction.fxml");
    }

}
