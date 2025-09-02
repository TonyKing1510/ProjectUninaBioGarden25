package it.unina.gui;

import it.unina.controller.RegisterController;
import it.unina.controller.WelcomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

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
