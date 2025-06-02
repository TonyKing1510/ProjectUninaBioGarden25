package it.unina.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainPageController {

  @FXML private Button plantManagementButton;
  @FXML private Button userManagementButton;
  @FXML private Button statisticsButton;

  @FXML private BorderPane borderPane;

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

}
