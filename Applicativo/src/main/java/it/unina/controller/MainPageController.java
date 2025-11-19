package it.unina.controller;

import it.unina.gui.VisualizeProjectGUI;
import it.unina.gui.VisualizeNotificheGUI;
import it.unina.gui.CreateProjectGUI;
import it.unina.gui.LoginGUI;
import it.unina.model.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller per la pagina principale dell'applicazione.
 * Gestisce la visualizzazione dinamica delle sotto-sezioni della GUI
 * e visualizza le informazioni dell'utente loggato.
 *
 * @author entn
 */
public class MainPageController {

  /**
   * Etichetta per mostrare il nome e cognome dell'utente loggato.
   */
  @FXML
  private Label nomeCognomeLabel;


  /**
   * Pannello principale dove vengono caricate dinamicamente le viste delle sezioni.
   */
  @FXML
  private BorderPane borderPane;



  @FXML
  private BorderPane borderPane2;

  /**
   * Utente attualmente loggato nel sistema.
   */
  private Utente utenteLoggato;

  /**
   * Stage precedente, utile per operazioni come il ritorno al login o chiusura.
   *@author entn
   */
  private Stage previousStage;




  /**
   * Imposta lo stage precedente (es. finestra di login).
   *
   * @param previousStage lo stage precedente
   * @author entn
   */
  public void setPreviousStage(Stage previousStage) {
    this.previousStage = previousStage;
  }

  /**
   * Metodo di inizializzazione chiamato automaticamente da JavaFX
   * dopo il caricamento del file FXML associato.
   * @author entn, Sderr12
   */
  @FXML
  public void initialize() {
    // Inizializzazione futura, se necessaria
  }
  

  @FXML
  private void goToVisualizeProject() throws IOException {
    VisualizeProjectGUI.initializeProjectView(borderPane, utenteLoggato);
  }

  @FXML
  private void goToVisualizeNotifiche() throws IOException{
      VisualizeNotificheGUI.initializeNotificheView(borderPane, utenteLoggato);
  }

  /**
   * Carica la vista dei progetti all'interno del pannello principale.
   *
   * @throws IOException se il caricamento della vista fallisce
   * @author entn
   */
  @FXML
  private void goToCreateProject() throws IOException {

    CreateProjectGUI.openPageCreateProject(utenteLoggato);
  }

  /**
   * Imposta l'utente loggato e aggiorna la GUI per mostrare il suo nome e cognome.
   *\
   * @param utente l'utente attualmente loggato
   *@author entn
   */
  public void setUtenteLoggato(Utente utente) {
    this.utenteLoggato = utente;
    nomeCognomeLabel.setText(utente.getNome() + " " + utente.getCognome());
  }


  /**   * Gestisce il logout dell'utente, chiudendo la finestra corrente
   * e tornando alla schermata di login.
   *
   * @throws IOException se il caricamento della vista di login fallisce
   * @author entn
   */
  public void logout() throws IOException {
    if (previousStage != null) {
      Stage currentStage = (Stage) borderPane.getScene().getWindow();
      currentStage.close();
      clearData();
      WelcomeController previousController = (WelcomeController) previousStage.getUserData();

      LoginGUI.initializeLoginView(previousController);
    }
  }

    /**
     * Resetta i dati dell'utente e pulisce la GUI.
     * @author entn
     */
  private void clearData() {
    utenteLoggato = null;
    nomeCognomeLabel.setText("");
    borderPane.setCenter(null);
  }
}
