package com.usiu.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LibraryApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
    java.net.URL fxmlUrl = getClass().getClassLoader().getResource("com/usiu/library/views/login.fxml");
    System.out.println("Loading FXML from: " + fxmlUrl);
    FXMLLoader loader = new FXMLLoader(fxmlUrl);
    Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("USIU Library System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
