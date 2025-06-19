package it.unina.gui;

import it.unina.controller.LottiController;
import it.unina.controller.components.LottoCardController;
import it.unina.controller.components.ProjectCardController;
import it.unina.model.Lotto;
import it.unina.model.Utente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * ColtureGUI è la classe che gestisce la visualizzazione della vista Colture nell'applicazione.
 * Questa classe carica il file FXML associato alla vista Colture e lo imposta in un BorderPane.
 *
 * @author entn
 */
public class LottiGUI {
    LottiGUI() {
        // Constructor logic can be added here if needed
    }

    /**
     * Inizializza la vista Colture e la imposta nel BorderPane fornito.
     *
     * @param borderPane Il BorderPane in cui inserire la vista Colture.
     * @throws IOException se C'è un errore durante il caricamento del file FXML.
     * @author entn
     */
    public static void initLottiView(BorderPane borderPane, Utente utente) throws IOException {
        FXMLLoader loader = new FXMLLoader(LottiGUI.class.getResource("/it/unina/LottiView.fxml"));
        Parent node = loader.load();
        node.getStylesheets().add(LottiGUI.class.getResource("/it/unina/css/coltureview.css").toExternalForm());
        borderPane.setCenter(node);
        LottiController controller = loader.getController();
        controller.setUtente(utente);

    }

    public static void initLottiCard(VBox contentBox, Utente utente, Lotto lotti) throws IOException {
        FXMLLoader loader = new FXMLLoader(ProjectGUI.class.getResource("/it/unina/components/LottiCard.fxml"));
        AnchorPane card = loader.load();

        // Ottieni il controller della card e setta i dati
        LottoCardController controller = loader.getController();
        controller.setUtente(utente);
        controller.setLotti(lotti);

        contentBox.getChildren().add(card);
    }
}
