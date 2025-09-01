package it.unina.gui;

import it.unina.controller.ProjectViewController;
import it.unina.controller.components.ProjectCardController;
import it.unina.controller.components.ProjectCardDetailsController;
import it.unina.model.Progetto;
import it.unina.model.Utente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * ProjectGUI è la classe che gestisce la visualizzazione della schermata dei progetti.
 * * Questa classe si occupa di caricare il file FXML corrispondente e impostare gli stili CSS necessari.
 * @author entn
 */
public class VisualizeProjectGUI {
    VisualizeProjectGUI() {
        // Constructor logic can be added here if needed
    }

    /**
     * Inizializza la visualizzazione della schermata dei progetti.
     * Carica il file FXML e applica gli stili CSS necessari.
     *
     * @param borderPane Il BorderPane in cui caricare la vista dei progetti.
     * @throws IOException Se si verifica un errore durante il caricamento del file FXML.
     * @author entn
     */
    public static void initializeProjectView(BorderPane borderPane, Utente utente) throws IOException {
        FXMLLoader loader = new FXMLLoader(VisualizeProjectGUI.class.getResource("/it/unina/ProjectView.fxml"));
        Parent node = loader.load();
        ProjectViewController controller = loader.getController();
        controller.setStagioniMenu();
        controller.setUtenteLoggato(utente);
        node.getStylesheets().add(VisualizeProjectGUI.class.getResource("/it/unina/css/coltureview.css").toExternalForm());
        borderPane.setCenter(node);


    }

    public static void initProjectCard(Progetto progetto, VBox contentBox, Utente utente) throws IOException {
        FXMLLoader loader = new FXMLLoader(VisualizeProjectGUI.class.getResource("/it/unina/components/ProjectCard.fxml"));
        AnchorPane card = loader.load();

        // Ottieni il controller della card e setta i dati
        ProjectCardController controller = loader.getController();
        controller.setProgetto(progetto);
        controller.setUtente(utente);
        controller.setProgettoDetails(progetto);

        contentBox.getChildren().add(card);
    }

    public static void openProjectDetailsView(Progetto progetto, Utente utente) throws IOException {
        FXMLLoader loader = new FXMLLoader(VisualizeProjectGUI.class.getResource("/it/unina/components/ProjectDetails.fxml"));
        Parent node = loader.load();


        node.getStylesheets().add(VisualizeProjectGUI.class.getResource("/it/unina/css/coltureview.css").toExternalForm());


        ProjectCardDetailsController controller = loader.getController();
        controller.setUtente(utente);
        controller.setProgetto(progetto);
        controller.setProgettoDetails(progetto);


        Stage stage = new Stage();
        stage.setTitle("Dettagli Progetto");
        stage.setScene(new Scene(node));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }
}
