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

    public CreateActivityController() {
    }

    public CreateActivityController(Utente utenteLoggato, List<CheckBox> coltureList) {
        this.utenteLoggato = utenteLoggato;
        this.coltureList = coltureList;
    }
    public CreateActivityController(List<CheckBox> coltureList) {
        this.coltureList = coltureList;
        this.utenteLoggato = null;
    }




    @FXML
    private void initialize() {
        setColtureMenu();
        setColtivatoriMenu();
        setTipoAttivitaMenu();
        setStatoAttivitaMenu();
    }
    
    public void setColtureList(List<CheckBox> coltureList) {
        this.coltureList = coltureList;
    }

    public void setColtivatoriMenu(){
        List<Utente> coltivatori = utenteDAO.getColtivatoriDisponibili();
        System.out.println("Coltivatori disponibili: " + coltivatori.size());
        for(Utente coltivatore : coltivatori){
            MenuItem item = new MenuItem(String.valueOf(coltivatore.getIdUtente()));
            item.setOnAction(event -> {
                coltivatoreMenu.setText(String.valueOf(coltivatore.getIdUtente()));
                System.out.println("Hai selezionato: " + coltivatore);
            });
            coltivatoreMenu.getItems().add(item);
        }
    }

    public void setColtureMenu(){
        System.out.println("Colture disponibili: " + coltureList.size());
        for(CheckBox coltura : coltureList){
            MenuItem item = new MenuItem(coltura.getText());
            item.setOnAction(event -> {
                colturaMenu.setText(coltura.getText());
                System.out.println("Hai selezionato: " + coltura);
            });
            colturaMenu.getItems().add(item);
        }
    }


    public void setUtenteLoggato(Utente utente) {
        this.utenteLoggato = utente;
    }

    public void setColtureCheckBox(List<CheckBox> coltureList) {
        this.coltureList = coltureList;
    }

    public void setTipoAttivitaMenu(){
        List<String> tipiAttivita = List.of(TipoAttivita.IRRIGAZIONE.toString(),TipoAttivita.SEMINA.toString(),TipoAttivita.RACCOLTA.toString());
        for(String tipo : tipiAttivita){
            MenuItem item = new MenuItem(tipo);
            item.setOnAction(event -> {
                tipoAttivitaMenu.setText(tipo);
                System.out.println("Hai selezionato: " + tipo);
            });
            tipoAttivitaMenu.getItems().add(item);
        }
    }

    public void setStatoAttivitaMenu(){
        List<String> statiAttivita = List.of(StatoAttivita.PROGRAMMATA.toString(),StatoAttivita.IN_CORSO.toString(), StatoAttivita.COMPLETATA.toString());
        for(String stato : statiAttivita){
            MenuItem item = new MenuItem(stato);
            item.setOnAction(event -> {
                statoAttivitaMenu.setText(stato);
                System.out.println("Hai selezionato: " + stato);
            });
            statoAttivitaMenu.getItems().add(item);
        }
    }

    @FXML
    public void addAttivita() {
        int id_proprietario = utenteLoggato.getIdUtente(); // ho id_proprietario
        String coltivatoreSelezionato = coltivatoreMenu.getText(); // ho id_coltivatore
        String colturaSelezionata = colturaMenu.getText().substring(0, 1);
        String tipoAttivitaSelezionata = tipoAttivitaMenu.getText();
        String statoAttivitaSelezionato = statoAttivitaMenu.getText();
        Date dataInizioAttivita = java.sql.Date.valueOf(dataInizio.getValue());
        Date dataFineAttivita = java.sql.Date.valueOf(dataFine.getValue());
        int quantitaRaccolta = Integer.parseInt(textQuantitaRaccolta.getText());
        int quantitaUsata = Integer.parseInt(textQuantitaUsata.getText());
        System.out.println("Coltura selezionata: " + Integer.parseInt(colturaSelezionata));

        Attivita nuovaAttivita = new Attivita();
        nuovaAttivita.setDataFine(dataFineAttivita);
        nuovaAttivita.setDataInizio(dataInizioAttivita);
        nuovaAttivita.setQuantitaRaccolta(quantitaRaccolta);
        nuovaAttivita.setQuantitaUsata(quantitaUsata);
        StatoAttivita stato = statoAttivitaSelezionato.equals("PROGRAMMATA") ? StatoAttivita.PROGRAMMATA :
                statoAttivitaSelezionato.equals("IN_CORSO") ? StatoAttivita.IN_CORSO :
                        StatoAttivita.COMPLETATA;
        nuovaAttivita.setStato(stato);
        TipoAttivita tipo = tipoAttivitaSelezionata.equals("IRRIGAZIONE") ? TipoAttivita.IRRIGAZIONE :
                tipoAttivitaSelezionata.equals("SEMINA") ? TipoAttivita.SEMINA :
                        TipoAttivita.RACCOLTA;
        nuovaAttivita.setTipo(tipo);
        System.out.println("Nuova attivita creata: " + nuovaAttivita);
        boolean successo = attivitaDAO.addAttivita(nuovaAttivita, Integer.parseInt(colturaSelezionata),Integer.parseInt(coltivatoreSelezionato),id_proprietario);
        if (successo) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successo");
            alert.setHeaderText(null);
            alert.setContentText("Attività aggiunta con successo!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText(null);
            alert.setContentText("Si è verificato un errore durante l'aggiunta dell'attività.");
            alert.showAndWait();
        }


    }


}
