package it.unina.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/LoginPage.fxml"));
    loginView = loader.load();

    LoginController controller = loader.getController();
    controller.setRootController(this); 
  }

  private void loadRegisterView() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/RegisterPage.fxml"));
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
    }catch (IOException e) {
      e.printStackTrace();

      /* Insert here logic to display what went wrong. */
    }
  }
}
