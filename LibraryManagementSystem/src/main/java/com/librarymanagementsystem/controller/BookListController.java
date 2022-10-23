package com.librarymanagementsystem.controller;

import com.librarymanagementsystem.LibraryApplication;
import com.librarymanagementsystem.model.Author;
import com.librarymanagementsystem.model.Book;
import com.librarymanagementsystem.model.Category;
import com.librarymanagementsystem.model.DatabaseService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookListController implements Initializable {

    @FXML
    private TableColumn<Author, String> colAuthor;

    @FXML
    private TableColumn<Category, String> colCategory;

    @FXML
    private TableColumn<Book, Integer> colCode;

    @FXML
    private TableColumn<Book, String> colCreatedBy;

    @FXML
    private TableColumn<Book, Boolean> colAvailable;

    @FXML
    private TableColumn<Book, String> colTitle;

    @FXML
    private TableView<Book> tblBookList;

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("Book.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("available"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        colCreatedBy.setCellValueFactory(new PropertyValueFactory<>("adminName"));

        List<Book> bookList = DatabaseService.listAllBook();

        tblBookList.setItems(FXCollections.observableArrayList(bookList));
    }
}
