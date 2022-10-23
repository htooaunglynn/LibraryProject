package com.librarymanagementsystem.controller;

import com.librarymanagementsystem.LibraryApplication;
import com.librarymanagementsystem.model.Admin;
import com.librarymanagementsystem.model.Author;
import com.librarymanagementsystem.model.Category;
import com.librarymanagementsystem.model.DatabaseService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AuthorController implements Initializable {
    @FXML
    private TableColumn<Author, LocalDate> colBirthday;

    @FXML
    private TableColumn<Author, String> colCity;

    @FXML
    private TableColumn<Author, Integer> colCreated;

    @FXML
    private TableColumn<Author, Integer> colID;

    @FXML
    private TableColumn<Author, String> colName;

    @FXML
    private DatePicker dateBirthday;

    @FXML
    private TableView<Author> tblAuthor;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtName;

    @FXML
    void btnAdd(ActionEvent event) {

        try {
            var name = txtName.getText();
            var city = txtCity.getText();
            var birthday = dateBirthday.getValue();

            if (name.equals("")) {
                LibraryApplication.showAlert(Alert.AlertType.ERROR, "Name is required");
                return;
            }

            if (city.equals("")) {
                LibraryApplication.showAlert(Alert.AlertType.ERROR, "City is required");
                return;
            }

            DatabaseService.addAuthor(name, city, birthday);
            LibraryApplication.showAlert(Alert.AlertType.INFORMATION, "Data Success");
            txtName.setText("");
            txtCity.setText("");
            dateBirthday.setValue(null);

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

            var name = txtName.getText();
            var city = txtCity.getText();
            var birthday = dateBirthday.getValue();

            var author = tblAuthor.getSelectionModel().getSelectedItem();
            author.setName(name);
            author.setCity(city);
            author.setBirthday(birthday);

            DatabaseService.editAuthor(author);
            LibraryApplication.showAlert(Alert.AlertType.INFORMATION, "Data success");

            showData();

            txtName.setText("");
            txtCity.setText("");
            dateBirthday.setValue(null);

        } catch (Exception e) {
            LibraryApplication.showAlert(Alert.AlertType.ERROR, e.getMessage());
        }

    }

    public void showData() {
        List<Author> authorList = DatabaseService.findAllAuthor();
        tblAuthor.setItems(FXCollections.observableArrayList(authorList));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colCreated.setCellValueFactory(new PropertyValueFactory<>("adminId"));

        showData();

        tblAuthor.getSelectionModel().selectedItemProperty()
                .addListener(
                        (observable, oldSelected, newSelected) -> {
                            if (newSelected != null) {
                                Author selectAuthor = tblAuthor.getSelectionModel().getSelectedItem();

                                txtName.setText(selectAuthor.getName());
                                txtCity.setText(selectAuthor.getCity());
                                dateBirthday.setValue(selectAuthor.getBirthday());
                            }
                        }
                );
    }
}
