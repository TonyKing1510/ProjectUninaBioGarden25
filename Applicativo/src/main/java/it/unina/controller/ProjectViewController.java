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
    private Button cercaButton;
    @FXML
    private TextField superficieField;
    @FXML
    private MenuButton stagioneMenu;

    private Utente utenteLoggato;
    private final ProgettoDAO progettoDAO = new ProgettoDAOImpl();


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

    @FXML
    public void refreshProjects(){
        contentBox.getChildren().clear();
        Double superficieText = null;
        String superficieInput = superficieField.getText();
        if (superficieInput != null && !superficieInput.isEmpty()) {
            superficieText = Double.valueOf(superficieInput);
        }
        String stagioneSelezionata = stagioneMenu.getText();
        Stagione stagione = Stagione.valueOf(stagioneSelezionata.toUpperCase());
        System.out.println("stagione selezionata: " + stagione);
        List<Progetto> progetti = progettoDAO.getProgettiWithFilter(utenteLoggato.getIdUtente(),stagione,superficieText);
        for (Progetto progetto : progetti) {
            try {
                System.out.println("Caricamento progetto: " + progetto.getIdProgetto());
                VisualizeProjectGUI.initProjectCard(progetto, contentBox, utenteLoggato);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStagioniMenu() {
        stagioneMenu(stagioneMenu);
    }

    static void stagioneMenu(MenuButton stagioneMenu) {
        List<String> voci = List.of("Primavera", "Estate", "Autunno", "Inverno");
        stagioneMenu.setText("Seleziona Stagione");
        for (String opzione : voci) {
            MenuItem item = new MenuItem(opzione);

            item.setOnAction(event -> {
                stagioneMenu.setText(opzione);
                System.out.println("Hai selezionato: " + opzione);
            });

            stagioneMenu.getItems().add(item);
        }
    }


}
