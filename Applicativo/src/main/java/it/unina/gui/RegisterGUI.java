package it.unina.gui;

import it.unina.controller.RegisterController;
import it.unina.controller.WelcomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Classe di utilità per la gestione della GUI della pagina di registrazione.
 * <p>
 * Questa classe carica e inizializza la vista {@code RegisterPage.fxml}
 * e collega il relativo controller {@link RegisterController} con il
 * controller principale {@link WelcomeController}.
 * <p>
 * È implementata come utility class: il costruttore è privato per
 * evitare istanziazioni e i metodi sono statici.
 *
 * @author entn
 */
public class RegisterGUI {

    private static Parent registerView;

    /**
     * Costruttore privato per impedire l'istanziazione della classe utility.
     * @author entn
     */
    private RegisterGUI() {
        // Impedisce l'istanziazione
    }

    /**
     * Inizializza la pagina di registrazione caricando il file FXML
     * e collegando il {@link RegisterController} al controller principale.
     *
     * @param welcomeController il controller principale {@link WelcomeController}
     *                          a cui associare la pagina di registrazione.
     * @throws IOException se si verifica un errore durante il caricamento dell'FXML.
     * @author entn
     */
    public static void initializeRegisterPage(WelcomeController welcomeController) throws IOException {
        FXMLLoader loader = new FXMLLoader(RegisterGUI.class.getResource("/it/unina/RegisterPage.fxml"));
        registerView = loader.load();
        RegisterController controller = loader.getController();
        controller.setRootController(welcomeController);
    }

}
