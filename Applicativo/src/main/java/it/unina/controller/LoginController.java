package it.unina.controller;


import it.unina.controller.WelcomeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.scene.control.TextField;

public class LoginController {

  @FXML private TextField usernameField;

  private WelcomeController rootController;
  

  public void setRootController( WelcomeController rootController){
    this.rootController = rootController;
  }

  @FXML private void goToRegister(ActionEvent event) {
    rootController.switchToRegister();
  }

  @FXML private void onLogin(ActionEvent event){
    System.out.println("Hello!");
    rootController.goToMainApp(event);
  }
}
