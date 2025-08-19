package it.unina.controller;

import it.unina.dao.LottoDAO;
import it.unina.dao.ProgettoDAO;
import it.unina.gui.VisualizeProjectGUI;
import it.unina.implementazionePostgreSQL.LottoDAOImpl;
import it.unina.implementazionePostgreSQL.ProgettoDAOImpl;
import it.unina.model.Lotto;
import it.unina.model.Progetto;
import it.unina.model.Stagione;
import it.unina.model.Utente;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Date;
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


    public void setStagioniMenu() {
        List<String> voci = List.of("Primavera", "Estate", "Autunno", "Inverno");
        for (String opzione : voci) {
            MenuItem item = new MenuItem(opzione);

            item.setOnAction(event -> {
                stagioneMenu.setText(opzione);
                System.out.println("Hai selezionato: " + opzione);
            });

            stagioneMenu.getItems().add(item);
        }
    }

    public void setLottiMenu() {
        List<Lotto> lotti = lottoDAO.getLottiDisponibili(utenteLoggato.getIdUtente());
        lottoMenu.setText("Seleziona lotto");

        for (Lotto opzione : lotti) {
            MenuItem item = new MenuItem();
            item.setText("Lotto numero : " + opzione.getIdLotto());

            item.setOnAction(event -> {
                lottoMenu.setText(String.valueOf(opzione.getIdLotto()));
                System.out.println("Hai selezionato: " + opzione);
            });

            lottoMenu.getItems().add(item);
        }
    }


    @FXML
    private void addProject() {
        String title = titleProject.getText();
        Date startDate = java.sql.Date.valueOf(dateInit.getValue());
        Date endDate = java.sql.Date.valueOf(dateFine.getValue());
        Stagione stagione = stagioneMenu.getText().equals("Primavera") ? Stagione.PRIMAVERA :
                          stagioneMenu.getText().equals("Estate") ? Stagione.ESTATE :
                          stagioneMenu.getText().equals("Autunno") ? Stagione.AUTUNNO :
                          Stagione.INVERNO;
        Lotto lotto = lottoMenu.getText().equals("Seleziona lotto") ? null :
                      lottoDAO.getLottoById(Integer.parseInt(lottoMenu.getText()));


        progettoAdd = new Progetto(title,stagione ,utenteLoggato,startDate, endDate,lotto);
        boolean progetto = progettoDAO.addProgettoAndUpdateLotto(progettoAdd,lotto);
        if (progetto) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Progetto Aggiunto");
            alert.setHeaderText(null);
            alert.setContentText("Il progetto è stato aggiunto con successo!");
            alert.showAndWait();
            // Chiude la finestra di dialogo dopo l'aggiunta del progetto
            addButtonProject.getScene().getWindow().hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText(null);
            alert.setContentText("Si è verificato un errore durante l'aggiunta del progetto.");
            alert.showAndWait();
        }


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

    public void setViewAddProject() {
        // Imposta il testo del pulsante di aggiunta progetto
        stagioneMenu.setText("Seleziona stagione");
        // Inizializza il menu a tendina delle stagioni
        setStagioniMenu();
        // Imposta il titolo del progetto
        titleProject.setPromptText("Inserisci il titolo del progetto");

        // Imposta le date iniziali e finali
        dateInit.setPromptText("Data inizio");
        dateFine.setPromptText("Data fine");
    }
}
