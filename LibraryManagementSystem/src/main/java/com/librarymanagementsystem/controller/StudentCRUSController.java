package com.librarymanagementsystem.controller;

import com.librarymanagementsystem.LibraryApplication;
import com.librarymanagementsystem.model.Admin;
import com.librarymanagementsystem.model.DatabaseService;
import com.librarymanagementsystem.model.Student;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.naming.Name;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class StudentCRUSController implements Initializable {
    @FXML // fx:id="colAcademicYear"
    private TableColumn<Student, String> colAcademicYear; // Value injected by FXMLLoader

    @FXML // fx:id="colCardID"
    private TableColumn<Student, Integer> colCardID; // Value injected by FXMLLoader

    @FXML // fx:id="colCreatedDate"
    private TableColumn<Student, LocalDate> colCreatedDate; // Value injected by FXMLLoader

    @FXML // fx:id="colExpiredDate"
    private TableColumn<Student, LocalDate> colExpiredDate; // Value injected by FXMLLoader

    @FXML // fx:id="colName"
    private TableColumn<Student, String> colName; // Value injected by FXMLLoader

    @FXML // fx:id="colRollNumber"
    private TableColumn<Student, Integer> colRollNumber; // Value injected by FXMLLoader

    @FXML // fx:id="colYear"
    private TableColumn<Student, String> colYear; // Value injected by FXMLLoader

    @FXML // fx:id="tblStudent"
    private TableView<Student> tblStudent; // Value injected by FXMLLoader

    @FXML // fx:id="txtAcademicYear"
    private TextField txtAcademicYear; // Value injected by FXMLLoader

    @FXML // fx:id="txtName"
    private TextField txtName; // Value injected by FXMLLoader

    @FXML // fx:id="txtRollNumber"
    private TextField txtRollNumber; // Value injected by FXMLLoader

    @FXML // fx:id="txtYear"
    private TextField txtYear; // Value injected by FXMLLoader

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("Book.fxml");
    }

    @FXML
    void btnEdit(ActionEvent event) {
        try {

            var name = txtName.getText();
            var rollNumber = Integer.parseInt(txtRollNumber.getText());
            var year = txtYear.getText();
            var acadiemicYear = txtAcademicYear.getText();

            Student student = tblStudent.getSelectionModel().getSelectedItem();

            student.setName(name);
            student.setRollNumber(rollNumber);
            student.setYear(year);
            student.setAcademicYear(acadiemicYear);

            DatabaseService.editStudent(student);
            LibraryApplication.showAlert(Alert.AlertType.INFORMATION, "Data success");

            showData();

            txtName.setText("");
            txtRollNumber.setText("");
            txtYear.setText("");
            txtAcademicYear.setText("");

        } catch (Exception e) {
            LibraryApplication.showAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    void btnUpdateTime(ActionEvent event) {

        try {
            var name = txtName.getText();
            var rollNumber = Integer.parseInt(txtRollNumber.getText());
            var year = txtYear.getText();
            var acadiemicYear = txtAcademicYear.getText();

            Student student = tblStudent.getSelectionModel().getSelectedItem();

            student.setName(name);
            student.setRollNumber(rollNumber);
            student.setYear(year);
            student.setAcademicYear(acadiemicYear);
            student.setCreatedDate(LocalDate.now());
            student.setExpiredDate(LocalDate.now().plusYears(1));

            DatabaseService.updateTimeStudent(student);
            LibraryApplication.showAlert(Alert.AlertType.INFORMATION, "Data success");

            showData();

            txtName.setText("");
            txtRollNumber.setText("");
            txtYear.setText("");
            txtAcademicYear.setText("");

        } catch (Exception e) {
            LibraryApplication.showAlert(Alert.AlertType.ERROR, e.getMessage());
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colCardID.setCellValueFactory(new PropertyValueFactory<>("card_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRollNumber.setCellValueFactory(new PropertyValueFactory<>("rollNumber"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colAcademicYear.setCellValueFactory(new PropertyValueFactory<>("academicYear"));
        colCreatedDate.setCellValueFactory(new PropertyValueFactory<>("createdDate"));
        colExpiredDate.setCellValueFactory(new PropertyValueFactory<>("expiredDate"));

        showData();

        tblStudent.getSelectionModel().selectedItemProperty()
                .addListener(
                        (observable, oldSelected, newSelected) -> {
                            if (newSelected != null) {
                                Student selectedStudent = tblStudent.getSelectionModel().getSelectedItem();

                                txtName.setText(selectedStudent.getName());
                                txtRollNumber.setText(String.valueOf(selectedStudent.getRollNumber()));
                                txtYear.setText(selectedStudent.getYear());
                                txtAcademicYear.setText(selectedStudent.getAcademicYear());
                            }
                        }
                );

    }

    public void showData() {

            List<Student> studentList= DatabaseService.findAllStudent();
            tblStudent.setItems(FXCollections.observableArrayList(studentList));

    }
}
