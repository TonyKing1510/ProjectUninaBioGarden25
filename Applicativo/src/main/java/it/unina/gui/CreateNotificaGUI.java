package it.unina.gui;

import it.unina.controller.CreateActivityController;
import it.unina.controller.CreateNotificaController;
import it.unina.model.Utente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CreateNotificaGUI {
    CreateNotificaGUI(){}

    public static void openCreateNotificaView(Utente utente) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateActivityGUI.class.getResource("/it/unina/CreateNotificaPage.fxml"));
        Parent root = fxmlLoader.load();

        // Configura il controller
        CreateNotificaController controller = fxmlLoader.getController();
        controller.setProprietarioCorrente(utente);
        controller.caricaProgetti();

        // Imposta la scena e mostra la finestra
        Stage stage = new Stage();
        stage.setTitle("Crea Nuova Notifica");
        Scene scene = new Scene(root);

        // Aggancio del CSS
        scene.getStylesheets().add(
                Objects.requireNonNull(CreateActivityGUI.class.getResource("/it/unina/css/style.css")).toExternalForm()
        );

        stage.setScene(scene);
        stage.show();

    }
}
