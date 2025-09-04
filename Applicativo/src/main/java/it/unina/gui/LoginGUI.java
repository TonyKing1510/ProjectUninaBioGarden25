package it.unina.gui;

import it.unina.controller.LoginController;
import it.unina.controller.WelcomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * Classe di utilità per la gestione della GUI di login.
 * <p>
 * Questa classe carica e inizializza la vista {@code LoginPage.fxml},
 * configurando il relativo {@link LoginController} con il
 * {@link WelcomeController} principale.
 * <p>
 * È implementata come utility class: non deve essere istanziata direttamente.
 *
 * @author entn
 */
public class LoginGUI {

    /**
     * Riferimento alla vista del login caricata da FXML.
     */
    private static Parent loginView;

    /**
     * Costruttore privato per impedire l’istanziazione della classe,
     * riflettendo la sua natura statica/utility.
     * @author entn
     */
    private LoginGUI() {
        // Impedisce l'istanziazione
    }

    /**
     * Inizializza la vista di login caricando il file {@code LoginPage.fxml}.
     * <p>
     * Collega inoltre il controller di login al {@link WelcomeController},
     * in modo che quest’ultimo possa gestire la navigazione tra le viste.
     *
     * @param welcomeController il controller principale della schermata di benvenuto.
     * @throws IOException se si verifica un errore durante il caricamento dell’FXML.
     * @author entn
     */
    public static void initializeLoginView(WelcomeController welcomeController) throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginGUI.class.getResource("/it/unina/LoginPage.fxml"));
        loginView = loader.load();
        LoginController controller = loader.getController();
        controller.setRootController(welcomeController);
    }

    /**
     * Restituisce la vista del login precedentemente caricata.
     *
     * @return la {@link Parent} che rappresenta la schermata di login, oppure {@code null} se non è stata ancora inizializzata.
     * @author entn
     */
    public static Parent getLoginView() {
        return loginView;
    }
}
