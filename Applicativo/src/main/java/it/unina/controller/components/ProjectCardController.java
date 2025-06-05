package it.unina.controller.components;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ProjectCardController {

    @FXML private AnchorPane cardPane;
    @FXML private void onProjectClick() {
        System.out.println("Hello! I'm a project card!");
    }
}
