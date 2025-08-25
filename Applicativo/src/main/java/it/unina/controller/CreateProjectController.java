package it.unina.controller;

import it.unina.dao.ColtureDAO;
import it.unina.dao.LottoDAO;
import it.unina.dao.ProgettoDAO;
import it.unina.gui.CreateActivityGUI;
import it.unina.implementazionePostgreSQL.ColtureDAOImpl;
import it.unina.implementazionePostgreSQL.LottoDAOImpl;
import it.unina.implementazionePostgreSQL.ProgettoDAOImpl;
import it.unina.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private VBox vBoxLotto;


    @FXML
    private VBox vBoxColture;
    @FXML
    private Button creaProgettoButton;





    private List<CheckBox> coltureCheckBoxes = new ArrayList<>();
    private List<CheckBox> lottiCheckBoxes = new ArrayList<>();

    private Utente utenteLoggato;
    private final LottoDAO lottoDAO = new LottoDAOImpl();
    private final ColtureDAO coltureDAO = new ColtureDAOImpl();
    private final ProgettoDAO progettoDAO = new ProgettoDAOImpl();



    public void setUtenteLoggato(Utente utente) {
        this.utenteLoggato = utente;
    }

    public void initialize() {
        setStagioniMenu();
    }

    public void setLottiMenu() {
        List<Lotto> lotti = lottoDAO.getLottiDisponibili(utenteLoggato.getIdUtente());

        vBoxLotto.getChildren().clear(); // pulisco la VBox
        lottiCheckBoxes.clear(); // pulisco la lista

        for (Lotto opzione : lotti) {
            CheckBox lottoCheckBox = new CheckBox(opzione.getIdLotto() + " - " + opzione.getNome());

            // listener per aggiornare la lista quando selezioni/deselezioni
            lottoCheckBox.setOnAction(event -> {
                if (lottoCheckBox.isSelected()) {
                    lottiCheckBoxes.add(lottoCheckBox);
                    System.out.println("Lotto selezionato: " + opzione.getNome());
                } else {
                    lottiCheckBoxes.remove(lottoCheckBox);
                    System.out.println("Lotto deselezionato: " + opzione.getNome());
                }
            });

            lottoCheckBox.setStyle(
                    "-fx-font-size: 14px; " +
                            "-fx-text-fill: #2c3e50; " +
                            "-fx-padding: 5px;"
            );

            vBoxLotto.getChildren().add(lottoCheckBox);
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



    // Dichiarazione HashMap
    private Map<Colture, Integer> associazioniColturaLotto = new HashMap<>();

    public void setColtureMenu(List<CheckBox> lottiSelezionati) {
        List<Colture> coltureDisponibili = coltureDAO.getColtureDisponibili();
        vBoxColture.getChildren().clear(); // pulisco prima la VBox
        System.out.println("Lotti selezionati: " + lottiSelezionati.size());

        for (Colture coltura : coltureDisponibili) {
            // Checkbox per la coltura
            CheckBox colturaCheckBox = new CheckBox(coltura.getIdColture() + " - " + coltura.getTitolo());
            colturaCheckBox.setStyle(
                    "-fx-font-size: 14px; " +
                            "-fx-text-fill: #2c3e50; " +
                            "-fx-padding: 5px;"
            );

            // Recupero SOLO gli id dei lotti selezionati
            List<Integer> lottiSelezionatiList = new ArrayList<>();
            for (CheckBox checkBox : lottiSelezionati) {
                if (checkBox.isSelected()) {
                    String[] parts = checkBox.getText().split(" - ");
                    int idLotto = Integer.parseInt(parts[0]);
                    lottiSelezionatiList.add(idLotto);
                }
            }

            // ComboBox con SOLO gli ID
            ComboBox<Integer> lottoComboBox = new ComboBox<>();
            lottoComboBox.getItems().addAll(lottiSelezionatiList);
            lottoComboBox.setDisable(true);

            // Azione sulla selezione della coltura
            colturaCheckBox.setOnAction(event -> {
                if (colturaCheckBox.isSelected()) {
                    coltureCheckBoxes.add(colturaCheckBox);
                    lottoComboBox.setDisable(false);
                    System.out.println("Coltura selezionata: " + coltura.getTitolo());
                } else {
                    lottoComboBox.setDisable(true);
                    lottoComboBox.setValue(null);
                    associazioniColturaLotto.remove(coltura);
                    System.out.println("Coltura deselezionata: " + coltura.getTitolo());
                }
            });

            // Azione sulla scelta del lotto
            lottoComboBox.setOnAction(event -> {
                Integer lottoIdScelto = lottoComboBox.getValue();
                if (lottoIdScelto != null) {
                    associazioniColturaLotto.put(coltura, lottoIdScelto);
                    System.out.println("Associata coltura " + coltura.getTitolo() +
                            " al lotto con ID " + lottoIdScelto);
                }
            });

            // Aggiungo CheckBox e ComboBox in riga
            HBox riga = new HBox(10, colturaCheckBox, lottoComboBox);
            vBoxColture.getChildren().add(riga);
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
            coltivatoriAttivitaPane.setVisible(false);
            selectionColturePane.setVisible(false);
            selectionLottoPane.setVisible(true);
        }
    }

    public void setVisibleSelectionColturePane() {
        boolean isLottoSelected = lottiCheckBoxes.isEmpty();
        boolean isTitleEmpty = titleProject.getText().isEmpty();
        boolean isStagioneEmpty = stagioneMenu.getText().equals("Primavera") ||
                stagioneMenu.getText().equals("Estate") ||
                stagioneMenu.getText().equals("Autunno") ||
                stagioneMenu.getText().equals("Inverno");
        boolean isDateInitEmpty = dateInit.getValue() == null;
        boolean isDateFineEmpty = dateFine.getValue() == null;

        if ( isLottoSelected || isTitleEmpty || !isStagioneEmpty || isDateInitEmpty || isDateFineEmpty) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione");
            alert.setHeaderText("Per procedere, compila i campi obbligatori.");
            showAlert(alert);
        } else {
            infoProgettoPane.setVisible(false);
            coltivatoriAttivitaPane.setVisible(false);
            selectionLottoPane.setVisible(false);
            selectionColturePane.setVisible(true);
            setColtureMenu(lottiCheckBoxes);}
    }

    public void setVisibleColtivatoriAttivitaPane() {
        boolean isAnyColturaSelected = coltureCheckBoxes.isEmpty();
        boolean isLottoSelected = lottiCheckBoxes.isEmpty();
        boolean isTitleEmpty = titleProject.getText().isEmpty();
        boolean isStagioneEmpty = stagioneMenu.getText().equals("Primavera") ||
                stagioneMenu.getText().equals("Estate") ||
                stagioneMenu.getText().equals("Autunno") ||
                stagioneMenu.getText().equals("Inverno");
        boolean isDateInitEmpty = dateInit.getValue() == null;
        boolean isDateFineEmpty = dateFine.getValue() == null;

        if (isAnyColturaSelected|| isLottoSelected || isTitleEmpty || !isStagioneEmpty || isDateInitEmpty || isDateFineEmpty) {
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

    @FXML
    public void addProgetto(){
        String title = titleProject.getText();
        Date startDate = java.sql.Date.valueOf(dateInit.getValue());
        Date endDate = java.sql.Date.valueOf(dateFine.getValue());
        Stagione stagione = Stagione.valueOf(stagioneMenu.getText().toUpperCase());
        List<Integer> idLottiSelezionati = new ArrayList<>();
        for (CheckBox checkBox : lottiCheckBoxes) {
            if (checkBox.isSelected()) {
                String[] parts = checkBox.getText().split(" - ");
                int idLotto = Integer.parseInt(parts[0]);
                idLottiSelezionati.add(idLotto);
            }
        }
        List<Lotto> lottiSelezionati = new ArrayList<>();
        for (int idLotto : idLottiSelezionati) {
            Lotto lotto = lottoDAO.getLottoById(idLotto);
            lottiSelezionati.add(lotto);
        }


        Progetto progettoAdd = new Progetto();
        progettoAdd.setTitolo(title);
        progettoAdd.setDataInizio(startDate);
        progettoAdd.setDataFine(endDate);
        progettoAdd.setStagione(stagione);
        progettoAdd.setCreatore(utenteLoggato);
        progettoAdd.setLottiOspitati(lottiSelezionati);
        boolean progetto = progettoDAO.addProgettoAndUpdateLotto(progettoAdd, lottiSelezionati);
        if (progetto) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Progetto Aggiunto");
            alert.setHeaderText(null);
            alert.setContentText("Il progetto è stato aggiunto con successo!");
            alert.showAndWait();
            // Chiudo solo la finestra corrente
            Stage stage = (Stage) creaProgettoButton.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Si è verificato un errore durante l'aggiunta del progetto.");
            alert.setContentText("Riprova più tardi.");
            alert.showAndWait();
        }
    }

    @FXML
    private void salvaAssociazioni() {
        boolean success = coltureDAO.salvaAssociazioni(associazioniColturaLotto);

        Alert alert;
        if (success) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successo");
            alert.setHeaderText("Associazioni salvate");
            alert.setContentText("Le associazioni coltura-lotto sono state salvate correttamente!");
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Salvataggio fallito");
            alert.setContentText("Si è verificato un errore durante il salvataggio delle associazioni.");
        }

        alert.showAndWait();
    }


}
