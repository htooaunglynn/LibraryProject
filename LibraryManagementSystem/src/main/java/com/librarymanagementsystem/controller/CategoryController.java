package com.librarymanagementsystem.controller;

import com.librarymanagementsystem.LibraryApplication;
import com.librarymanagementsystem.model.Category;
import com.librarymanagementsystem.model.DatabaseService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {

    @FXML
    private TableColumn<Category, LocalDate> colCreated;

    @FXML
    private TableColumn<Category, Integer> colId;

    @FXML
    private TableColumn<Category, String> colName;

    @FXML
    private TableColumn<Category, LocalDate> colUpdated;

    @FXML
    private TableView<Category> tblCategory;

    @FXML
    private TextField txtCategoryName;

    @FXML
    void btnAdd(ActionEvent event) {
        try {
            var name = txtCategoryName.getText();

            if (name.equals("")) {
                LibraryApplication.showAlert(Alert.AlertType.ERROR, "Category name is required");
                return;
            }

            DatabaseService.saveCategory(name);
            LibraryApplication.showAlert(Alert.AlertType.INFORMATION, "Data Success");
            txtCategoryName.setText("");

            showData();

        } catch (Exception e) {
            LibraryApplication.showAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("Book.fxml");
    }

    @FXML
    void btnEdit(ActionEvent event) {

        try {

            var name = txtCategoryName.getText();

            var category = tblCategory.getSelectionModel().getSelectedItem();
            category.setName(name);

            DatabaseService.updateCategory(category);
            LibraryApplication.showAlert(Alert.AlertType.INFORMATION, "Data success");
            showData();
            txtCategoryName.setText("");

        } catch (Exception e) {
            LibraryApplication.showAlert(Alert.AlertType.ERROR, e.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCreated.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        colUpdated.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        showData();

        tblCategory.getSelectionModel().selectedItemProperty()
                .addListener(
                        (observable, oldSelected, newSelected) -> {
                            if (newSelected != null) {
                                Category selectCategory = tblCategory.getSelectionModel().getSelectedItem();

                                txtCategoryName.setText(selectCategory.getName());
                            }
                        }
                );
    }

    public void showData() {
        List<Category> categoryList = DatabaseService.findAllCategory();
        tblCategory.setItems(FXCollections.observableArrayList(categoryList));
    }
}
