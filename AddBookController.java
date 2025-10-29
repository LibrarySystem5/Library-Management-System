package com.usiu.library.controllers;

import com.usiu.library.models.Book;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddBookController {

    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField yearField;
    @FXML private CheckBox availableCheck;
    @FXML private Label messageLabel;

    @FXML
    private void handleSaveBook() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String yearText = yearField.getText().trim();

        if (title.isEmpty() || author.isEmpty() || yearText.isEmpty()) {
            messageLabel.setText("All fields are required!");
            messageLabel.setTextFill(javafx.scene.paint.Color.RED);
            messageLabel.setVisible(true);
            return;
        }

        try {
            int year = Integer.parseInt(yearText);
            boolean available = availableCheck.isSelected();

            String status = available ? "Available" : "Checked Out";
            Book newBook = new Book("0", title, author, status);
            System.out.println("Book Added: " + newBook.getTitle() + " (" + year + ")");

            messageLabel.setText("Book added successfully!");
            messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            messageLabel.setVisible(true);

            // Optionally, close window after saving
            Stage stage = (Stage) titleField.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            messageLabel.setText("Year must be a number!");
            messageLabel.setTextFill(javafx.scene.paint.Color.RED);
            messageLabel.setVisible(true);
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }
}
