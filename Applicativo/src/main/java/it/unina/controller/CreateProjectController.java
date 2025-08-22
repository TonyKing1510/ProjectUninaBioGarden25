package it.unina.controller;

import it.unina.dao.ColtureDAO;
import it.unina.dao.LottoDAO;
import it.unina.gui.CreateActivityGUI;
import it.unina.implementazionePostgreSQL.ColtureDAOImpl;
import it.unina.implementazionePostgreSQL.LottoDAOImpl;
import it.unina.model.Colture;
import it.unina.model.Lotto;
import it.unina.model.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
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


    @FXML
    private VBox vBoxColture;





    private List<CheckBox> coltureCheckBoxes = new ArrayList<>();
    private Utente utenteLoggato;
    private final LottoDAO lottoDAO = new LottoDAOImpl();
    private final ColtureDAO coltureDAO = new ColtureDAOImpl();


    public void setUtenteLoggato(Utente utente) {
        this.utenteLoggato = utente;
    }

    public void initialize() {
        setStagioniMenu();
        setColtureMenu();
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



    public void setColtureMenu(){
        List<Colture> coltureDisponibili = coltureDAO.getColtureDisponibili();
        for (Colture coltura : coltureDisponibili) {
            CheckBox colturaCheckBox = new CheckBox(coltura.getIdColture() + " - " + coltura.getTitolo());
            vBoxColture.getChildren().add(colturaCheckBox);
            coltureCheckBoxes.add(colturaCheckBox);
            colturaCheckBox.setOnAction(event -> {
                if (colturaCheckBox.isSelected()) {
                    System.out.println("Coltura selezionata: " + coltura.getTitolo());
                } else {
                    System.out.println("Coltura deselezionata: " + coltura.getTitolo());
                }
            });
            colturaCheckBox.setStyle(
                    "-fx-font-size: 14px; " +
                            "-fx-text-fill: #2c3e50; " +
                            "-fx-padding: 5px;"
            );
        }
    }

    public void setVisibleInfoProgettoPane() {
        infoProgettoPane.setVisible(true);
        selectionLottoPane.setVisible(false);
        selectionColturePane.setVisible(false);
        coltivatoriAttivitaPane.setVisible(false);
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

    public void setVisibleSelectionColturePane() {
        boolean isLottoSelected = !lottoMenu.getText().equals("Seleziona lotto");
        boolean isTitleEmpty = titleProject.getText().isEmpty();
        boolean isStagioneEmpty = !(stagioneMenu.getText().equals("Primavera") ||
                stagioneMenu.getText().equals("Estate") ||
                stagioneMenu.getText().equals("Autunno") ||
                stagioneMenu.getText().equals("Inverno"));
        boolean isDateInitEmpty = dateInit.getValue() == null;
        boolean isDateFineEmpty = dateFine.getValue() == null;

        System.out.println("isLottoSelected: " + isLottoSelected);
        System.out.println("ciao" + lottoMenu.getText());bb



        if (isLottoSelected || isTitleEmpty || !isStagioneEmpty || isDateInitEmpty || isDateFineEmpty) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione");
            alert.setHeaderText("Per procedere, compila i campi obbligatori.");
            showAlert(alert);

        } else {
            infoProgettoPane.setVisible(false);
            coltivatoriAttivitaPane.setVisible(false);
            selectionLottoPane.setVisible(false);
            selectionColturePane.setVisible(true);
        }
    }

    public void setVisibleColtivatoriAttivitaPane() {
        boolean isAnyColturaSelected = coltureCheckBoxes.stream().noneMatch(CheckBox::isSelected);
        boolean isLottoSelected = !lottoMenu.getText().equals("Seleziona lotto");
        boolean isTitleEmpty = titleProject.getText().isEmpty();
        boolean isStagioneEmpty = !(stagioneMenu.getText().equals("Primavera") ||
                stagioneMenu.getText().equals("Estate") ||
                stagioneMenu.getText().equals("Autunno") ||
                stagioneMenu.getText().equals("Inverno"));
        boolean isDateInitEmpty = dateInit.getValue() == null;
        boolean isDateFineEmpty = dateFine.getValue() == null;

        if (isAnyColturaSelected || isLottoSelected || isTitleEmpty || !isStagioneEmpty || isDateInitEmpty || isDateFineEmpty) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione");
            alert.setHeaderText("Seleziona almeno una coltura per procedere.");
            showAlert(alert);
        } else {
            infoProgettoPane.setVisible(false);
            selectionLottoPane.setVisible(false);
            selectionColturePane.setVisible(false);
            coltivatoriAttivitaPane.setVisible(true);
        }
    }

    @FXML
    public void openColtivatoriAttivita() throws Exception {
        CreateActivityGUI.openCreateActivity(utenteLoggato, coltureCheckBoxes);
    }


    private void showAlert(Alert alert) {
        alert.setContentText("Per procedere, compila i campi obbligatori.");
        alert.showAndWait();
    }


}
