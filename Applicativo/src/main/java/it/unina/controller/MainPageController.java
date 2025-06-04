package it.unina.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

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


}
