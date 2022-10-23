package com.librarymanagementsystem.controller;

import com.librarymanagementsystem.LibraryApplication;
import com.librarymanagementsystem.model.DatabaseService;
import com.librarymanagementsystem.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class StudentRegisterController {

    @FXML
    private TextField txtAcademicYear;

    @FXML
    private TextField txtYear;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtRollNo;

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("Library.fxml");
    }

    @FXML
    void btnRegister(ActionEvent event) {

        try {
            var name = txtName.getText();
            var rollNo = txtRollNo.getText();
            var year = txtYear.getText();
            var academicYear = txtAcademicYear.getText();

            if (name.equals("")) {
                LibraryApplication.showAlert(Alert.AlertType.ERROR, "Name is required");
                return;
            }

            if (rollNo == "") {
                LibraryApplication.showAlert(Alert.AlertType.ERROR, "Roll Number is required");
                return;
            }

            Student student = new Student();

            student.setName(name);
            student.setRollNumber(Integer.parseInt(rollNo));
            student.setYear(year);
            student.setAcademicYear(academicYear);

            DatabaseService.studentRegister(student);
            LibraryApplication.showAlert(Alert.AlertType.INFORMATION, "Data Success");


        } catch (Exception e) {
            LibraryApplication.showAlert(Alert.AlertType.ERROR, e.getMessage());
        }

    }
}
