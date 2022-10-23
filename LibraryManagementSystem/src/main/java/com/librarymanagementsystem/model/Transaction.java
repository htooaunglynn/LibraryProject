package com.librarymanagementsystem.model;

import java.time.LocalDate;

public class Transaction {
    private int id, fees;
    private Admin admin = new Admin();
    private Student student = new Student();
    private LocalDate borrowDate, dueDate;
    private Book book = new Book();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getStudentCard() {
        return student.getCard_id();
    }

    public Integer getBookCode() {
        return book.getCode();
    }

    public Integer getAdminId() {
        return admin.getId();
    }
}
