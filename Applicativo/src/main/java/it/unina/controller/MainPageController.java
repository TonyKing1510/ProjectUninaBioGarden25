package it.unina.controller;

import it.unina.model.Utente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainPageController {


  /*@FXML private Button plantManagementButton;
  @FXML private Button userManagementButton;
  @FXML private Button statisticsButton;*/


  @FXML private Label nomeCognomeLabel;

  @FXML private BorderPane borderPane;

  private Utente utenteLoggato;
  private Stage previousStage;


  public void setPreviousStage(Stage previousStage) {
    this.previousStage = previousStage;
  }


  @FXML
  public void initialize() {
   // TODO farà qualcosa;
  }

  @FXML private void goToPlantManagement() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/ColtureView.fxml"));
      Parent node = loader.load();
      node.getStylesheets().add(getClass().getResource("/it/unina/css/coltureview.css").toExternalForm());
      borderPane.setCenter(node);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML private void goToCultivator() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/Cultivator.fxml"));
      Parent node = loader.load();
      node.getStylesheets().add(getClass().getResource("/it/unina/css/cultivator.css").toExternalForm());
      borderPane.setCenter(node);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  @FXML private void goToProject() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/ProjectView.fxml"));
      Parent node = loader.load();
      node.getStylesheets().add(getClass().getResource("/it/unina/css/coltureview.css").toExternalForm());
      borderPane.setCenter(node);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }



  public void setUtenteLoggato(Utente utente) {
    this.utenteLoggato = utente;
    nomeCognomeLabel.setText(utente.getNome() + " " + utente.getCognome());
  }



}
