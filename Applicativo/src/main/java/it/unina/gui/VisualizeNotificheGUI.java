package it.unina.gui;


import it.unina.controller.NotificheViewController;
import it.unina.controller.components.ProjectCardController;
import it.unina.model.Progetto;
import it.unina.model.Utente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;


public class VisualizeNotificheGUI {
    VisualizeNotificheGUI(){

    }

    public static void initializeNotificheView(BorderPane borderPane, Utente utente) throws IOException {
        FXMLLoader loader = new FXMLLoader(VisualizeProjectGUI.class.getResource("/it/unina/NotificheView.fxml"));
        Parent node = loader.load();
        NotificheViewController controller = loader.getController();  //
        controller.setUtenteLoggatoLoadNotifiche(utente);  //
        node.getStylesheets().add(Objects.requireNonNull(VisualizeProjectGUI.class.getResource("/it/unina/css/coltureview.css")).toExternalForm());
        borderPane.setCenter(node);
    }


}
