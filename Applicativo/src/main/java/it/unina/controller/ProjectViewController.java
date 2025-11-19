package it.unina.controller;

import it.unina.dao.ProgettoDAO;
import it.unina.gui.VisualizeProjectGUI;
import it.unina.implementazionepostgresql.ProgettoDAOImpl;
import it.unina.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Controller per la gestione della sezione Progetti dell'applicazione.
 * Permette di visualizzare i progetti dell'utente loggato, filtrare per superficie e stagione
 * e aprire le schede di dettaglio dei singoli progetti.
 * <p>
 * Si occupa inoltre di inizializzare il menu stagioni e aggiornare la visualizzazione
 * dei progetti quando necessario.
 * </p>
 *
 * @author entn, Sderr12
 */
public class ProjectViewController {

    /** Contenitore principale dove vengono aggiunte le card dei progetti */
    @FXML
    private VBox contentBox;

    /** Campo di input per filtrare i progetti in base alla superficie */
    @FXML
    private TextField superficieField;

    /** Menu per selezionare la stagione e filtrare i progetti */
    @FXML
    private MenuButton stagioneMenu;

    /** Utente attualmente loggato nell'applicazione */
    private Utente utenteLoggato;

    /** DAO per la gestione dei progetti nel database */
    private final ProgettoDAO progettoDAO = new ProgettoDAOImpl();


    /**
     * Imposta l'utente loggato per il controller.
     *
     * @param utente Utente attualmente loggato
     * @author entn, Sderr12
     */
    public void setUtenteLoggatoLoadProject(Utente utente) {
        System.out.println("setUtenteLoggato called. utente=" + (utente==null? "null": utente.getIdUtente()));

        this.utenteLoggato = utente;
        Platform.runLater(this::loadProjects);
    }

    /**
     * Metodo di inizializzazione del controller.
     * Viene eseguito automaticamente da JavaFX dopo il caricamento della view.
     * Carica i progetti dell'utente loggato nella GUI.
     * @author entn
     */
    @FXML
    private void initialize() {
        System.out.println("ProjectViewController.initialize()");
        setStagioniMenu();
    }

    /**
     * Carica i progetti dell'utente loggato e li aggiunge al contentBox.
     * Utilizza la GUI di VisualizeProjectGUI per creare le card.
     * @author entn
     */
    public void loadProjects() {
        System.out.println("loadProjects called. children before clear: " + contentBox.getChildren().size());

        contentBox.getChildren().clear();
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

    /**
     * Aggiorna la lista dei progetti visualizzati in base ai filtri
     * impostati dall'utente (stagione e superficie).
     * Pulisce il contentBox e ricostruisce le card dei progetti filtrati.
     * @author entn
     */
    @FXML
    public void refreshProjects() {
        contentBox.getChildren().clear();

        Double superficieText = null;
        String superficieInput = superficieField.getText();
        if (superficieInput != null && !superficieInput.isEmpty()) {
            superficieText = Double.valueOf(superficieInput);
        }

        String stagioneSelezionata = stagioneMenu.getText();
        Stagione stagione = Stagione.valueOf(stagioneSelezionata.toUpperCase());

        List<Progetto> progetti = progettoDAO.getProgettiWithFilter(
                utenteLoggato.getIdUtente(),
                stagione,
                superficieText
        );

        contentBox.getChildren().clear();

        if (progetti.isEmpty()) {

            Label emptyLabel = new Label("Nessun progetto trovato con questi filtri");
            emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: red;");

            VBox emptyBox = new VBox(emptyLabel);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(30));

            contentBox.getChildren().add(emptyBox);
            return;
        }

        
        for (Progetto progetto : progetti) {
            try {
                VisualizeProjectGUI.initProjectCard(progetto, contentBox, utenteLoggato);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





    /**
     * Inizializza il menu delle stagioni con le opzioni disponibili.
     * @author entn, Sderr12
     */
    public void setStagioniMenu() {
        stagioneMenu(stagioneMenu);
    }

    /**
     * Metodo statico che popola un MenuButton con le stagioni disponibili.
     * Imposta inoltre il comportamento al click su ciascuna voce.
     *
     * @param stagioneMenu MenuButton da popolare
     * @author entn, Sderr12
     */
    static void stagioneMenu(MenuButton stagioneMenu) {
        List<String> voci = List.of("Primavera", "Estate", "Autunno", "Inverno");
        stagioneMenu.setText("Seleziona Stagione");
        for (String opzione : voci) {
            MenuItem item = new MenuItem(opzione);
            item.setOnAction(event -> stagioneMenu.setText(opzione));
            stagioneMenu.getItems().add(item);
        }
    }
}
