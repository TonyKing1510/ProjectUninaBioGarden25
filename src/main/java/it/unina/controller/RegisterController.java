package it.unina.controller;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.scene.Node;
import io.github.palexdev.materialfx.controls.MFXTextField;


public class RegisterController {

  private WelcomeController rootController;

  @FXML private TextField regUser;

  @FXML private PasswordField regPass;

  @FXML private TextField regEmail;

  @FXML private Button registerButton;

  @FXML private Hyperlink showLogin;

  public void setRootController(WelcomeController rootController){
    this.rootController = rootController;
  }


  private void onRegister() {
    /* */
    System.out.println("Ok,register!");
  }

  @FXML private void showLogin() {
    rootController.switchToLogin();

  }
}
