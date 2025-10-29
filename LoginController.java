package com.usiu.library;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class LoginController {

    // LOGIN TAB
    @FXML private TextField loginUsernameField;
    @FXML private PasswordField loginPasswordField;
    @FXML private ComboBox<String> loginRoleCombo;
    @FXML private Label loginErrorLabel;

    // SIGNUP TAB
    @FXML private TextField signupNameField;
    @FXML private TextField signupUsernameField;
    @FXML private PasswordField signupPasswordField;
    @FXML private PasswordField signupConfirmField;
    @FXML private ComboBox<String> signupRoleCombo;
    @FXML private Label signupMessageLabel;

    @FXML private TabPane authTabPane;

    @FXML
    private void handleLogin() throws IOException {
        String username = loginUsernameField.getText().trim();
        String password = loginPasswordField.getText().trim();
        String role = loginRoleCombo.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            loginErrorLabel.setText("Please fill in all fields.");
            loginErrorLabel.setVisible(true);
            return;
        }

        // Temporary login logic
        if (username.equals("student") && password.equals("1234") && role.equals("Student")) {
            loadDashboard("student_dashboard.fxml", "Welcome Student!");
        } else if (username.equals("admin") && password.equals("1234") && role.equals("Librarian")) {
            loadDashboard("librarian_dashboard.fxml", "Librarian Dashboard");
        } else {
            loginErrorLabel.setText("Invalid credentials. Try again.");
            loginErrorLabel.setVisible(true);
        }
    }

    @FXML
    private void handleSignup() {
        String name = signupNameField.getText().trim();
        String username = signupUsernameField.getText().trim();
        String password = signupPasswordField.getText().trim();
        String confirm = signupConfirmField.getText().trim();
        String role = signupRoleCombo.getValue();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || confirm.isEmpty() || role == null) {
            signupMessageLabel.setText("Please complete all fields.");
            signupMessageLabel.setStyle("-fx-text-fill: red;");
            signupMessageLabel.setVisible(true);
            return;
        }

        if (!password.equals(confirm)) {
            signupMessageLabel.setText("Passwords do not match.");
            signupMessageLabel.setStyle("-fx-text-fill: red;");
            signupMessageLabel.setVisible(true);
            return;
        }

        // Here you could save the user to a database later
        signupMessageLabel.setText("Account created successfully!");
        signupMessageLabel.setStyle("-fx-text-fill: green;");
        signupMessageLabel.setVisible(true);

        // Switch to Login tab automatically
        authTabPane.getSelectionModel().selectFirst();
    }

    private void loadDashboard(String fxml, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/usiu/library/views/" + fxml));
        Parent root = loader.load();
        Stage stage = (Stage) loginUsernameField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
    }
}
