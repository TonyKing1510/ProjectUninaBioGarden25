package it.unina.gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * WelcomePage è la classe principale dell'applicazione che gestisce la visualizzazione della pagina di benvenuto.
 * @author entn
 * @author Sderr12
 */
public class WelcomePage extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(WelcomePage.class.getResource("/it/unina/WelcomeCard.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1540, 790);

            scene.getStylesheets().add(Objects.requireNonNull(WelcomePage.class.getResource("/it/unina/css/WelcomePage.css")).toExternalForm());
            stage.setTitle("");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException | NullPointerException e) {

            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Errore di avvio");
            alert.setHeaderText("Impossibile caricare le risorse");
            alert.setContentText("Verifica la presenza dei file FXML/CSS richiesti.");
            alert.showAndWait();

            e.printStackTrace();
            javafx.application.Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
