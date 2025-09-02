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

public class AttivitaCardFactory {
    AttivitaCardFactory() {
    }

    private static final AttivitaDAO attivitaDAO = new AttivitaDAOImpl();

    public static final String STILE_TEXT_DATI =
            "-fx-fill: #ffffff;" +
                    "-fx-font-size: 15px;" +
                    "-fx-font-family: 'System';" +
                    "-fx-font-style: italic;";

    public static final String STILE_TEXT =
            "-fx-fill: #ffffff;" +
                    "-fx-font-size: 15px;" +
                    "-fx-font-family: 'System';" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-style: italic;";

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
                // Qui puoi anche aggiornare il DB e refreshare la card
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
                statoBox,            // <-- ora qui c’è il TextFlow con il bottone affiancato
                dataInizioFlow,
                dataFineFlow,
                coltivatoriFlow,
                colturaFlow,
                separator
        );

        return vBoxInfoAttivita;
    }


    private static TextFlow getColtivatoriFlow(List<Utente> coltivatori) {
        TextFlow coltivatoriFlow = new TextFlow();
        Text coltivatoriLabelPart = new Text("Id coltivatore associato: ");
        coltivatoriLabelPart.setStyle(STILE_TEXT);
        StringBuilder coltivatoriNames = new StringBuilder();
        for (int i = 0; i < coltivatori.size(); i++) {
            coltivatoriNames.append(coltivatori.getFirst().getIdUtente());
            if (i < coltivatori.size() - 1) {
                coltivatoriNames.append(", ");
            }
        }
        Text coltivatoriValuePart = new Text(coltivatoriNames.toString());
        coltivatoriValuePart.setStyle(STILE_TEXT_DATI);
        coltivatoriFlow.getChildren().addAll(coltivatoriLabelPart, coltivatoriValuePart);
        return coltivatoriFlow;
    }

    private static TextFlow getDataInizioFlow(String s, String attivita) {
        TextFlow dataInizioFlow = new TextFlow();
        Text dataInizioLabelPart = new Text(s);
        dataInizioLabelPart.setStyle(STILE_TEXT);
        Text dataInizioValuePart = new Text(attivita);
        dataInizioValuePart.setStyle(STILE_TEXT_DATI);
        dataInizioFlow.getChildren().addAll(dataInizioLabelPart, dataInizioValuePart);
        return dataInizioFlow;
    }

    private static TextFlow getStatoAttivitaFlow(String s, String attivita) {
        return getDataInizioFlow(s, attivita);
    }

    private static TextFlow getTipoAttivitaFlow(Attivita attivita) {
        return getDataInizioFlow("Tipo di Attività: ", String.valueOf(attivita.getTipo()));
    }
}
