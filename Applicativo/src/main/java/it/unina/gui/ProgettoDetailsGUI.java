package it.unina.gui;

import it.unina.model.Progetto;
import it.unina.controller.ProgettiDetailsController;
import it.unina.model.Utente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProgettoDetailsGUI {
    ProgettoDetailsGUI() {

    }

    public static void openPageProgettoDetailsGUI(Progetto progetto, Utente utente) throws IOException {
        FXMLLoader loader = new FXMLLoader(ProgettoDetailsGUI.class.getResource("/it/unina/ProgettiDetails.fxml"));
        Parent root = loader.load();
        ProgettiDetailsController controller = loader.getController();
        controller.setProgetto(progetto);
        controller.setUtente(utente);
        controller.setProgettoDetails(progetto);
        controller.setLottiDetails();
        // Imposta la scena e mostra la finestra
        Stage stage = new Stage();
        stage.setTitle("Dettagli Progetto");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
