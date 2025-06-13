package it.unina.controller;

import it.unina.gui.ProjectGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
/**
 * Controller per la gestione della sezione Progetti dell'applicazione.
 * Permette di aprire un dialogo per l'aggiunta di nuovi progetti.
 *
 * @author entn
 */
public class ProjectController {
    @FXML
    private Button addButton;


    /**
     * Apre un dialogo per l'aggiunta di un nuovo progetto.
     * @author entn
     */
    @FXML
    public void openAddProjectDialog() throws IOException {
        ProjectGUI.openAddProjectView(addButton.getScene().getWindow());
    }
}
