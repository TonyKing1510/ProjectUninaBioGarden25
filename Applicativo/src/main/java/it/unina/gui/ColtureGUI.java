package it.unina.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * ColtureGUI è la classe che gestisce la visualizzazione della vista Colture nell'applicazione.
 * Questa classe carica il file FXML associato alla vista Colture e lo imposta in un BorderPane.
 *
 * @author entn
 */
public class ColtureGUI {
    ColtureGUI() {
        // Constructor logic can be added here if needed
    }

    /**
     * Inizializza la vista Colture e la imposta nel BorderPane fornito.
     *
     * @param borderPane Il BorderPane in cui inserire la vista Colture.
     * @throws IOException se C'è un errore durante il caricamento del file FXML.
     * @author entn
     */
    public static void initializeColtureView(BorderPane borderPane) throws IOException {
        FXMLLoader loader = new FXMLLoader(ColtureGUI.class.getResource("/it/unina/LottiView.fxml"));
        Parent node = loader.load();
        node.getStylesheets().add(ColtureGUI.class.getResource("/it/unina/css/coltureview.css").toExternalForm());
        borderPane.setCenter(node);

    }

    public static void addColtureView(BorderPane borderPane) throws IOException {
        FXMLLoader loader = new FXMLLoader(ColtureGUI.class.getResource("/it/unina/components.fxml"));
        Parent node = loader.load();
        node.getStylesheets().add(ColtureGUI.class.getResource("/it/unina/css/coltureview.css").toExternalForm());
        borderPane.setCenter(node);
    }
}
