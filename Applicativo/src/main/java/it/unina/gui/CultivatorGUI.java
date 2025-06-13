package it.unina.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;


/**
 * Classe per la gestione della GUI del coltivatore.
 * Questa classe è responsabile dell'inizializzazione e della visualizzazione della vista del coltivatore.
 *
 * @author entn
 */
public class CultivatorGUI {
    CultivatorGUI() {
        // Initialize the GUI components here
        // This constructor can be used to set up the initial state of the GUI
    }
    /**
     * Inizializza la vista del coltivatore.
     * Questo metodo carica il file FXML per la vista del coltivatore e lo imposta nel BorderPane fornito.
     *
     * @param borderPane il BorderPane in cui caricare la vista del coltivatore.
     * @throws IOException se c'è un errore nel caricamento del file FXML o delle risorse.
     * @author entn
     */
    public static void initializeCultivatorView(BorderPane borderPane) throws IOException {
        // Method to initialize the view for the cultivator
        // This can include loading FXML files, setting up event handlers, etc.
            FXMLLoader loader = new FXMLLoader(CultivatorGUI.class.getResource("/it/unina/Cultivator.fxml"));
            Parent node = loader.load();
            node.getStylesheets().add(CultivatorGUI.class.getResource("/it/unina/css/cultivator.css").toExternalForm());
            borderPane.setCenter(node);

    }
}
