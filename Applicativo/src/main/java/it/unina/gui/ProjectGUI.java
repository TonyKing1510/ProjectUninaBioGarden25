package it.unina.gui;

import it.unina.controller.ProjectViewController;
import it.unina.model.Utente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

/**
 * ProjectGUI è la classe che gestisce la visualizzazione della schermata dei progetti.
 * * Questa classe si occupa di caricare il file FXML corrispondente e impostare gli stili CSS necessari.
 * @author entn
 */
public class ProjectGUI {
    ProjectGUI() {
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
        FXMLLoader loader = new FXMLLoader(ProjectGUI.class.getResource("/it/unina/ProjectView.fxml"));
        Parent node = loader.load();
        ProjectViewController controller = loader.getController();
        controller.setUtenteLoggato(utente);
        node.getStylesheets().add(ProjectGUI.class.getResource("/it/unina/css/coltureview.css").toExternalForm());
        borderPane.setCenter(node);


    }

    public static void openAddProjectView(Window window, Utente utente) throws IOException {
        FXMLLoader loader = new FXMLLoader(ProjectGUI.class.getResource("/it/unina/AddProjectView.fxml"));
        Parent node = loader.load();
        node.getStylesheets().add(ProjectGUI.class.getResource
                ("/it/unina/css/coltureview.css").toExternalForm());
        ProjectViewController controller = loader.getController();
        controller.setUtenteLoggato(utente);
        Stage stage = new Stage();
        stage.setTitle("Aggiungi Progetto");
        stage.setScene(new Scene(node, 800, 600));
        stage.setResizable(false);
        stage.show();

    }
}
