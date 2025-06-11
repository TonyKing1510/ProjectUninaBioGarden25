package it.unina.gui;

import it.unina.controller.RegisterController;
import it.unina.controller.WelcomeController;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

public class RegisterGUI {
    private static Parent registerView;

    private RegisterGUI() {
        // Private constructor to prevent instantiation
    }

    public static void initializeRegisterPage(WelcomeController welcomeController) throws IOException {
        FXMLLoader loader = new FXMLLoader(RegisterGUI.class.getResource("/it/unina/RegisterPage.fxml"));
        registerView = loader.load();
        RegisterController controller = loader.getController();
        controller.setRootController(welcomeController);
    }



}
