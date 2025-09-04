package it.unina.gui;

import it.unina.controller.CreateProjectController;
import it.unina.model.Utente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Classe di utilità per la gestione della GUI di creazione di un nuovo progetto.
 * <p>
 * Questa classe carica e visualizza la vista {@code CreateProjectPage.fxml},
 * inizializzando il {@link CreateProjectController} con l'utente loggato
 * e configurando il menu dei lotti disponibili.
 * <p>
 * È implementata come utility class e fornisce solo metodi statici.
 *
 * @author entn
 */
public class CreateProjectGUI {

    /**
     * Costruttore package-private per impedire l’uso esterno della classe,
     * mantenendo la natura utility.
     * @author entn
     */
    CreateProjectGUI() {

    }

    /**
     * Apre una nuova finestra per la creazione di un progetto.
     * <p>
     * Carica il file {@code CreateProjectPage.fxml}, inizializza
     * il controller {@link CreateProjectController} con l’utente loggato
     * e configura il menu dei lotti disponibili.
     * <p>
     * Applica inoltre il foglio di stile {@code style.css}.
     *
     * @param utenteLoggato l’utente attualmente autenticato.
     * @throws IOException se si verifica un errore durante il caricamento dell’FXML.
     * @author entn
     */
    public static void openPageCreateProject(Utente utenteLoggato) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateProjectGUI.class.getResource("/it/unina/CreateProjectPage.fxml"));
        Parent root = fxmlLoader.load();

        // Configura il controller
        CreateProjectController controller = fxmlLoader.getController();
        controller.setUtenteLoggato(utenteLoggato);
        controller.setLottiMenu();

        // Imposta la scena e mostra la finestra
        Stage stage = new Stage();
        stage.setTitle("Crea Nuovo Progetto");

        Scene scene = new Scene(root);

        // Aggancio del CSS
        scene.getStylesheets().add(
                Objects.requireNonNull(CreateProjectGUI.class.getResource("/it/unina/css/style.css")).toExternalForm()
        );

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
