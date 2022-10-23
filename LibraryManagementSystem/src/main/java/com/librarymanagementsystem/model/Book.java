package com.librarymanagementsystem.model;

import java.time.LocalDate;

public class Book {

    private int code;
    private LocalDate publicDate;
    private boolean available;
    private String title;
    private Author author = new Author();
    private Category category = new Category();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public LocalDate getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(LocalDate publicDate) {
        this.publicDate = publicDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    private Admin admin = new Admin();

    public String getAuthorName() {
        return author.getName();
    }

    public String getCategoryName() {
        return category.getName();
    }

    public String getAdminName() {
        return admin.getEmail();
    }
}
