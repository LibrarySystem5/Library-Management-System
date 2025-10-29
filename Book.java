package com.usiu.library.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {
    private final StringProperty bookId;
    private final StringProperty title;
    private final StringProperty author;
    private final StringProperty status;
    private final StringProperty returnDate;

    public Book(String id, String title, String author, String status) {
        this.bookId = new SimpleStringProperty(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.status = new SimpleStringProperty(status);
        this.returnDate = new SimpleStringProperty("");
    }

    public StringProperty bookIdProperty() { return bookId; }
    public StringProperty titleProperty() { return title; }
    public StringProperty authorProperty() { return author; }
    public StringProperty statusProperty() { return status; }
    public StringProperty returnDateProperty() { return returnDate; }

    public String getTitle() { return title.get(); }
    public String getStatus() { return status.get(); }
    public void setStatus(String status) { this.status.set(status); }
    public void setReturnDate(String date) { this.returnDate.set(date); }
}
