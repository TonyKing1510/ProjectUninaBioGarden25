package it.unina.controller;

import it.unina.dao.NotificaDAO;
import it.unina.gui.CreateNotificaGUI;
import it.unina.implementazionepostgresql.NotificaDAOImpl;
import it.unina.model.Notifica;
import it.unina.model.Ruolo;
import it.unina.model.Utente;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller per la gestione della sezione Notifiche dell'applicazione.
 * Permette di visualizzare le notifiche dell'utente loggato e gestirle.
 * <p>
 * Si occupa di caricare le notifiche dal database e visualizzarle come card
 * nel contentBox.
 * </p>
 *
 * @author Sderr12
 */
public class NotificheViewController {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /** Contenitore principale dove vengono aggiunte le card delle notifiche */
    @FXML
    private VBox contentBox;

    /** ScrollPane che contiene il contentBox */
    @FXML
    private ScrollPane scrollPane;

    /** Bottone per creare/aggiornare notifiche */
    @FXML
    private Button creaNotifica;

    @FXML
    private Label labelInfoText;

    /** Utente attualmente loggato nell'applicazione */
    private Utente utenteLoggato;

    /** DAO per la gestione delle notifiche nel database */
    private final NotificaDAO notificaDAO;



    /**
     * Costruttore del controller.
     * Inizializza il DAO per le notifiche.
     * @author Sderr12
     */
    public NotificheViewController() {
        this.notificaDAO = new NotificaDAOImpl();
    }

    /**
     * Metodo di inizializzazione del controller.
     * Viene eseguito automaticamente da JavaFX dopo il caricamento della view.
     * Configura il bottone per creare notifiche.
     *
     * @author Sderr12
     */
    @FXML
    private void initialize() {
    }

    /**
     * Imposta l'utente loggato per il controller e carica le notifiche.
     *
     * @param utente Utente attualmente loggato
     * @author Sderr12
     */
    public void setUtenteLoggatoLoadNotifiche(Utente utente) {
        System.out.println("setUtenteLoggato called. utente=" + (utente == null ? "null" : utente.getIdUtente()));

        this.utenteLoggato = utente;
        Platform.runLater(this::loadNotifiche);
    }

    /**
     * Carica le notifiche dell'utente loggato e le aggiunge al contentBox.
     * Il tipo di notifiche caricate dipende dal ruolo dell'utente:
     * - PROPRIETARIO: vede le notifiche che ha creato
     * - COLTIVATORE: vede le notifiche a lui destinate o destinate a tutti
     *
     * @author Sderr12
     */
    public void loadNotifiche() {
        System.out.println("loadNotifiche called. children before clear: " + contentBox.getChildren().size());

        contentBox.getChildren().clear();

        if (utenteLoggato == null) {
            return;
        }

        try {
            List<Notifica> notifiche;

            // Carica notifiche in base al ruolo
            if (utenteLoggato.getRuolo() == Ruolo.PROPRIETARIO) {
                notifiche = notificaDAO.getNotifichePerProprietario(utenteLoggato.getIdUtente());
            } else {
                notifiche = notificaDAO.getNotifichePerDestinatario(utenteLoggato.getIdUtente());
                creaNotifica.setVisible(false);
                labelInfoText.setText("Visualizza le tue notifiche qui in basso");

            }

            if (notifiche.isEmpty()) {

                return;
            }

            // Crea le card per ogni notifica
            for (Notifica notifica : notifiche) {
                System.out.println("Caricamento notifica: " + notifica.getId());
                VBox card = creaNotificaCard(notifica);
                contentBox.getChildren().add(card);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * Aggiorna la lista delle notifiche visualizzate.
     * Ricarica tutte le notifiche dal database.
     *
     * @author Sderr12
     */
    @FXML
    public void openCreateNotifica() throws IOException {
        CreateNotificaGUI.openCreateNotificaView(utenteLoggato);
    }

    /**
     * Carica le notifiche per un progetto specifico.
     *
     * @param idProgetto ID del progetto
     * @author Sderr12
     */
    public void loadNotifichePerProgetto(int idProgetto) {
        contentBox.getChildren().clear();

        try {
            List<Notifica> notifiche = notificaDAO.getNotifichePerProgetto(idProgetto);

            if (notifiche.isEmpty()) {
                return;
            }

            for (Notifica notifica : notifiche) {
                VBox card = creaNotificaCard(notifica);
                contentBox.getChildren().add(card);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea una card visuale per una notifica.
     * La card include titolo, tipo, descrizione, data, destinatario e bottoni di azione.
     *
     * @param notifica oggetto Notifica da visualizzare
     * @return VBox contenente la card della notifica
     * @author Sderr12
     */
    private VBox creaNotificaCard(Notifica notifica) {
        VBox card = new VBox(10);
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-radius: 10; " +
                        "-fx-border-color: #1b5d20; " +
                        "-fx-border-width: 2; " +
                        "-fx-padding: 15;"
        );
        card.setPrefWidth(740);

        // Header con titolo e data
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        Label titoloLabel = new Label(notifica.getTitolo());
        titoloLabel.setFont(new Font("System Bold", 16));
        titoloLabel.setStyle("-fx-text-fill: #1b5d20;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        String dataFormattata = notifica.getDataCreazione() != null
                ? notifica.getDataCreazione().format(DATE_FORMATTER)
                : "";
        Label dataLabel = new Label(dataFormattata);
        dataLabel.setFont(new Font("System Italic", 12));
        dataLabel.setStyle("-fx-text-fill: #666666;");

        header.getChildren().addAll(titoloLabel, spacer, dataLabel);

        // Badge con tipo e scadenza
        HBox badgeBox = new HBox(10);
        badgeBox.setAlignment(Pos.CENTER_LEFT);

        Label tipoBadge = new Label(notifica.getTipo().toUpperCase());
        tipoBadge.setStyle(
                "-fx-background-color: " + getColoreTipo(notifica.getTipo()) + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 5; " +
                        "-fx-padding: 3 8 3 8; " +
                        "-fx-font-size: 10; " +
                        "-fx-font-weight: bold;"
        );
        badgeBox.getChildren().add(tipoBadge);

        if (notifica.getGiorniScadenza() > 0) {
            Label scadenzaBadge = new Label("Scade tra " + notifica.getGiorniScadenza() + " giorni");
            scadenzaBadge.setStyle(
                    "-fx-background-color: #ff9800; " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 5; " +
                            "-fx-padding: 3 8 3 8; " +
                            "-fx-font-size: 10;"
            );
            badgeBox.getChildren().add(scadenzaBadge);
        }

        // Destinatario
        Label destinatarioLabel = new Label();
        destinatarioLabel.setFont(new Font("System Italic", 11));
        destinatarioLabel.setStyle("-fx-text-fill: #666666;");

        if (notifica.isTutti()) {
            destinatarioLabel.setText("Destinato a tutti i coltivatori");
        } else if (notifica.getDestinatario() != null) {
            destinatarioLabel.setText("Destinatario: " + notifica.getDestinatario().getUsername());
        }

        // Descrizione
        Label descrizioneLabel = new Label(notifica.getDescrizione());
        descrizioneLabel.setWrapText(true);
        descrizioneLabel.setFont(new Font("System", 14));
        descrizioneLabel.setStyle("-fx-text-fill: #333333;");

        // Informazioni progetto se presente
        VBox progettoBox = new VBox();
        if (notifica.getProgetto() != null) {
            Label progettoLabel = new Label("Progetto #" + notifica.getProgetto().getIdProgetto());
            progettoLabel.setFont(new Font("System Italic", 12));
            progettoLabel.setStyle("-fx-text-fill: #1b5d20;");
            progettoBox.getChildren().add(progettoLabel);
        }

        // Aggiungi elementi alla card
        card.getChildren().addAll(header, badgeBox, destinatarioLabel, descrizioneLabel);

        if (!progettoBox.getChildren().isEmpty()) {
            card.getChildren().add(progettoBox);
        }

        // Barra azioni (solo per proprietari)
        if (utenteLoggato != null && utenteLoggato.getRuolo() == Ruolo.PROPRIETARIO) {
            HBox barraAzioni = creaBarraAzioni(notifica, card);
            card.getChildren().add(barraAzioni);
        }

        return card;
    }

    /**
     * Crea la barra delle azioni per una notifica (visibile solo ai proprietari).
     * Include il bottone per eliminare la notifica.
     *
     * @param notifica notifica corrente
     * @param card card della notifica
     * @return HBox con i pulsanti di azione
     * @author Sderr12
     */
    private HBox creaBarraAzioni(Notifica notifica, VBox card) {
        HBox barra = new HBox(10);
        barra.setAlignment(Pos.CENTER_RIGHT);
        barra.setPadding(new Insets(5, 0, 0, 0));

        Button btnElimina = new Button("Elimina");
        btnElimina.setStyle(
                "-fx-background-color: #f44336; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 5; " +
                        "-fx-cursor: hand; " +
                        "-fx-padding: 5 15 5 15;"
        );
        btnElimina.setOnAction(e -> eliminaNotifica(notifica.getId(), card));

        barra.getChildren().add(btnElimina);
        return barra;
    }

    /**
     * Elimina una notifica dal database e rimuove la card dalla vista.
     *
     * @param idNotifica ID della notifica da eliminare
     * @param card card visuale da rimuovere
     * @author Sderr12
     */
    private void eliminaNotifica(int idNotifica, VBox card) {
        try {
            boolean success = notificaDAO.eliminaNotifica(idNotifica);

            if (success) {
                contentBox.getChildren().remove(card);
                System.out.println("Notifica " + idNotifica + " eliminata con successo");

                // Se non ci sono più notifiche, mostra messaggio
                if (contentBox.getChildren().isEmpty()) {
                }
            } else {
                System.err.println("Impossibile eliminare la notifica " + idNotifica);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Errore nell'eliminazione della notifica");
        }
    }

    /**
     * Mostra un messaggio quando non ci sono notifiche da visualizzare.
     *
     * @param messaggio testo da visualizzare
     * @author Sderr12
     */

    /**
     * Restituisce il colore hex in base al tipo di notifica.
     *
     * @param tipo tipo di notifica
     * @return colore hex da utilizzare per il badge
     * @author Sderr12
     */
    private String getColoreTipo(String tipo) {
        if (tipo == null) return "#2196F3";

        switch (tipo.toLowerCase()) {
            case "anomalia":
            case "errore":
                return "#f44336"; // Rosso
            case "avviso":
            case "warning":
                return "#ff9800"; // Arancione
            case "attività":
            case "attivita":
            case "attività imminente":
                return "#4CAF50"; // Verde
            case "info":
            case "informazione":
                return "#2196F3"; // Blu
            default:
                return "#9C27B0"; // Viola per tipi non riconosciuti
        }
    }
}