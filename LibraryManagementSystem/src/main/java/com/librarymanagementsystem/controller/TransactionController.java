package com.librarymanagementsystem.controller;

import com.librarymanagementsystem.LibraryApplication;
import com.librarymanagementsystem.model.*;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;


public class TransactionController implements Initializable {
    @FXML
    private TableColumn<Book, Boolean> colAvailable;

    @FXML
    private TableColumn<Book, Integer> colBookID;

    @FXML
    private TableColumn<Book, String> colBookTitle;

    @FXML
    private TableColumn<Admin, Integer> colTAdminId;

    @FXML
    private TableColumn<Transaction, LocalDate> colTBorrowDate;

    @FXML
    private TableColumn<Transaction, LocalDate> colTDueDate;

    @FXML
    private TableColumn<Transaction, Integer> colTFees;

    @FXML
    private TableColumn<Book, Integer> colTbookId;

    @FXML
    private TableColumn<Student, String> colTcardId;

    @FXML
    private TableColumn<Transaction, Integer> colTid;

    @FXML
    private TableView<Book> tblBook;

    @FXML
    private TableView<Transaction> tblTransaction;

    @FXML
    private TextField txtBookId;

    @FXML
    private TextField txtCardId;

    @FXML
    private TextField txtBookAvailable;

    @FXML
    private TextField txtUdeDate;

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        LibraryApplication.changleScence("Book.fxml");
    }

    @FXML
    void btnBorrow(ActionEvent event) {

        try {
            var bookId = txtBookId.getText();
            var cardId = txtCardId.getText();

            var available = Boolean.valueOf(txtBookAvailable.getText());

            if  (bookId.equals("")) {
                LibraryApplication.showAlert(Alert.AlertType.ERROR, "Code number is required");
                return;
            }

            if  (cardId.equals("")) {
                LibraryApplication.showAlert(Alert.AlertType.ERROR, "Code number is required");
                return;
            }

            if (available == false) {
                LibraryApplication.showAlert(Alert.AlertType.ERROR, "Book does not have");
                return;
            }

            DatabaseService.borrowBook(bookId, cardId);
            LibraryApplication.showAlert(Alert.AlertType.INFORMATION, "Success");

            showTransactionData();

            Book book = new Book();
            book.setCode(Integer.parseInt(bookId));
            book.setAvailable(false);

            DatabaseService.availableBookBorrow(book);
            showBookData();

        } catch (Exception e) {
            LibraryApplication.showAlert(Alert.AlertType.ERROR, e.getMessage());
        }

    }

    @FXML
    void btnReturn(ActionEvent event) {

        try {
            var bookId = txtBookId.getText();
            var cardId = txtCardId.getText();
            var dueDate = LocalDate.parse(txtUdeDate.getText());

            LocalDate dateNow = LocalDate.now();


            if  (bookId.equals("")) {
                LibraryApplication.showAlert(Alert.AlertType.ERROR, "Code number is required");
                return;
            }

            if  (cardId.equals("")) {
                LibraryApplication.showAlert(Alert.AlertType.ERROR, "Code number is required");
                return;
            }

            var result  = dueDate.compareTo(dateNow);
            System.out.println(result);

            if (result < 8 && result > 0) {
                DatabaseService.returnBook(bookId);
                LibraryApplication.showAlert(Alert.AlertType.INFORMATION, "Success");
            } else {
                DatabaseService.returnBook(bookId);
                LibraryApplication.showAlert(Alert.AlertType.WARNING, "Due date is over fees 2000 ks");
            }

            showTransactionData();

            Book book = new Book();
            book.setCode(Integer.parseInt(bookId));
            book.setAvailable(true);

            DatabaseService.availableBookBorrow(book);
            showBookData();


        } catch (Exception e) {
            LibraryApplication.showAlert(Alert.AlertType.ERROR, e.getMessage());
        }

    }

    public void showBookData() {
        List<Book> bookList= DatabaseService.listAllBook();
        tblBook.setItems(FXCollections.observableArrayList(bookList));
    }

    public void showTransactionData() {
        List<Transaction> transactionList = DatabaseService.findAllTransaction();
        tblTransaction.setItems(FXCollections.observableArrayList(transactionList));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colBookID.setCellValueFactory(new PropertyValueFactory<>("code"));
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("available"));

        tblBook.getSelectionModel().selectedItemProperty()
                .addListener(
                        (observable, oldSelected, newSelected) -> {
                            if (newSelected != null) {
                                Book selectedItem = tblBook.getSelectionModel().getSelectedItem();

                                txtBookId.setText(String.valueOf(selectedItem.getCode()));
                                txtBookAvailable.setText(String.valueOf(selectedItem.getAvailable()));
                            }
                        }
                );

        showBookData();

        colTid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTcardId.setCellValueFactory(new PropertyValueFactory<>("studentCard"));
        colTbookId.setCellValueFactory(new PropertyValueFactory<>("bookCode"));
        colTBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colTDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colTFees.setCellValueFactory(new PropertyValueFactory<>("fees"));
        colTAdminId.setCellValueFactory(new PropertyValueFactory<>("adminId"));

        tblTransaction.getSelectionModel().selectedItemProperty()
                .addListener(
                        (observable, oldSelected, newSelected) -> {
                            if (newSelected != null) {
                                Transaction selectedItem = tblTransaction.getSelectionModel().getSelectedItem();

                                txtBookId.setText(String.valueOf(selectedItem.getBookCode()));
                                txtCardId.setText(String.valueOf(selectedItem.getStudentCard()));
                                txtUdeDate.setText(String.valueOf(selectedItem.getDueDate()));
                                txtBookAvailable.setText(String.valueOf(selectedItem.getBook().getAvailable()));
                            }
                        }
                );

        showTransactionData();

    }
}
