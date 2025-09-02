package it.unina.gui;

import it.unina.controller.CreateProjectController;
import it.unina.model.Utente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class CreateProjectGUI {
    CreateProjectGUI(){

    }

    public static void openPageCreateProject(Utente utenteLoggato) throws IOException {
        // Logica per aprire la pagina di creazione del progetto
        FXMLLoader fxmlLoader = new FXMLLoader(CreateProjectGUI.class.getResource("/it/unina/CreateProjectPage.fxml"));
        Parent root = fxmlLoader.load();
        CreateProjectController controller = fxmlLoader.getController();
        controller.setUtenteLoggato(utenteLoggato);
        controller.setLottiMenu();

        // Imposta la scena e mostra la finestra
        Stage stage = new Stage();
        stage.setTitle("Crea Nuovo Progetto");

        Scene scene = new Scene(root);

        // 🔥 Aggancio del CSS
        scene.getStylesheets().add(
                CreateProjectGUI.class.getResource("/it/unina/css/style.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

}
