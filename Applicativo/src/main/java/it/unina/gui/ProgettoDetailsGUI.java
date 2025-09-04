package it.unina.gui;

import it.unina.model.Progetto;
import it.unina.controller.ProgettiDetailsController;
import it.unina.model.Utente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe di utilità per la gestione della GUI dei dettagli di un progetto.
 * <p>
 * Questa classe carica e visualizza la vista {@code ProgettiDetails.fxml},
 * inizializzando il relativo {@link ProgettiDetailsController} con i dati
 * di un progetto e dell'utente corrente.
 * <p>
 * È implementata come utility class: non dovrebbe essere istanziata
 * direttamente e fornisce solo metodi statici.
 *
 * @author entn
 */
public class ProgettoDetailsGUI {

    /**
     * Costruttore package-private per impedire l’uso esterno della classe.
     * <p>
     * L’assenza di un costruttore pubblico riflette la natura statica/utility della classe.
     * @author entn
     */
    ProgettoDetailsGUI() {
        // Impedisce l'istanziazione diretta dall'esterno
    }

    /**
     * Apre una nuova finestra che mostra i dettagli di un progetto.
     * <p>
     * Carica il file {@code ProgettiDetails.fxml}, inizializza il controller
     * {@link ProgettiDetailsController} con il progetto e l’utente passati,
     * e popola le sezioni relative a lotti, colture e attività.
     *
     * @param progetto Il progetto da visualizzare.
     * @param utente   L’utente corrente associato alla sessione.
     * @throws IOException Se si verifica un errore durante il caricamento dell’FXML.
     * @author entn
     */
    public static void openPageProgettoDetailsGUI(Progetto progetto, Utente utente) throws IOException {
        FXMLLoader loader = new FXMLLoader(ProgettoDetailsGUI.class.getResource("/it/unina/ProgettiDetails.fxml"));
        Parent root = loader.load();

        // Configura il controller con i dati del progetto e dell'utente
        ProgettiDetailsController controller = loader.getController();
        controller.setProgetto(progetto);
        controller.setUtente(utente);
        controller.setProgettoDetails(progetto);
        controller.setLottiDetails();
        controller.setColtureDetails();
        controller.setAttivitaDetails();

        // Imposta la scena e mostra la finestra
        Stage stage = new Stage();
        stage.setTitle("Dettagli Progetto");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
