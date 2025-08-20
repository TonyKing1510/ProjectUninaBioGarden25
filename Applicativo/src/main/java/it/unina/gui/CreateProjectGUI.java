package it.unina.gui;

import it.unina.controller.CreateProjectController;
import it.unina.model.Utente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;


public class CreateProjectGUI {
    CreateProjectGUI(){

    }

    public static void openPageCreateProject(Utente utenteLoggato) throws IOException {
        // Logica per aprire la pagina di creazione del progetto
        System.out.println("Apertura della pagina di creazione del progetto...");
        FXMLLoader fxmlLoader = new FXMLLoader(CreateProjectGUI.class.getResource("/it/unina/CreateProjectPage.fxml"));
        Parent root = fxmlLoader.load();
        CreateProjectController controller = fxmlLoader.getController();
        System.out.println("Utente caricato: " + utenteLoggato);
        controller.setUtenteLoggato(utenteLoggato);
        controller.setLottiMenu();
        // Imposta la scena e mostra la finestra
        Stage stage = new Stage();
        stage.setTitle("Crea Nuovo Progetto");
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
    }
}
