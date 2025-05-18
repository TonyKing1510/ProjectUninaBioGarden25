package it.unina;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomePage extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WelcomePage.class.getResource("/it/unina/WelcomeCard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);

        scene.getStylesheets().add(WelcomePage.class.getResource("/it/unina/css/WelcomePage.css").toExternalForm());

        stage.setTitle("");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
