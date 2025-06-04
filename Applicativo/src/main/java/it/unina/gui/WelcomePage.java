package it.unina.gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class WelcomePage extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WelcomePage.class.getResource("/it/unina/WelcomeCard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1540, 790);

        scene.getStylesheets().add(Objects.requireNonNull(WelcomePage.class.getResource("/it/unina/css/WelcomePage.css")).toExternalForm());
        stage.setTitle("");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
