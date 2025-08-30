package it.unina.controller;

import it.unina.dao.LottoDAO;
import it.unina.dao.ProgettoDAO;
import it.unina.gui.VisualizeProjectGUI;
import it.unina.implementazionepostgresql.LottoDAOImpl;
import it.unina.implementazionepostgresql.ProgettoDAOImpl;
import it.unina.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

/**
 * Controller per la gestione della sezione Progetti dell'applicazione.
 * Permette di aprire un dialogo per l'aggiunta di nuovi progetti.
 *
 * @author entn
 */
public class ProjectViewController {
    @FXML
    private VBox contentBox;
    @FXML
    private Button addButton;
    @FXML
    private TextField titleProject;
    @FXML
    private DatePicker dateInit;
    @FXML
    private DatePicker dateFine;
    @FXML
    private Button addButtonProject;
    @FXML
    private MenuButton stagioneMenu;
    @FXML
    private MenuButton lottoMenu;

    private Utente utenteLoggato;
    private Progetto progettoAdd;
    private final ProgettoDAO progettoDAO = new ProgettoDAOImpl();
    private final LottoDAO lottoDAO = new LottoDAOImpl();


    public void setUtenteLoggato(Utente utente) {
        this.utenteLoggato = utente;
    }

    @FXML
    private void initialize() {
        // Carica i progetti dell'utente loggato
        Platform.runLater(this::loadProjects);
    }


    public void loadProjects() {
        List<Progetto> progetti = progettoDAO.getProgettiByIdUtente(utenteLoggato.getIdUtente());
        for (Progetto progetto : progetti) {
            try {
                System.out.println("Caricamento progetto: " + progetto.getIdProgetto());
                VisualizeProjectGUI.initProjectCard(progetto, contentBox, utenteLoggato);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
