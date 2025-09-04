package it.unina.gui;

import it.unina.controller.CreateActivityController;
import it.unina.model.Utente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

/**
 * Classe di utilità per la gestione della GUI di creazione di una nuova attività.
 * <p>
 * Questa classe carica e visualizza la vista {@code CreateActivityPage.fxml},
 * inizializzando il {@link CreateActivityController} con l’utente loggato
 * e le colture selezionate tramite checkbox.
 * <p>
 * È implementata come utility class e fornisce solo metodi statici.
 *
 * @author entn
 */
public class CreateActivityGUI {

    /**
     * Costruttore package-private per impedire l’istanziazione esterna.
     */
    CreateActivityGUI() {
    }

    /**
     * Apre una nuova finestra per la creazione di un’attività.
     * <p>
     * Carica il file {@code CreateActivityPage.fxml}, inizializza il controller
     * {@link CreateActivityController} con l’utente loggato e la lista di colture
     * selezionate, e applica il foglio di stile {@code style.css}.
     *
     * @param utenteLoggato   l’utente attualmente autenticato.
     * @param coltureCheckBox lista di checkbox che rappresentano le colture selezionate.
     * @throws Exception se si verifica un errore durante il caricamento dell’FXML.
     * @author entn
     */
    public static void openCreateActivity(Utente utenteLoggato, List<CheckBox> coltureCheckBox) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateActivityGUI.class.getResource("/it/unina/CreateActivityPage.fxml"));
        Parent root = fxmlLoader.load();

        // Configura il controller
        CreateActivityController controller = fxmlLoader.getController();
        controller.setUtenteLoggato(utenteLoggato);
        controller.setColtureList(coltureCheckBox);
        controller.setColtureMenu();

        // Imposta la scena e mostra la finestra
        Stage stage = new Stage();
        stage.setTitle("Crea Nuova Attività");
        Scene scene = new Scene(root);

        // Aggancio del CSS
        scene.getStylesheets().add(
                Objects.requireNonNull(CreateActivityGUI.class.getResource("/it/unina/css/style.css")).toExternalForm()
        );

        stage.setScene(scene);
        stage.show();
    }
}
