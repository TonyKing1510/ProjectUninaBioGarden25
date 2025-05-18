package it.unina.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class WelcomeController {

    @FXML private VBox loginPane;

    @FXML private VBox registerPane;



    @FXML private void initialize() {
        loginPane.setVisible(true);
        loginPane.setOpacity(1.0);
        registerPane.setVisible(false);
        registerPane.setOpacity(0.0);
    }

    @FXML private void onLogin() {
        System.out.println("OK!");
    }

    @FXML private void onRegister() {
        System.out.println("OK!");
    }

    @FXML private void showRegister() {
        animateSwitch(loginPane, registerPane);
    }

    @FXML private void showLogin() {
        animateSwitch(registerPane, loginPane);
    }

    private void animateSwitch(VBox from, VBox to) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), from);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            from.setVisible(false);

            to.setVisible(true);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), to);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        fadeOut.play();
    }
}
