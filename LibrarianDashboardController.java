package com.usiu.library.controllers;

import java.io.IOException;

import com.usiu.library.models.Book;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class LibrarianDashboardController {

    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book, String> colBookId;
    @FXML private TableColumn<Book, String> colTitle;
    @FXML private TableColumn<Book, String> colAuthor;
    @FXML private TableColumn<Book, String> colStatus;

    private ObservableList<Book> bookData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Example data for display
        bookData.addAll(
            new Book("1", "Clean Code", "Robert C. Martin", "Available"),
            new Book("2", "Effective Java", "Joshua Bloch", "Checked Out"),
            new Book("3", "Introduction to Algorithms", "Cormen et al.", "Available")
        );

        colBookId.setCellValueFactory(cell -> cell.getValue().bookIdProperty());
        colTitle.setCellValueFactory(cell -> cell.getValue().titleProperty());
        colAuthor.setCellValueFactory(cell -> cell.getValue().authorProperty());
        colStatus.setCellValueFactory(cell -> cell.getValue().statusProperty());

        bookTable.setItems(bookData);
    }

    @FXML
private void handleAddBook() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/usiu/library/views/add_book.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Book");
        stage.setScene(new Scene(loader.load()));
        stage.showAndWait();
    } catch (IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Error loading add book form: " + e.getMessage());
        alert.showAndWait();
    }
}


    @FXML
    private void handleUpdateBook() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Update Book feature coming soon!");
        alert.showAndWait();
    }

    @FXML
    private void handleRemoveBook() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Remove Book feature coming soon!");
        alert.showAndWait();
    }

    @FXML
    private void handleLogout() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/usiu/library/views/login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) bookTable.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("USIU Library System");
    }
}
