package it.unina.controller;

import it.unina.dao.UtenteDAO;
import it.unina.gui.MainAppGui;
import it.unina.implementazionePostgreSQL.UtenteDAOImpl;
import it.unina.model.Ruolo;
import it.unina.model.Utente;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

/**
 * Controller per la schermata di registrazione utente.
 * Gestisce l'interfaccia grafica e la logica di registrazione di un nuovo utente.
 * @author entn, Sderr12
 */
public class RegisterController {

  /** Controller principale per il cambio schermate. */
  private WelcomeController rootController;

  @FXML private TextField nomeField;
  @FXML private TextField cognomeField;
  @FXML private TextField mailField;
  @FXML private PasswordField passwordField;
  @FXML private Button registerButton;
  @FXML private MenuButton ruoloMenu;
  @FXML private TextField usernameField;

  /** Utente appena registrato, se la registrazione ha successo. */
  public Utente utenteRegistrato;

  /** Riferimento allo stage precedente, utile per tornare indietro. */
  private Stage previousStage;

  /** DAO per la gestione dei dati utente. */
  private final UtenteDAO utenteDAO = new UtenteDAOImpl();

  /**
   * Imposta il controller principale per il cambio schermate.
   *
   * @param rootController il controller principale (es. WelcomeController)
   */
  public void setRootController(WelcomeController rootController) {
    this.rootController = rootController;
  }

  /**
   * Inizializza la GUI al caricamento.
   * Imposta il menu dei ruoli e i listener dei pulsanti.
   * @author entn
   */
  @FXML
  private void initialize() {
    ruoloMenu.setText("Seleziona ruolo");

    Platform.runLater(() -> {
      ruoloMenu.setText("Seleziona ruolo");
      setRuoli();
    });
  }

  /**
   * Popola il menu a tendina con i ruoli disponibili.
   * Quando viene selezionato un ruolo, viene aggiornato il testo del menu.
   * @author entn
   */
  private void setRuoli() {
    List<String> voci = List.of("proprietario", "coltivatore");
    for (String opzione : voci) {
      MenuItem item = new MenuItem(opzione);

      item.setOnAction(event -> {
        ruoloMenu.setText(opzione);
        System.out.println("Hai selezionato: " + opzione);
      });

      ruoloMenu.getItems().add(item);
    }
  }

  /**
   * Gestisce il processo di registrazione utente.
   * Valida i campi, crea l'oggetto {@link Utente} e lo salva tramite DAO.
   * Se la registrazione ha successo, apre l'applicazione principale.
   * @author entn
   */
  @FXML
  private void onRegister() {
    String nome = nomeField.getText().trim();
    String cognome = cognomeField.getText().trim();
    String mail = mailField.getText().trim();
    String password = passwordField.getText().trim();
    String username = usernameField.getText().trim();

    Ruolo ruolo;
    try {
      ruolo = Ruolo.valueOf(ruoloMenu.getText().toUpperCase());
    } catch (IllegalArgumentException e) {
      showAlert("Errore", "Seleziona un ruolo valido.");
      return;
    }

    if (nome.isEmpty() || cognome.isEmpty() || mail.isEmpty() || password.isEmpty()) {
      showAlert("Errore", "Tutti i campi sono obbligatori.");
      return;
    }

    utenteRegistrato = new Utente(nome, cognome, mail, password, ruolo, username);
    boolean register = utenteDAO.addUtente(utenteRegistrato);

    if (register) {
      System.out.println("Utente registrato: " + utenteRegistrato.getNome());
      MainAppGui.initializeMainApp((Stage) registerButton.getScene().getWindow(), utenteRegistrato);
    } else {
      System.out.println("Registrazione fallita.");
    }

    showAlert("Registrazione completata", "Utente registrato con successo!");
    rootController.switchToLogin();
  }

  /**
   * Torna alla schermata di login.
   * @author Sderr12
   */
  @FXML
  private void showLogin() {
    rootController.switchToLogin();
  }

  /**
   * Mostra un messaggio di avviso a schermo.
   *
   * @param title   il titolo della finestra di dialogo
   * @param message il messaggio da mostrare
   * @author entn
   */
  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Imposta lo stage precedente, utile per gestire il ritorno.
   *
   * @param previousStage lo stage precedente
   * @author entn
   */
  public void setPreviousStage(Stage previousStage) {
    this.previousStage = previousStage;
  }

}
