package it.unina.controller;

import it.unina.dao.LottoDAO;
import it.unina.implementazionePostgreSQL.LottoDAOImpl;
import it.unina.model.Lotto;
import it.unina.model.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import javax.sound.midi.SysexMessage;
import java.util.List;

public class CreateProjectController {

    @FXML
    private Pane infoProgettoPane;
    @FXML
    private Pane selectionLottoPane;
    @FXML
    private Pane selectionColturePane;
    @FXML
    private Pane coltivatoriAttivitaPane;


    @FXML
    private TextField titleProject;
    @FXML
    private DatePicker dateInit;
    @FXML
    private DatePicker dateFine;
    @FXML
    private MenuButton stagioneMenu;
    @FXML
    private MenuButton lottoMenu;






    private Utente utenteLoggato;
    private final LottoDAO lottoDAO = new LottoDAOImpl();


    public void setUtenteLoggato(Utente utente) {
        this.utenteLoggato = utente;
    }

    public void initialize() {
        setStagioniMenu();
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

    public void setStagioniMenu() {
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

    public void setVisibleSelectionLottoPane() {
        boolean isTitleEmpty = titleProject.getText().isEmpty();
        boolean isStagioneEmpty = stagioneMenu.getText().equals("Primavera") ||
                                  stagioneMenu.getText().equals("Estate") ||
                                  stagioneMenu.getText().equals("Autunno") ||
                                  stagioneMenu.getText().equals("Inverno");
        boolean isDateInitEmpty = dateInit.getValue() == null;
        boolean isDateFineEmpty = dateFine.getValue() == null;

        if (isTitleEmpty || !isStagioneEmpty || isDateInitEmpty || isDateFineEmpty) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione");
            alert.setHeaderText("Compila i campi obbligatori");
            showAlert(alert);
        } else {
            infoProgettoPane.setVisible(false);
            selectionLottoPane.setVisible(true);
        }
    }

    private void showAlert(Alert alert) {
        alert.setContentText("Per procedere, compila i campi obbligatori.");
        alert.showAndWait();
    }

    public void setVisibleInfoProgettoPane() {
        infoProgettoPane.setVisible(true);
        selectionLottoPane.setVisible(false);
        selectionColturePane.setVisible(false);
        coltivatoriAttivitaPane.setVisible(false);
    }
}
