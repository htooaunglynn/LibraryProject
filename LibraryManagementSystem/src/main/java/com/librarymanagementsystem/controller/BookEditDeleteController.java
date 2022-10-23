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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookEditDeleteController implements Initializable {

    @FXML
    private ComboBox<String> cmbAuthors;

    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private TextField txtCodeToSearch;

    @FXML
    private TextField txtTitle;

    private List<Category> categoryList;
    private List<Author> authorList;
    private Book book;

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("Book.fxml");
    }

    @FXML
    void btnDelete(ActionEvent event) {
        try {
            DatabaseService.deleteBook(book.getCode());
            LibraryApplication.showAlert(Alert.AlertType.INFORMATION, "Success");
        } catch (Exception e) {
            LibraryApplication.showAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    void btnEdit(ActionEvent event) {

        try {

            var title = txtTitle.getText();
            var authorIndex = cmbAuthors.getSelectionModel().getSelectedIndex();
            var categoryIndex = cmbCategory.getSelectionModel().getSelectedIndex();

            book.setTitle(title);
            book.setAuthor(authorList.get(authorIndex));
            book.setCategory(categoryList.get(categoryIndex));

            DatabaseService.editBook(book);
            LibraryApplication.showAlert(Alert.AlertType.INFORMATION, "Success");

        } catch (Exception e) {
            LibraryApplication.showAlert(Alert.AlertType.ERROR, e.getMessage());
        }

    }

    @FXML
    void btnSearch(ActionEvent event) {
        var code = txtCodeToSearch.getText();

        if (code.equals("")) {
            LibraryApplication.showAlert(Alert.AlertType.ERROR, "Code number is required");
            return;
        }

        book = DatabaseService.findByBookId(Integer.parseInt(code));

        if (book == null) {
            LibraryApplication.showAlert(Alert.AlertType.WARNING, "This book is does not exist.");
        } else {
            txtTitle.setText(book.getTitle());
            cmbAuthors.getSelectionModel().select(book.getAuthorName());
            cmbCategory.getSelectionModel().select(book.getCategoryName());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryList = DatabaseService.findAllCategory();
        authorList = DatabaseService.findAllAuthor();

        List<String> authorName = authorList.stream()
                .map(obj -> obj.getName())
                .toList();

        List<String> categoryName = categoryList.stream()
                .map(obj -> obj.getName())
                .toList();

        cmbAuthors.setItems(FXCollections.observableArrayList(authorName));
        cmbCategory.setItems(FXCollections.observableArrayList(categoryName));
    }
}
