package it.unina.controller;

import it.unina.model.Utente;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import java.io.IOException;


/**
 * Controller per la schermata di benvenuto dell'applicazione.
 * @author entn, Sderr12
 */
public class WelcomeController {

  @FXML private StackPane rootPane;

  private Parent loginView;
  private Parent registerView;

  /** Utente attualmente loggato. */
  private Utente utenteLoggato;

  /**
   * Imposta l'utente attualmente loggato.
   *
   * @param utente l'utente autenticato
   * @author entn
   */
  public void setUtenteLoggato(Utente utente) {
    this.utenteLoggato = utente;
  }

  /**
   * Restituisce l'utente attualmente loggato.
   *
   * @return l'utente autenticato, oppure null se nessuno è loggato
   * @author entn
   */
  public Utente getUtenteLoggato() {
    return utenteLoggato;
  }

  /**
   * Inizializza il controller caricando le viste di login e registrazione.
   * Visualizza inizialmente la schermata di login.
   *
   * @author entn,Sderr12
   */
  @FXML
  public void initialize() {
    try {
      loadLoginView();
      loadRegisterView();
      showView(loginView);
      setUtenteLoggato(utenteLoggato); // Di default è null
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Carica la vista di login dal file FXML e imposta il controller.
   *
   * @throws IOException se il file FXML non viene trovato o non può essere caricato
   * @author Sderr12
   */
  private void loadLoginView() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/LoginPage.fxml"));
    loginView = loader.load();
    LoginController controller = loader.getController();
    controller.setRootController(this);
  }

  /**
   * Carica la vista di registrazione dal file FXML e imposta il controller.
   *
   * @throws IOException se il file FXML non viene trovato o non può essere caricato
   * @author Sderr12
   */
  private void loadRegisterView() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/RegisterPage.fxml"));
    registerView = loader.load();
    RegisterController controller = loader.getController();
    controller.setRootController(this);
  }

  /**
   * Passa alla vista di login.
   *
   * @author Sderr12
   */
  public void switchToLogin() {
    showView(loginView);
  }

  /**
   * Passa alla vista di registrazione.
   *
   * @author Sderr12
   */
  public void switchToRegister() {
    showView(registerView);
  }

  /**
   * Mostra una nuova vista all'interno del contenitore principale con un'animazione di transizione.
   *
   * @param newView la vista da visualizzare (Parent Node)
   * @author Sderr12
   */
  private void showView(Node newView) {
    if (!rootPane.getChildren().isEmpty()) {
      Node currentView = rootPane.getChildren().get(0);

      newView.setTranslateX(rootPane.getWidth());
      rootPane.getChildren().add(newView);

      TranslateTransition outAnim = new TranslateTransition(Duration.millis(350), currentView);
      outAnim.setToX(-rootPane.getWidth());

      TranslateTransition inAnim = new TranslateTransition(Duration.millis(350), newView);
      inAnim.setToX(0);

      outAnim.setOnFinished(event -> rootPane.getChildren().remove(currentView));

      outAnim.play();
      inAnim.play();
    } else {
      rootPane.getChildren().setAll(newView);
    }
  }

  /**
   * Avvia la schermata principale dell'applicazione dopo il login o la registrazione.
   * Chiude la finestra corrente e apre la schermata principale (`MainPage.fxml`).
   *
   * @param event l'evento associato (es. clic su un pulsante)
   * @author Sderr12
   */
  protected void goToMainApp(ActionEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/MainPage.fxml"));
      Parent mainRoot = loader.load();

      Scene scene = new Scene(mainRoot, 1540, 790);
      Stage stage = new Stage();
      stage.setTitle("");
      stage.setScene(scene);
      stage.show();

      Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      currentStage.close();
    } catch (IOException e) {
      e.printStackTrace();
      // Inserire qui eventuale logica per segnalare l'errore
    }
  }
}
