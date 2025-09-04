package it.unina.controller;

import it.unina.dao.UtenteDAO;
import it.unina.implementazionepostgresql.UtenteDAOImpl;
import it.unina.model.Utente;
import it.unina.gui.MainAppGui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * Controller per la schermata di login.
 * Gestisce l'autenticazione dell'utente e il passaggio alla schermata principale
 * o alla schermata di registrazione.
 *
 * @author entn,Sderr12
 */
public class LoginController {

  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;
  @FXML private Button accediButton;

  private WelcomeController rootController;

  private final UtenteDAO utenteDAO = new UtenteDAOImpl();

  /**
   * Initilize Gui for the login view and set
   * button to accept Enter as a valid input
   * when fields are correctly filled.
   *
   * @author Sderr12
   */
  public void initialize() {
    accediButton.setDefaultButton(true);
  }

  /**
   * Imposta il controller principale (WelcomeController)
   * per consentire il passaggio tra le viste.
   *
   * @param rootController il controller principale
   * @author Sderr12
   */
  public void setRootController(WelcomeController rootController) {
    this.rootController = rootController;
  }

  /**
   * Metodo chiamato quando l'utente clicca su "Registrati".
   * Passa alla schermata di registrazione.
   *
   * @param event l'evento del clic sul pulsante
   * @author Sderr12
   */
  @FXML
  private void goToRegister(ActionEvent event) {
    rootController.switchToRegister();
  }

  /**
   * Gestisce il login dell'utente. Verifica le credenziali e,
   * in caso positivo, apre la schermata principale dell'applicazione.
   *
   * @param event l'evento del clic sul pulsante di login
   * @author entn
   */
  @FXML
  private void onLogin(ActionEvent event) {
    String username = usernameField.getText().trim();
    String password = passwordField.getText();

    if (username.isEmpty() || password.isEmpty()) {
      showAlert("Errore", "Inserisci email e password.");
      return;
    }

    Utente utente = utenteDAO.login(username, password);
    

    if (utente != null) {
      MainAppGui.initializeMainApp((Stage) accediButton.getScene().getWindow(), utente);
    } else {
      showAlert("Login fallito", "Email o password non validi.");
    }
  }

  /**
   * Mostra un messaggio di errore all'utente tramite una finestra di dialogo.
   *
   * @param titolo il titolo dell'alert
   * @param messaggio il messaggio da visualizzare all'interno dell'alert
   * @author entn
   */
  private void showAlert(String titolo, String messaggio) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(titolo);
    alert.setHeaderText(null);
    alert.setContentText(messaggio);
    alert.showAndWait();
  }
}
