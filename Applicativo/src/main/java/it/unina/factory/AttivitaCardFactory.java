package it.unina.factory;

import it.unina.dao.AttivitaDAO;
import it.unina.implementazionepostgresql.AttivitaDAOImpl;
import it.unina.model.Attivita;
import it.unina.model.Colture;
import it.unina.model.StatoAttivita;
import it.unina.model.Utente;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Factory per la creazione di card grafiche relative alle {@link Attivita}.
 * <p>
 * Ogni card mostra informazioni principali di un’attività agricola:
 * tipo, stato (modificabile tramite dialogo), date, coltura associata
 * e i coltivatori coinvolti.
 * </p>
 *
 * <h2>Esempio d’uso:</h2>
 * <pre>{@code
 * Attivita attivita = ...;
 * List<Utente> coltivatori = ...;
 * Colture coltura = ...;
 * VBox card = AttivitaCardFactory.createAttivitaCard(attivita, coltivatori, coltura);
 * }</pre>
 *
 * @author entn
 */
public class AttivitaCardFactory {

    /**
     * Costruttore package-private per impedire istanziazione esterna.
     */
    AttivitaCardFactory() {
        // Impedisce l'istanziazione
    }

    /** DAO per la gestione delle attività (interazione con DB). */
    private static final AttivitaDAO attivitaDAO = new AttivitaDAOImpl();

    /** Stile CSS per i valori (dati) mostrati nelle card. */
    public static final String STILE_TEXT_DATI =
            "-fx-fill: #ffffff;" +
                    "-fx-font-size: 15px;" +
                    "-fx-font-family: 'System';" +
                    "-fx-font-style: italic;";

    /** Stile CSS per le etichette (titoli) mostrati nelle card. */
    public static final String STILE_TEXT =
            "-fx-fill: #ffffff;" +
                    "-fx-font-size: 15px;" +
                    "-fx-font-family: 'System';" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-style: italic;";

    /**
     * Crea una card informativa per una {@link Attivita}.
     * <p>
     * La card include:
     * <ul>
     *     <li>Tipo di attività</li>
     *     <li>Stato (con bottone per modifica e aggiornamento DB)</li>
     *     <li>Date di inizio e fine</li>
     *     <li>Coltivatori associati</li>
     *     <li>Coltura di riferimento</li>
     * </ul>
     * </p>
     *
     * @param attivita    l’attività da rappresentare.
     * @param coltivatori lista degli utenti (coltivatori) coinvolti.
     * @param colture     la coltura associata all’attività.
     * @return una {@link VBox} contenente la card informativa.
     * @author entn
     */
    public static VBox createAttivitaCard(Attivita attivita, List<Utente> coltivatori, Colture colture) {
        VBox vBoxInfoAttivita = new VBox(5);

        TextFlow tipoAttivitaFlow = getTipoAttivitaFlow(attivita);

        // Stato attività + bottone "Modifica"
        TextFlow statoAttivitaFlow = getStatoAttivitaFlow("Stato Attività: ", attivita.getStato().toString());
        Button modificaButton = new Button("Modifica");
        modificaButton.setOnAction(e -> {
            // elenco degli stati disponibili
            List<String> stati = Arrays.asList("IN_CORSO", "COMPLETATA", "PROGRAMMATA");

            ChoiceDialog<String> dialog = new ChoiceDialog<>(attivita.getStato().toString(), stati);
            dialog.setTitle("Modifica Stato");
            dialog.setHeaderText("Scegli il nuovo stato dell'attività");
            dialog.setContentText("Nuovo stato:");

            // Mostra il dialog e aspetta la scelta
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(nuovoStato -> {
                attivita.setStato(StatoAttivita.valueOf(nuovoStato));
                // Aggiornamento su DB
                attivitaDAO.updateAttivita(attivita);
            });
        });

        HBox statoBox = new HBox(5, statoAttivitaFlow, modificaButton);

        TextFlow dataInizioFlow = getDataInizioFlow("Data Inizio: ", attivita.getDataInizio().toString());
        TextFlow dataFineFlow = getDataInizioFlow("Data Fine: ", attivita.getDataFine().toString());
        TextFlow coltivatoriFlow = getColtivatoriFlow(coltivatori);
        TextFlow colturaFlow = getDataInizioFlow("Coltura: ", colture.getTitolo());

        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #ffffff; -fx-pref-height: 2;");

        vBoxInfoAttivita.getChildren().addAll(
                tipoAttivitaFlow,
                statoBox,    // Stato + pulsante modifica
                dataInizioFlow,
                dataFineFlow,
                coltivatoriFlow,
                colturaFlow,
                separator
        );

        return vBoxInfoAttivita;
    }

    /**
     * Crea un {@link TextFlow} per visualizzare i coltivatori associati a un’attività.
     *
     * @param coltivatori lista degli utenti coltivatori.
     * @return un {@link TextFlow} con gli id dei coltivatori.
     * @author entn
     */
    private static TextFlow getColtivatoriFlow(List<Utente> coltivatori) {
        TextFlow coltivatoriFlow = new TextFlow();

        Text coltivatoriLabelPart = new Text("Id coltivatore associato: ");
        coltivatoriLabelPart.setStyle(STILE_TEXT);

        StringBuilder coltivatoriIds = new StringBuilder();
        for (int i = 0; i < coltivatori.size(); i++) {
            coltivatoriIds.append(coltivatori.get(i).getIdUtente());
            if (i < coltivatori.size() - 1) {
                coltivatoriIds.append(", ");
            }
        }

        Text coltivatoriValuePart = new Text(coltivatoriIds.toString());
        coltivatoriValuePart.setStyle(STILE_TEXT_DATI);

        coltivatoriFlow.getChildren().addAll(coltivatoriLabelPart, coltivatoriValuePart);
        return coltivatoriFlow;
    }

    /**
     * Metodo di supporto per creare un {@link TextFlow} etichetta + valore.
     *
     * @param label testo dell’etichetta.
     * @param value valore da mostrare.
     * @return un {@link TextFlow} pronto da inserire nella card.
     * @author entn
     */
    private static TextFlow getDataInizioFlow(String label, String value) {
        TextFlow flow = new TextFlow();

        Text labelPart = new Text(label);
        labelPart.setStyle(STILE_TEXT);

        Text valuePart = new Text(value);
        valuePart.setStyle(STILE_TEXT_DATI);

        flow.getChildren().addAll(labelPart, valuePart);
        return flow;
    }

    /**
     * Metodo di supporto per creare un {@link TextFlow} con lo stato dell’attività.
     *
     * @param label etichetta descrittiva.
     * @param stato stato attuale dell’attività.
     * @return un {@link TextFlow} con lo stato.
     * @author entn
     */
    private static TextFlow getStatoAttivitaFlow(String label, String stato) {
        return getDataInizioFlow(label, stato);
    }

    /**
     * Metodo di supporto per mostrare il tipo di attività.
     *
     * @param attivita attività di riferimento.
     * @return un {@link TextFlow} con il tipo dell’attività.
     * @author entn
     */
    private static TextFlow getTipoAttivitaFlow(Attivita attivita) {
        return getDataInizioFlow("Tipo di Attività: ", String.valueOf(attivita.getTipo()));
    }
}
