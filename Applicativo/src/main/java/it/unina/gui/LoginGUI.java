package it.unina.gui;

import it.unina.controller.LoginController;
import it.unina.controller.WelcomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class LoginGUI {
    private static Parent loginView;
    private LoginGUI() {
        // Private constructor to prevent instantiation
    }

    public static void initializeLoginView(WelcomeController welcomeController) throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginGUI.class.getResource("/it/unina/LoginPage.fxml"));
        loginView = loader.load();
        LoginController controller = loader.getController();
        controller.setRootController(welcomeController);
    }


}
