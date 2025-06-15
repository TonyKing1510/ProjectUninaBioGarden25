package it.unina.controller;

import it.unina.controller.components.ProjectCardController;
import it.unina.dao.ProgettoDAO;
import it.unina.gui.ProjectGUI;
import it.unina.implementazionePostgreSQL.ProgettoDAOImpl;
import it.unina.model.Progetto;
import it.unina.model.Stagione;
import it.unina.model.Utente;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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

    private Utente utenteLoggato;
    private Progetto progettoAdd;
    private final ProgettoDAO progettoDAO = new ProgettoDAOImpl();

    public void setUtenteLoggato(Utente utente) {
        this.utenteLoggato = utente;
    }

    @FXML
    private void initialize() {

        Platform.runLater(() -> {
            // Carica i progetti dell'utente loggato
            loadProjects();
            // Imposta il testo del pulsante di aggiunta progetto
            stagioneMenu.setText("Seleziona stagione");
            // Inizializza il menu a tendina delle stagioni
            setStagioniMenu();
            // Imposta il titolo del progetto
            titleProject.setPromptText("Inserisci il titolo del progetto");

            // Imposta le date iniziali e finali
            dateInit.setPromptText("Data inizio");
            dateFine.setPromptText("Data fine");
        });



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


    /**
     * Apre un dialogo per l'aggiunta di un nuovo progetto.
     * @author entn
     */
    @FXML
    public void openAddProjectDialog() throws IOException {
        ProjectGUI.openAddProjectView(addButton.getScene().getWindow(), utenteLoggato);
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

        // Logica per aggiungere il progetto
        // Ad esempio, chiamare un metodo DAO per salvare il progetto nel database
        System.out.println(utenteLoggato.getRuolo());

        progettoAdd = new Progetto(title,stagione ,utenteLoggato,startDate, endDate);
        boolean progetto = progettoDAO.addProgetto(progettoAdd);
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/components/ProjectCard.fxml"));
                AnchorPane card = loader.load();

                // Ottieni il controller della card e setta i dati
                ProjectCardController controller = loader.getController();
                controller.setProgetto(progetto); // Metodo che scriverai tu

                contentBox.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
