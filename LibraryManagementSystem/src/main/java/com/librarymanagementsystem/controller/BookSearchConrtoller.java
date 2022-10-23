package com.librarymanagementsystem.controller;

import com.librarymanagementsystem.LibraryApplication;
import com.librarymanagementsystem.model.Book;
import com.librarymanagementsystem.model.DatabaseService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookSearchConrtoller implements Initializable {

    @FXML
    private TableColumn<Book, String> colAuthor;

    @FXML
    private TableColumn<Book, String> colCategory;

    @FXML
    private TableColumn<Book, Integer> colCode;

    @FXML
    private TableColumn<Book, Integer> colCreatedBy;

    @FXML
    private TableColumn<Book, Boolean> colAvailable;

    @FXML
    private TableColumn<Book, String> colTitle;

    @FXML
    private TableView<Book> tblBookSearch;

    @FXML
    private TextField txtBookAuthor;

    @FXML
    private TextField txtBookCategory;

    @FXML
    private TextField txtBookTitle;

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("Book.fxml");
    }

    @FXML
    void btnSearch(ActionEvent event) {
        var category = txtBookCategory.getText();
        var author = txtBookAuthor.getText();
        var title = txtBookTitle.getText();

        List<Book> bookList = DatabaseService.searchBook(title, author, category);

        tblBookSearch.setItems(FXCollections.observableArrayList(bookList));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("available"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        colCreatedBy.setCellValueFactory(new PropertyValueFactory<>("adminName"));

    }
}
