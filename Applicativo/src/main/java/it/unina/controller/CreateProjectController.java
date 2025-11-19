package it.unina.controller;

import it.unina.dao.ColtureDAO;
import it.unina.dao.LottoDAO;
import it.unina.dao.ProgettoDAO;
import it.unina.gui.CreateActivityGUI;
import it.unina.implementazionepostgresql.ColtureDAOImpl;
import it.unina.implementazionepostgresql.LottoDAOImpl;
import it.unina.implementazionepostgresql.ProgettoDAOImpl;
import it.unina.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.*;

/**
 * Controller per la creazione di un nuovo progetto.
 * Gestisce le varie fasi della creazione:
 * informazioni generali, selezione lotti, selezione colture
 * e associazione coltivatori/attività.
 * Fornisce anche la funzionalità per salvare le associazioni
 * coltura-lotto e aprire la finestra per la creazione delle attività.
 *
 * @author entn
 */
public class CreateProjectController {

    /** Pane contenente le informazioni generali del progetto */
    @FXML
    private Pane infoProgettoPane;

    /** Pane per la selezione dei lotti */
    @FXML
    private Pane selectionLottoPane;

    /** Pane per la selezione delle colture */
    @FXML
    private Pane selectionColturePane;

    /** Pane per la selezione dei coltivatori e delle attività */
    @FXML
    private Pane coltivatoriAttivitaPane;

    /** Campo testo per il titolo del progetto */
    @FXML
    private TextField titleProject;

    /** DatePicker per la data di inizio del progetto */
    @FXML
    private DatePicker dateInit;

    /** DatePicker per la data di fine del progetto */
    @FXML
    private DatePicker dateFine;

    /** MenuButton per la selezione della stagione */
    @FXML
    private MenuButton stagioneMenu;

    /** VBox contenente le checkbox dei lotti disponibili */
    @FXML
    private VBox vBoxLotto;

    /** VBox contenente le checkbox delle colture */
    @FXML
    private VBox vBoxColture;

    /** Bottone per creare il progetto */
    @FXML
    private Button creaProgettoButton;

    @FXML
    private ScrollPane scrollPaneLotto;

    @FXML
    private AnchorPane anchorLottoPane;

    @FXML
    private ScrollPane scrollPaneColture;

    /** Bottone per associare le colture ai lotti**/
    @FXML
    private Button associaColtureButton;


    /** Lista delle checkbox delle colture selezionate */
    private List<CheckBox> coltureCheckBoxes = new ArrayList<>();

    /** Lista delle checkbox dei lotti selezionati */
    private List<CheckBox> lottiCheckBoxes = new ArrayList<>();

    /** Mappa per salvare le associazioni tra coltura e lotto */
    private Map<Colture, Integer> associazioniColturaLotto = new HashMap<>();

    /** Utente attualmente loggato */
    private Utente utenteLoggato;

    /** DAO per la gestione dei lotti */
    private final LottoDAO lottoDAO = new LottoDAOImpl();

    /** DAO per la gestione delle colture */
    private final ColtureDAO coltureDAO = new ColtureDAOImpl();

    /** DAO per la gestione dei progetti */
    private final ProgettoDAO progettoDAO = new ProgettoDAOImpl();

    /** Costanti per le stagioni e messaggi di alert */
    private static final String PRIMAVERA = "Primavera";
    private static final String ESTATE = "Estate";
    private static final String AUTUNNO = "Autunno";
    private static final String INVERNO = "Inverno";
    private static final String ATTENZIONE = "Attenzione";

    private boolean coltureAssociate = false;

    /**
     * Imposta l'utente attualmente loggato.
     * @param utente Utente loggato
     */
    public void setUtenteLoggato(Utente utente) {
        this.utenteLoggato = utente;
    }

    /**
     * Inizializza il controller impostando il menu delle stagioni.
     */
    public void initialize() {
        setStagioniMenu();
    }

    /**
     * Popola la VBox dei lotti disponibili con checkbox selezionabili.
     */
    public void setLottiMenu() {
        List<Lotto> lotti = lottoDAO.getLottiDisponibili(utenteLoggato.getIdUtente());
        vBoxLotto.getChildren().clear();
        lottiCheckBoxes.clear();
        System.out.println("Lotti disponibili: " + lotti.size());

        for (Lotto opzione : lotti) {
            CheckBox lottoCheckBox = new CheckBox(opzione.getIdLotto() + " - " + opzione.getNome());
            lottoCheckBox.setStyle(
                    "-fx-font-size: 15px;" +
                            "-fx-text-fill: #ffffff;" +
                            "-fx-padding: 5px;" +
                            "-fx-font-family: 'System';" +
                            "-fx-font-style: italic;"
            );
            lottoCheckBox.setOnAction(event -> {
                if (lottoCheckBox.isSelected()) {
                    lottiCheckBoxes.add(lottoCheckBox);
                } else {
                    lottiCheckBoxes.remove(lottoCheckBox);
                }
            });
            vBoxLotto.getChildren().add(lottoCheckBox);
            scrollPaneLotto.setVvalue(0);
            anchorLottoPane.setPrefHeight(vBoxLotto.getChildren().size() * 35 + 20);
        }
    }

    /**
     * Imposta il menu delle stagioni utilizzando il metodo statico del ProjectViewController.
     */
    public void setStagioniMenu() {
        ProjectViewController.stagioneMenu(stagioneMenu);
    }

    /**
     * Popola la VBox delle colture con checkbox e ComboBox per associare i lotti.
     * @param lottiSelezionati Lista delle checkbox dei lotti selezionati
     */
    public void setColtureMenu(List<CheckBox> lottiSelezionati) {
        List<Colture> coltureDisponibili = coltureDAO.getColtureDisponibili();
        vBoxColture.getChildren().clear(); // Pulisco la VBox prima di riempirla

        // Preparo la lista dei lotti selezionati
        List<String> lottiSelezionatiList = new ArrayList<>();
        for (CheckBox checkBox : lottiSelezionati) {
            if (checkBox.isSelected()) {
                lottiSelezionatiList.add(checkBox.getText());
            }
        }

        for (Colture coltura : coltureDisponibili) {
            CheckBox colturaCheckBox = new CheckBox(coltura.getIdColture() + " - " + coltura.getTitolo());
            colturaCheckBox.setStyle("""
                -fx-font-size: 15px;
                -fx-text-fill: #ffffff;
                -fx-padding: 5px;
                -fx-font-family: 'System';
                -fx-font-style: italic;
                """);

            // ComboBox per scegliere il lotto associato
            ComboBox<String> lottoComboBox = new ComboBox<>();
            lottoComboBox.getItems().addAll(lottiSelezionatiList);
            lottoComboBox.setDisable(true);
            lottoComboBox.setStyle("""
                -fx-background-color: white;
                -fx-text-fill: black;
                -fx-font-size: 14px;
                -fx-border-color: #cccccc;
                -fx-border-radius: 5px;
                -fx-background-radius: 5px;
                """);

            // Ripristino stato precedente se esiste un'associazione
            Integer lottoAssociato = associazioniColturaLotto.get(coltura);
            if (lottoAssociato != null) {
                colturaCheckBox.setSelected(true);
                lottoComboBox.setDisable(false);

                // Cerco la stringa del lotto con quell'ID
                String lottoText = lottiSelezionatiList.stream()
                        .filter(text -> text.startsWith(lottoAssociato + " -"))
                        .findFirst()
                        .orElse(null);
                lottoComboBox.setValue(lottoText);

                coltureCheckBoxes.add(colturaCheckBox);
            }

            // Listener per la CheckBox
            colturaCheckBox.setOnAction(event -> {
                if (colturaCheckBox.isSelected()) {
                    lottoComboBox.setDisable(false);
                    coltureCheckBoxes.add(colturaCheckBox);
                } else {
                    lottoComboBox.setDisable(true);
                    lottoComboBox.setValue(null);
                    coltureCheckBoxes.remove(colturaCheckBox);
                    associazioniColturaLotto.remove(coltura);
                }
            });

            //  Listener per la ComboBox
            lottoComboBox.setOnAction(event -> {
                String selectedText = lottoComboBox.getValue();
                if (selectedText != null && colturaCheckBox.isSelected()) {
                    try {
                        Integer lottoIdScelto = Integer.parseInt(selectedText.split(" - ")[0]);
                        associazioniColturaLotto.put(coltura, lottoIdScelto);
                    } catch (NumberFormatException e) {
                        System.err.println("Errore nel parsing dell'ID del lotto: " + selectedText);
                    }
                }
            });

            // Aggiungo tutto nella VBox
            HBox riga = new HBox(10, colturaCheckBox, lottoComboBox);
            vBoxColture.getChildren().add(riga);
        }

        scrollPaneColture.setFitToWidth(true);
        scrollPaneColture.setPannable(true);
        scrollPaneColture.setVvalue(0);
    }



    /**
     * Mostra il pane delle informazioni generali del progetto.
     */
    public void setVisibleInfoProgettoPane() {
        infoProgettoPane.setVisible(true);
        selectionLottoPane.setVisible(false);
        selectionColturePane.setVisible(false);
        coltivatoriAttivitaPane.setVisible(false);
    }

    /**
     * Mostra il pane di selezione dei lotti dopo aver verificato che tutti i campi obbligatori siano compilati.
     */
    public void setVisibleSelectionLottoPane() {
        boolean isTitleEmpty = titleProject.getText().isEmpty();
        boolean isStagioneEmpty = stagioneMenu.getText().equals(PRIMAVERA) ||
                stagioneMenu.getText().equals(ESTATE) ||
                stagioneMenu.getText().equals(AUTUNNO) ||
                stagioneMenu.getText().equals(INVERNO);
        boolean isDateInitEmpty = dateInit.getValue() == null;
        boolean isDateFineEmpty = dateFine.getValue() == null;

        if (isTitleEmpty || !isStagioneEmpty || isDateInitEmpty || isDateFineEmpty) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(ATTENZIONE);
            alert.setHeaderText("Compila i campi obbligatori");
            showAlert(alert);
        } else {
            infoProgettoPane.setVisible(false);
            coltivatoriAttivitaPane.setVisible(false);
            selectionColturePane.setVisible(false);
            selectionLottoPane.setVisible(true);
        }
    }

    /**
     * Mostra il pane di selezione delle colture dopo aver verificato i campi obbligatori.
     */
    public void setVisibleSelectionColturePane() {
        boolean isLottoSelected = lottiCheckBoxes.isEmpty();
        boolean isTitleEmpty = titleProject.getText().isEmpty();
        boolean isStagioneEmpty = stagioneMenu.getText().equals(PRIMAVERA) ||
                stagioneMenu.getText().equals(ESTATE) ||
                stagioneMenu.getText().equals(AUTUNNO) ||
                stagioneMenu.getText().equals(INVERNO);
        boolean isDateInitEmpty = dateInit.getValue() == null;
        boolean isDateFineEmpty = dateFine.getValue() == null;

        if (isLottoSelected || isTitleEmpty || !isStagioneEmpty || isDateInitEmpty || isDateFineEmpty) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(ATTENZIONE);
            alert.setHeaderText("Per procedere, compila i campi obbligatori.");
            showAlert(alert);
        } else {
            infoProgettoPane.setVisible(false);
            coltivatoriAttivitaPane.setVisible(false);
            selectionLottoPane.setVisible(false);
            selectionColturePane.setVisible(true);
            setColtureMenu(lottiCheckBoxes);
        }
    }

    /**
     * Mostra il pane dei coltivatori e attività dopo aver verificato la selezione delle colture.
     */
    public void setVisibleColtivatoriAttivitaPane() {
        boolean isAnyColturaSelected = coltureCheckBoxes.isEmpty();
        boolean isLottoSelected = lottiCheckBoxes.isEmpty();
        boolean isTitleEmpty = titleProject.getText().isEmpty();
        boolean isStagioneEmpty = stagioneMenu.getText().equals(PRIMAVERA) ||
                stagioneMenu.getText().equals(ESTATE) ||
                stagioneMenu.getText().equals(AUTUNNO) ||
                stagioneMenu.getText().equals(INVERNO);
        boolean isDateInitEmpty = dateInit.getValue() == null;
        boolean isDateFineEmpty = dateFine.getValue() == null;
        boolean isAssociationEmpty = associazioniColturaLotto.isEmpty();


        if ( isAssociationEmpty || isAnyColturaSelected || isLottoSelected || isTitleEmpty || !isStagioneEmpty || isDateInitEmpty || isDateFineEmpty || !coltureAssociate ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(ATTENZIONE);
            alert.setHeaderText("Seleziona almeno una coltura per procedere o associale.");
            showAlert(alert);
        } else {
            infoProgettoPane.setVisible(false);
            selectionLottoPane.setVisible(false);
            selectionColturePane.setVisible(false);
            coltivatoriAttivitaPane.setVisible(true);
        }
    }

    /**
     * Apre la finestra per la creazione delle attività associate alle colture selezionate.
     * @throws Exception Se si verifica un errore nell'apertura della GUI
     */
    @FXML
    public void openColtivatoriAttivita() throws Exception {
        CreateActivityGUI.openCreateActivity(utenteLoggato, coltureCheckBoxes);
    }

    /**
     * Mostra un alert con il messaggio predefinito per i campi obbligatori.
     * @param alert Alert da mostrare
     */
    private void showAlert(Alert alert) {
        alert.setContentText("Per procedere, compila i campi obbligatori.");
        alert.showAndWait();
    }

    /**
     * Aggiunge il progetto al database con i lotti selezionati.
     * Mostra un alert di conferma o errore.
     */
    @FXML
    public void addProgetto() {
        String title = titleProject.getText();
        if (controlloCampiStagioneDateMancanti()) return;

        Date startDate = Date.valueOf(dateInit.getValue());
        Date endDate = Date.valueOf(dateFine.getValue());
        Stagione stagione = Stagione.valueOf(stagioneMenu.getText().toUpperCase());


        if (controlloDateCoincidono(stagione)) return;

        boolean nessunaColtura = coltureCheckBoxes == null || coltureCheckBoxes.isEmpty() || coltureCheckBoxes.stream().noneMatch(CheckBox::isSelected);
        boolean nessunLotto = lottiCheckBoxes == null || lottiCheckBoxes.isEmpty() || lottiCheckBoxes.stream().noneMatch(CheckBox::isSelected);
        boolean nessunaAssociazione = associazioniColturaLotto == null || associazioniColturaLotto.isEmpty();

        if (controlloColtureLotto(nessunaColtura, nessunLotto, nessunaAssociazione)) return;


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

        Alert alert;
        if (progetto) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Progetto Aggiunto");
            alert.setHeaderText(null);
            alert.setContentText("Il progetto è stato aggiunto con successo!");
            alert.showAndWait();
            Stage stage = (Stage) creaProgettoButton.getScene().getWindow();
            stage.close();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Si è verificato un errore durante l'aggiunta del progetto.");
            alert.setContentText("Riprova più tardi.");
            alert.showAndWait();
        }
    }

    private static boolean controlloColtureLotto(boolean nessunaColtura, boolean nessunLotto, boolean nessunaAssociazione) {
        if (nessunaColtura || nessunLotto || nessunaAssociazione) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selezione Mancante");
            alert.setHeaderText("Colture o Lotti non selezionati");
            alert.setContentText("Assicurati di selezionare almeno una coltura, un lotto e di creare l'associazione coltura-lotto.");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    private boolean controlloDateCoincidono(Stagione stagione) {
        if (!isPeriodoNellaStagione(dateInit.getValue(), dateFine.getValue(), stagione)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Stagione Non Corrispondente");
            alert.setHeaderText("Le date non coincidono con la stagione selezionata.");
            alert.setContentText("Controlla che il periodo scelto rientri nella stagione di " + stagioneMenu.getText() + ".");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    private boolean controlloCampiStagioneDateMancanti() {
        if (dateInit.getValue() == null || dateFine.getValue() == null || stagioneMenu.getText() == null || stagioneMenu.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campi Mancanti");
            alert.setHeaderText(null);
            alert.setContentText("Compila tutti i campi: stagione, data di inizio e data di fine.");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    /**
     * Metodo di supporto per verificare se il periodo rientra nella stagione.
     */
    private boolean isPeriodoNellaStagione(LocalDate dataInizio, LocalDate dataFine, Stagione stagione) {
        return isDateInSeason(dataInizio, stagione) && isDateInSeason(dataFine, stagione);
    }

    /**
     * Controlla se una singola data rientra nella stagione.
     */
    private boolean isDateInSeason(LocalDate date, Stagione stagione) {
        MonthDay md = MonthDay.from(date);

        return switch (stagione) {
            case PRIMAVERA -> !md.isBefore(MonthDay.of(3, 21)) && !md.isAfter(MonthDay.of(6, 20));
            case ESTATE    -> !md.isBefore(MonthDay.of(6, 21)) && !md.isAfter(MonthDay.of(9, 22));
            case AUTUNNO   -> !md.isBefore(MonthDay.of(9, 23)) && !md.isAfter(MonthDay.of(12, 20));
            case INVERNO   -> md.isAfter(MonthDay.of(12, 20)) || md.isBefore(MonthDay.of(3, 21));
        };
    }


    /**
     * Salva le associazioni tra colture e lotti selezionati nel database.
     * Mostra un alert di conferma o errore.
     */
    @FXML
    private void salvaAssociazioni() {
        System.out.println(associazioniColturaLotto);
        boolean success = coltureDAO.salvaAssociazioni(associazioniColturaLotto);

        Alert alert;
        if (success) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successo");
            alert.setHeaderText("Associazioni salvate");
            alert.setContentText("Le associazioni coltura-lotto sono state salvate correttamente!");
            coltureAssociate = true;
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Salvataggio fallito");
            alert.setContentText("Si è verificato un errore durante il salvataggio delle associazioni.");
        }

        alert.showAndWait();
    }
}
