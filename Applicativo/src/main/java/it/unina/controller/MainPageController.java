package it.unina.controller;

import it.unina.gui.ColtureGUI;
import it.unina.gui.CultivatorGUI;
import it.unina.gui.ProjectGUI;
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
   * @author entn
   */
  @FXML
  public void initialize() {
    // Inizializzazione futura, se necessaria
  }

  /**
   * Carica la vista di gestione delle colture all'interno del pannello principale.
   *
   * @throws IOException se il caricamento della vista fallisce
   * @author entn
   */
  @FXML
  private void goToPlantManagement() throws IOException {
    ColtureGUI.initializeColtureView(borderPane);
  }

  /**
   * Carica la vista dei coltivatori all'interno del pannello principale.
   *
   * @throws IOException se il caricamento della vista fallisce
   * @author entn
   */
  @FXML
  private void goToCultivator() throws IOException {
    CultivatorGUI.initializeCultivatorView(borderPane);
  }

  /**
   * Carica la vista dei progetti all'interno del pannello principale.
   *
   * @throws IOException se il caricamento della vista fallisce
   * @author entn
   */
  @FXML
  private void goToProject() throws IOException {
    ProjectGUI.initializeProjectView(borderPane);
  }

  /**
   * Imposta l'utente loggato e aggiorna la GUI per mostrare il suo nome e cognome.
   *
   * @param utente l'utente attualmente loggato
   *@author entn
   */
  public void setUtenteLoggato(Utente utente) {
    this.utenteLoggato = utente;
    nomeCognomeLabel.setText(utente.getNome() + " " + utente.getCognome());
  }
}
