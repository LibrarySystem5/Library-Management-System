package com.usiu.library.controllers;

import java.time.LocalDate;

import com.usiu.library.models.Book;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StudentDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private TextField searchField;

    @FXML private TableView<Book> availableBooksTable;
    @FXML private TableView<Book> borrowedBooksTable;

    @FXML private TableColumn<Book, String> bookIdColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> statusColumn;

    @FXML private TableColumn<Book, String> borrowedBookIdColumn;
    @FXML private TableColumn<Book, String> borrowedTitleColumn;
    @FXML private TableColumn<Book, String> borrowedAuthorColumn;
    @FXML private TableColumn<Book, String> returnDateColumn;

    private ObservableList<Book> availableBooks = FXCollections.observableArrayList();
    private ObservableList<Book> borrowedBooks = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Setup columns
        bookIdColumn.setCellValueFactory(data -> data.getValue().bookIdProperty());
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        authorColumn.setCellValueFactory(data -> data.getValue().authorProperty());
        statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());

        borrowedBookIdColumn.setCellValueFactory(data -> data.getValue().bookIdProperty());
        borrowedTitleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        borrowedAuthorColumn.setCellValueFactory(data -> data.getValue().authorProperty());
        returnDateColumn.setCellValueFactory(data -> data.getValue().returnDateProperty());

        // Dummy book data
        availableBooks.addAll(
                new Book("B001", "Java Programming", "John Doe", "Available"),
                new Book("B002", "Data Structures", "Alice Smith", "Available"),
                new Book("B003", "Networking Basics", "Bob White", "Borrowed")
        );
        availableBooksTable.setItems(availableBooks);
    }

    // Search handler
    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase();
        if (keyword.isEmpty()) {
            availableBooksTable.setItems(availableBooks);
        } else {
            ObservableList<Book> filtered = availableBooks.filtered(
                    b -> b.getTitle().toLowerCase().contains(keyword)
            );
            availableBooksTable.setItems(filtered);
        }
    }

    // Borrow handler
    @FXML
    private void handleBorrowBook() {
        Book selected = availableBooksTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a book to borrow.");
            return;
        }

        if (selected.getStatus().equals("Borrowed")) {
            showAlert("That book is already borrowed.");
            return;
        }

        selected.setStatus("Borrowed");
        selected.setReturnDate(LocalDate.now().plusDays(7).toString());
        borrowedBooks.add(selected);
        borrowedBooksTable.setItems(borrowedBooks);
        availableBooksTable.refresh();

        showAlert("You borrowed: " + selected.getTitle());
    }

    // Return handler
    @FXML
    private void handleReturnBook() {
        Book selected = borrowedBooksTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a book to return.");
            return;
        }

        selected.setStatus("Available");
        selected.setReturnDate("");
        borrowedBooks.remove(selected);
        availableBooksTable.refresh();

        showAlert("Book returned successfully.");
    }

    // Logout handler
    @FXML
    private void handleLogout() {
        try {
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/usiu/library/views/login.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("USIU Library Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setStudentName(String name) {
        welcomeLabel.setText("Welcome, " + name + " 👋");
    }

    @FXML
    private void handleShowFines() {
        try {
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            String fxmlPath = "/com/usiu/library/views/fines.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            if (loader.getLocation() == null) {
                throw new Exception("Could not find FXML file at: " + fxmlPath);
            }
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("My Fines");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading fines view: " + e.getMessage());
        }
    }
}
