package it.unina.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.scene.Node;
import io.github.palexdev.materialfx.controls.MFXTextField;

import java.awt.event.MouseAdapter;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

public class WelcomeController {

  @FXML private StackPane rootPane;

  private Parent loginView;
  private Parent registerView;


  @FXML public void initialize() {
    try {
      loadLoginView();
      loadRegisterView();
      showView(loginView);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void loadLoginView() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/Login.fxml"));
    loginView = loader.load();
    LoginController controller = loader.getController();
    controller.setRootController(this); 
  }

  private void loadRegisterView() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/Register.fxml"));
    registerView = loader.load();
    RegisterController controller = loader.getController();
    controller.setRootController(this);
  }

  public void switchToLogin() {
    showView(loginView);
  }

  public void switchToRegister() {
    showView(registerView);
  }

  private void showView(Node newView) {
    if (!rootPane.getChildren().isEmpty()) {
      Node currentView = rootPane.getChildren().get(0);

      newView.setTranslateX(rootPane.getWidth());
      rootPane.getChildren().add(newView);


      TranslateTransition outAnim = new TranslateTransition(Duration.millis(350), currentView);
      outAnim.setToX(-rootPane.getWidth());

      TranslateTransition inAnim = new TranslateTransition(Duration.millis(350), newView);
      inAnim.setToX(0);

      outAnim.setOnFinished(event -> {
        rootPane.getChildren().remove(currentView);
      });

      outAnim.play();
      inAnim.play();
    } else {

      rootPane.getChildren().setAll(newView);
    }
  }



}
