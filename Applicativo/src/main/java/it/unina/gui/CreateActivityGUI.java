package it.unina.gui;

import it.unina.controller.CreateActivityController;
import it.unina.model.Colture;
import it.unina.model.Utente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.util.List;

public class CreateActivityGUI {
    CreateActivityGUI() {

    }

    public static void openCreateActivity(Utente utenteLoggato, List<CheckBox> coltureCheckBox) throws Exception {
        System.out.println("Create Activity GUI opened");
        FXMLLoader fxmlLoader = new FXMLLoader(CreateActivityGUI.class.getResource("/it/unina/CreateActivityPage.fxml"));
        fxmlLoader.load();
        CreateActivityController controller = fxmlLoader.getController();
        System.out.println("Controller loaded: " + controller);
        controller.setUtenteLoggato(utenteLoggato);
        controller.setColtureCheckBox(coltureCheckBox);

        Stage stage = new Stage();
        stage.setTitle("Crea Nuova Attività");
        Scene scene = new Scene(fxmlLoader.getRoot());
        scene.getStylesheets().add(
                CreateActivityGUI.class.getResource("/it/unina/css/style.css").toExternalForm()
        );
        stage.setScene(scene);



    }
}
