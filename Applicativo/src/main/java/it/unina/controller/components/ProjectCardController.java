package it.unina.controller.components;

import it.unina.gui.ProgettoDetailsGUI;
import it.unina.model.Progetto;
import it.unina.model.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * Controller della card di un progetto nell'interfaccia JavaFX.
 * Gestisce la visualizzazione di informazioni sintetiche del progetto
 * e permette di aprire la pagina dettagliata del progetto al click.
 *
 * @author entn
 */
public class ProjectCardController {

    @FXML
    private Label nomeProgetto; // Label per il titolo del progetto
    @FXML
    private Label stagioneLabel; // Label per la stagione del progetto
    @FXML
    private Label dataInizioLabel; // Label per la data di inizio
    @FXML
    private Label dataFineLabel; // Label per la data di fine

    private Progetto progetto; // Riferimento al progetto associato alla card
    private Utente utente; // Riferimento all'utente loggato

    /**
     * Imposta il progetto associato alla card.
     *
     * @param progetto progetto da associare
     * @author entn
     */
    public void setProgetto(Progetto progetto){
        this.progetto = progetto;
    }

    /**
     * Imposta l'utente loggato per questa card.
     *
     * @param utente utente loggato
     * @author entn
     */
    public void setUtente(Utente utente){
        this.utente = utente;
    }

    /**
     * Aggiorna le label della card con i dettagli del progetto fornito.
     * Visualizza titolo, stagione e date di inizio/fine.
     *
     * @param progetto progetto di cui mostrare i dettagli
     * @author entn
     */
    public void setProgettoDetails(Progetto progetto) {
        System.out.println("Setting project details for: " + progetto.getIdProgetto());
        nomeProgetto.setText(progetto.getTitolo());
        stagioneLabel.setText(progetto.getStagione().toString());
        dataInizioLabel.setText(progetto.getDataInizio().toString());
        dataFineLabel.setText(progetto.getDataFine().toString());
    }

    /**
     * Gestisce l'evento di click sulla card del progetto.
     * Apre la pagina dei dettagli del progetto tramite ProgettoDetailsGUI.
     *
     * @throws IOException se si verifica un errore nell'apertura della pagina dettagliata
     * @author entn
     */
    @FXML
    private void onProjectClick() throws IOException {
        ProgettoDetailsGUI.openPageProgettoDetailsGUI(progetto, utente);
    }

}
