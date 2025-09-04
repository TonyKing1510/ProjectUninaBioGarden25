package it.unina.controller;

import it.unina.dao.AttivitaDAO;
import it.unina.dao.UtenteDAO;
import it.unina.implementazionepostgresql.AttivitaDAOImpl;
import it.unina.implementazionepostgresql.UtenteDAOImpl;
import it.unina.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static it.unina.model.StatoAttivita.fromString;

/**
 * Controller per la creazione di attività nel sistema.
 * Gestisce l'interfaccia JavaFX per la selezione di coltivatori, colture,
 * tipo e stato dell'attività, nonché date e quantità.
 *
 * @author entn
 */
public class CreateActivityController {

    @FXML
    private MenuButton coltivatoreMenu;
    @FXML
    private MenuButton colturaMenu;
    @FXML
    private MenuButton tipoAttivitaMenu;
    @FXML
    private MenuButton statoAttivitaMenu;
    @FXML
    private DatePicker dataInizio;
    @FXML
    private DatePicker dataFine;
    @FXML
    private TextField textQuantitaRaccolta;
    @FXML
    private TextField textQuantitaUsata;

    private Utente utenteLoggato;
    private List<CheckBox> coltureList = new ArrayList<>();
    private final UtenteDAO utenteDAO = new UtenteDAOImpl();
    private final AttivitaDAO attivitaDAO = new AttivitaDAOImpl();

    /**
     * Costruttore di default del controller.
     * Inizializza il controller senza parametri.
     *
     * @author entn
     */
    public CreateActivityController() {
        // Costruttore di default
    }

    /**
     * Metodo di inizializzazione chiamato automaticamente da JavaFX.
     * Popola i menu a tendina con coltivatori, colture, tipo e stato attività.
     *
     * @author entn
     */
    @FXML
    private void initialize() {
        setColtureMenu();
        setColtivatoriMenu();
        setTipoAttivitaMenu();
        setStatoAttivitaMenu();
    }

    /**
     * Imposta la lista di colture con checkbox.
     *
     * @param coltureList Lista di checkbox rappresentanti le colture disponibili
     * @author entn
     */
    public void setColtureList(List<CheckBox> coltureList) {
        this.coltureList = coltureList;
    }

    /**
     * Popola il menu dei coltivatori disponibili tramite il DAO.
     * Associa ad ogni voce un'azione che aggiorna il testo del MenuButton.
     *
     * @author entn
     */
    public void setColtivatoriMenu(){
        List<Utente> coltivatori = utenteDAO.getColtivatoriDisponibili();

        for(Utente coltivatore : coltivatori){
            MenuItem item = new MenuItem(String.valueOf(coltivatore.getIdUtente()));
            item.setOnAction(event -> coltivatoreMenu.setText(String.valueOf(coltivatore.getIdUtente())));
            coltivatoreMenu.getItems().add(item);
        }
    }

    /**
     * Popola il menu delle colture disponibili tramite la lista di checkbox.
     * Associa ad ogni voce un'azione che aggiorna il testo del MenuButton.
     *
     * @author entn
     */
    public void setColtureMenu(){
        for(CheckBox coltura : coltureList){
            MenuItem item = new MenuItem(coltura.getText());
            item.setOnAction(event -> colturaMenu.setText(coltura.getText()));
            colturaMenu.getItems().add(item);
        }
    }

    /**
     * Imposta l'utente attualmente loggato nel controller.
     *
     * @param utente Utente loggato nel sistema
     * @author entn
     */
    public void setUtenteLoggato(Utente utente) {
        this.utenteLoggato = utente;
    }

    /**
     * Popola il menu dei tipi di attività (IRRIGAZIONE, SEMINA, RACCOLTA).
     * Aggiorna il MenuButton con il tipo selezionato.
     *
     * @author entn
     */
    public void setTipoAttivitaMenu(){
        List<String> tipiAttivita = List.of(TipoAttivita.IRRIGAZIONE.toString(),TipoAttivita.SEMINA.toString(),TipoAttivita.RACCOLTA.toString());
        for(String tipo : tipiAttivita){
            MenuItem item = new MenuItem(tipo);
            item.setOnAction(event -> tipoAttivitaMenu.setText(tipo));
            tipoAttivitaMenu.getItems().add(item);
        }
    }

    /**
     * Popola il menu degli stati delle attività (PROGRAMMATA, IN_CORSO, COMPLETATA).
     * Aggiorna il MenuButton con lo stato selezionato.
     *
     * @author entn
     */
    public void setStatoAttivitaMenu(){
        List<String> statiAttivita = List.of(StatoAttivita.PROGRAMMATA.toString(),StatoAttivita.IN_CORSO.toString(), StatoAttivita.COMPLETATA.toString());
        for(String stato : statiAttivita){
            MenuItem item = new MenuItem(stato);
            item.setOnAction(event -> statoAttivitaMenu.setText(stato));
            statoAttivitaMenu.getItems().add(item);
        }
    }

    /**
     * Crea una nuova attività utilizzando i dati selezionati nei menu e nei campi di input.
     * Effettua l'inserimento nel database tramite il DAO e mostra un alert di conferma o errore.
     *
     * @author entn
     */
    @FXML
    public void addAttivita() {
        int idProprietario = utenteLoggato.getIdUtente();
        String coltivatoreSelezionato = coltivatoreMenu.getText();
        String colturaSelezionata = colturaMenu.getText().split(" ")[0];
        String tipoAttivitaSelezionata = tipoAttivitaMenu.getText();
        String statoAttivitaSelezionato = statoAttivitaMenu.getText();
        Date dataInizioAttivita = java.sql.Date.valueOf(dataInizio.getValue());
        Date dataFineAttivita = java.sql.Date.valueOf(dataFine.getValue());
        int quantitaRaccolta = Integer.parseInt(textQuantitaRaccolta.getText());
        int quantitaUsata = Integer.parseInt(textQuantitaUsata.getText());

        Attivita nuovaAttivita = new Attivita();
        nuovaAttivita.setDataFine(dataFineAttivita);
        nuovaAttivita.setDataInizio(dataInizioAttivita);
        nuovaAttivita.setQuantitaRaccolta(quantitaRaccolta);
        nuovaAttivita.setQuantitaUsata(quantitaUsata);

        // Determinazione dello stato
        StatoAttivita stato;
        if ("programmata".equalsIgnoreCase(statoAttivitaSelezionato)) {
            stato = StatoAttivita.PROGRAMMATA;
        } else if ("in corso".equalsIgnoreCase(statoAttivitaSelezionato)) {
            stato = StatoAttivita.IN_CORSO;
        } else {
            stato = StatoAttivita.COMPLETATA;
        }
        System.out.println("Stato selezionato: " + stato);
        nuovaAttivita.setStato(stato);

        System.out.println("Stato selezionato: " + stato);

        // Determinazione del tipo
        TipoAttivita tipo;
        if ("Irrigazione".equals(tipoAttivitaSelezionata)) {
            tipo = TipoAttivita.IRRIGAZIONE;
        } else if ("Semina".equals(tipoAttivitaSelezionata)) {
            tipo = TipoAttivita.SEMINA;
        } else {
            tipo = TipoAttivita.RACCOLTA;
        }
        nuovaAttivita.setTipo(tipo);



        boolean successo = attivitaDAO.addAttivita(nuovaAttivita, Integer.parseInt(colturaSelezionata), Integer.parseInt(coltivatoreSelezionato), idProprietario);
        Alert alert;
        if (successo) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successo");
            alert.setHeaderText(null);
            alert.setContentText("Attività aggiunta con successo!");
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText(null);
            alert.setContentText("Si è verificato un errore durante l'aggiunta dell'attività.");
        }
        alert.showAndWait();
    }

}
