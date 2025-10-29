package com.usiu.library.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FinesController {
    @FXML private TextField studentNameField;
    @FXML private TextField bookTitleField;
    @FXML private TextField dueDateField;
    @FXML private TextField fineRateField;
    @FXML private TextField daysOverdueField;
    @FXML private Label totalFineLabel;
    @FXML private Button calculateButton;
    @FXML private Button payFineButton;
    
    private static final double FINE_RATE_PER_DAY = 50.0; // KES 50 per day
    
    @FXML
    public void initialize() {
        // Set the default fine rate
        fineRateField.setText(String.format("%.2f", FINE_RATE_PER_DAY));
    }
    
    @FXML
    private void handleCalculateTotal() {
        try {
            int daysOverdue = Integer.parseInt(daysOverdueField.getText());
            double totalFine = daysOverdue * FINE_RATE_PER_DAY;
            totalFineLabel.setText(String.format("%.2f", totalFine));
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid number of overdue days.");
        }
    }
    
    @FXML
    private void handlePayFine() {
        if (totalFineLabel.getText().isEmpty()) {
            showAlert("Please calculate the total fine first.");
            return;
        }
        
        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("Payment Confirmation");
        confirmation.setHeaderText("Confirm Fine Payment");
        confirmation.setContentText("Do you want to proceed with the payment of KES " + totalFineLabel.getText() + "?");
        
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Here you would implement the actual payment processing
                showAlert("Payment processed successfully!", AlertType.INFORMATION);
                clearFields();
            }
        });
    }
    
    private void clearFields() {
        studentNameField.clear();
        bookTitleField.clear();
        dueDateField.clear();
        daysOverdueField.clear();
        totalFineLabel.setText("");
    }
    
    private void showAlert(String message) {
        showAlert(message, AlertType.ERROR);
    }
    
    private void showAlert(String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(type == AlertType.ERROR ? "Error" : "Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleBack() {
        try {
            Stage stage = (Stage) studentNameField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/usiu/library/views/student_dashboard.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Student Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error returning to dashboard: " + e.getMessage(), AlertType.ERROR);
        }
    }
}
