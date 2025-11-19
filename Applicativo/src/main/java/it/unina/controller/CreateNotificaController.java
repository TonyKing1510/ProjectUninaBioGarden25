package it.unina.controller;

import it.unina.dao.UtenteDAO;
import it.unina.dao.ProgettoDAO;
import it.unina.dao.NotificaDAO;
import it.unina.implementazionepostgresql.NotificaDAOImpl;
import it.unina.implementazionepostgresql.ProgettoDAOImpl;
import it.unina.implementazionepostgresql.UtenteDAOImpl;
import it.unina.model.Notifica;
import it.unina.model.Utente;
import it.unina.model.Progetto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.List;

public class CreateNotificaController {

    public Utente getProprietarioCorrente() {
        return proprietarioCorrente;
    }

    public void setProprietarioCorrente(Utente proprietarioCorrente) {
        this.proprietarioCorrente = proprietarioCorrente;
    }

    private Utente proprietarioCorrente;// l'utente loggato

    private ProgettoDAO progettoDAO = new ProgettoDAOImpl();
    private UtenteDAO coltivatoreDAO = new UtenteDAOImpl();
    private NotificaDAO notificaDAO = new NotificaDAOImpl();

    private Progetto progettoSelezionato;
    private Utente coltivatoreSelezionato; // null se "Tutti"

    public CreateNotificaController(Utente proprietario, ProgettoDAO progettoDAO,
                                    UtenteDAO coltivatoreDAO, NotificaDAO notificaDAO) {
        this.proprietarioCorrente = proprietario;
        this.progettoDAO = progettoDAO;
        this.coltivatoreDAO = coltivatoreDAO;
        this.notificaDAO = notificaDAO;
    }

    @FXML
    private TextField textTitoloNotifica;

    @FXML
    private TextField textDescrizione;

    @FXML
    private TextField textGiorniScadenza;

    @FXML
    private TextField textTipoNotifica;

    @FXML
    private MenuButton progettoMenu;

    @FXML
    private MenuButton coltivatoriMenu;

    @FXML
    private Button creaNotifica;

    @FXML
    private Button caricaColtivatori;


    public CreateNotificaController() {
    }

    @FXML
    public void initialize() {

    }

    /**
     * Carica i progetti del proprietario e popola il MenuButton
     */
    public void caricaProgetti() {
        List<Progetto> progetti = progettoDAO.getProgettiByIdUtente(proprietarioCorrente.getIdUtente());
        progettoMenu.getItems().clear();

        for (Progetto p : progetti) {
            MenuItem item = new MenuItem(p.getTitolo());
            item.setOnAction(e -> {
                progettoSelezionato = p;
                progettoMenu.setText(p.getTitolo());
            });
            progettoMenu.getItems().add(item);
        }
    }


    /**
     * Carica i coltivatori associati a un progetto e popola il MenuButton
     */
    private void caricaColtivatori(int idProgetto) {
        coltivatoriMenu.getItems().clear();
        // Prima voce: Tutti i coltivatori
        MenuItem tutti = new MenuItem("Tutti i coltivatori");
        tutti.setOnAction(e -> {
            coltivatoreSelezionato = null;
            coltivatoriMenu.setText("Tutti i coltivatori");
            // Chiude il menu subito dopo la selezione
            Platform.runLater(() -> coltivatoriMenu.hide());
        });
        coltivatoriMenu.getItems().add(tutti);

        // Voci singoli coltivatori
        List<Utente> coltivatori = coltivatoreDAO.getColtivatoriByIdProgetto(idProgetto);
        for (Utente c : coltivatori) {
            MenuItem item = new MenuItem(c.getNome() + " " + c.getCognome());
            item.setOnAction(e -> {
                coltivatoreSelezionato = c;
                coltivatoriMenu.setText(c.getNome() + " " + c.getCognome());
                Platform.runLater(() -> coltivatoriMenu.hide());
            });
            coltivatoriMenu.getItems().add(item);
        }

        coltivatoriMenu.setDisable(false); // abilita ora il menu
    }


    @FXML
    public void onCaricaColtivatori() {
        if (progettoSelezionato == null) {
            showAlert("Errore", "Seleziona un progetto prima di creare la notifica.");
            return;
        }
        caricaColtivatori(progettoSelezionato.getIdProgetto());
    }


    /**
     * Gestisce il click sul pulsante "Crea Notifica"
     **/
    @FXML
    private void onCreaNotifica() {
        if (progettoSelezionato == null) {
            showAlert("Errore", "Seleziona un progetto prima di creare la notifica.");
            return;
        }
        String titolo = textTitoloNotifica.getText().trim();
        String descrizione = textDescrizione.getText().trim();
        String tipo = textTipoNotifica.getText().trim();
        int giorni = 0;
        try {
            giorni = Integer.parseInt(textGiorniScadenza.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Errore", "Giorni di scadenza non valido.");
            return;
        }
        if(coltivatoriMenu.getText().equals("Tutti i coltivatori")){
            coltivatoreSelezionato = null;
        }

       Notifica n = new Notifica();
        n.setProgetto(progettoSelezionato);
        n.setDescrizione(descrizione);
        n.setGiorniScadenza(giorni);
        n.setTipo(tipo);
        n.setTitolo(titolo);
        n.setOwner(proprietarioCorrente);
        if (coltivatoreSelezionato != null) {
            n.setDestinatario(coltivatoreSelezionato);
        }else{
            n.setTutti(true);
        }
        boolean successo = notificaDAO.inserisciNotifica(n);
        if (successo) {
            showAlert("Successo", "Notifica creata correttamente!");
            resetForm();
        } else {
            showAlert("Errore", "Errore durante la creazione della notifica.");
        }
    }

    /** Mostra alert semplice */
    private void showAlert(String titolo, String messaggio) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    /** Resetta i campi della form */
    private void resetForm() {
        textTitoloNotifica.clear();
        textDescrizione.clear();
        textGiorniScadenza.clear();
        textTipoNotifica.clear();
        progettoMenu.setText("Seleziona progetto");
        coltivatoriMenu.setText("Seleziona coltivatore");
        coltivatoriMenu.setDisable(true);
        progettoSelezionato = null;
        coltivatoreSelezionato = null;
    }
}
