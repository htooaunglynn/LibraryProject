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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class BookAddController implements Initializable {

    @FXML
    private ComboBox<String> cmbAuthor;

    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtTitle;

    private List<Category> categoryList;
    private List<Author> authorList;

    @FXML
    void btnAdd(ActionEvent event) {
        try {
            var code = txtCode.getText();
            var authorIndex = cmbAuthor.getSelectionModel().getSelectedIndex();
            var categoryIndex = cmbCategory.getSelectionModel().getSelectedIndex();

            if (code.equals("")) {
                LibraryApplication.showAlert(Alert.AlertType.ERROR, "Code number is required");
                return;
            }

            if (authorIndex == -1) {
                LibraryApplication.showAlert(Alert.AlertType.ERROR, "Author is required");
                return;
            }

            if (categoryIndex == -1) {
                LibraryApplication.showAlert(Alert.AlertType.ERROR, "Category is required");
                return;
            }

            var book = new Book();
            book.setTitle(txtTitle.getText());
            book.setCode(Integer.parseInt(code));
            book.setPublicDate(LocalDate.now());
            book.setAuthor(authorList.get(authorIndex));
            book.setCategory(categoryList.get(categoryIndex));

            DatabaseService.addBook(book);
            LibraryApplication.showAlert(Alert.AlertType.INFORMATION, "Success");

            txtCode.setText("");
            txtTitle.setText("");
            cmbCategory.getSelectionModel().clearSelection();
            cmbAuthor.getSelectionModel().clearSelection();
        } catch (Exception e) {
            LibraryApplication.showAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("Book.fxml");
    }

    @FXML
    void btnReset(ActionEvent event) {
        txtCode.setText("");
        txtTitle.setText("");
        cmbCategory.getSelectionModel().clearSelection();
        cmbAuthor.getSelectionModel().clearSelection();
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

        cmbAuthor.setItems(FXCollections.observableArrayList(authorName));
        cmbCategory.setItems(FXCollections.observableArrayList(categoryName));
    }
}
