package com.librarymanagementsystem.controller;

import com.librarymanagementsystem.LibraryApplication;
import com.librarymanagementsystem.model.Admin;
import com.librarymanagementsystem.model.Author;
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
import java.util.List;
import java.util.ResourceBundle;

public class AdminCRUDController implements Initializable {

    @FXML
    private TableColumn<Admin, String> colEmail;

    @FXML
    private TableColumn<Admin, Integer> colId;

    @FXML
    private TableColumn<Admin, String> colNrc;

    @FXML
    private TableColumn<Admin, String> colPassword;

    @FXML
    private TableColumn<Admin, String> colPhone;

    @FXML
    private TableView<Admin> tblAdmin;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNrc;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhone;


    @FXML
    void btnBack(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("Book.fxml");
    }

    @FXML
    void btnAdd(ActionEvent event) {

        try {
            var email = txtEmail.getText();
            var password = txtPassword.getText();
            var nrc = txtNrc.getText();
            var phone = txtPhone.getText();

            if (email.equals("")) {
                LibraryApplication.showAlert(Alert.AlertType.ERROR, "Email is required");
                return;
            }

            if (password.equals("")) {
                LibraryApplication.showAlert(Alert.AlertType.ERROR, "Password is required");
                return;
            }

            if (nrc.equals("")) {
                LibraryApplication.showAlert(Alert.AlertType.ERROR, "NRC is required");
                return;
            }

            if (phone.equals("")) {
                LibraryApplication.showAlert(Alert.AlertType.ERROR, "Phone is required");
                return;
            }

            Admin admin = new Admin();

            admin.setEmail(email);
            admin.setPassword(password);
            admin.setNrc(nrc);
            admin.setPhone(phone);

            DatabaseService.addAdmin(admin);
            LibraryApplication.showAlert(Alert.AlertType.INFORMATION, "Data Success");

            txtEmail.setText("");
            txtPassword.setText("");
            txtNrc.setText("");
            txtPhone.setText("");

            showData();

        } catch (Exception e) {
            LibraryApplication.showAlert(Alert.AlertType.ERROR, e.getMessage());
        }

    }

    @FXML
    void btnUpdate(ActionEvent event) {

        try {

            var email = txtEmail.getText();
            var password = txtPassword.getText();
            var nrc = txtNrc.getText();
            var phone = txtPhone.getText();

            Admin admin = tblAdmin.getSelectionModel().getSelectedItem();

            admin.setEmail(email);
            admin.setPassword(password);
            admin.setNrc(nrc);
            admin.setPhone(phone);

            DatabaseService.updateAdmin(admin);
            LibraryApplication.showAlert(Alert.AlertType.INFORMATION, "Data success");

            showData();

            txtEmail.setText("");
            txtPassword.setText("");
            txtNrc.setText("");
            txtPhone.setText("");

        } catch (Exception e) {
            LibraryApplication.showAlert(Alert.AlertType.ERROR, e.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colNrc.setCellValueFactory(new PropertyValueFactory<>("nrc"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        showData();

        tblAdmin.getSelectionModel().selectedItemProperty()
                .addListener(
                        (observable, oldSelected, newSelected) -> {
                            if (newSelected != null) {
                                Admin selectAdmin = tblAdmin.getSelectionModel().getSelectedItem();

                                txtEmail.setText(selectAdmin.getEmail());
                                txtPassword.setText(selectAdmin.getPassword());
                                txtNrc.setText(selectAdmin.getNrc());
                                txtPhone.setText(selectAdmin.getPhone());
                            }
                        }
                );

    }

    public void showData() {
        List<Admin> adminList = DatabaseService.findAllAdmin();
        tblAdmin.setItems(FXCollections.observableArrayList(adminList));
    }
}
