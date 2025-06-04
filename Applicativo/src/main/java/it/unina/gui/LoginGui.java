package it.unina.gui;

import it.unina.controller.LoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//classe per ora inutilizzata devo vedere meglio come gestire le GUI

public class LoginGui extends Application {

    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Label statusLabel;

    @Override
    public void start(Stage stage) {
        usernameField = new TextField();
        usernameField.setPromptText("Email");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        loginButton = new Button("Login");
        statusLabel = new Label();

        /*loginButton.setOnAction(e -> {
            LoginController controller = new LoginController();
            boolean success = controller.login(usernameField.getText(), passwordField.getText());

            if (success) {
                statusLabel.setText("Login riuscito!");
                // puoi caricare la GUI principale qui
            } else {
                statusLabel.setText("Email o password errati.");
            }
        });

        VBox root = new VBox(10, usernameField, passwordField, loginButton, statusLabel);
        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Login - UninaBioGarden");
        stage.show();*/
    }

    public static void main(String[] args) {
        launch();
    }
}
