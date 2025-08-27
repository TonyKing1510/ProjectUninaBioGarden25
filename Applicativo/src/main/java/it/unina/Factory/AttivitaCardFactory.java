package it.unina.Factory;

import it.unina.model.Attivita;
import it.unina.model.Colture;
import it.unina.model.Utente;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.List;

public class AttivitaCardFactory {
    AttivitaCardFactory() {
    }

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

        TextFlow statoAttivitaFlow = getStatoAttivitaFlow("Stato Attività: ", attivita.getStato().toString());

        TextFlow dataInizioFlow = getDataInizioFlow("Data Inizio: ", attivita.getDataInizio().toString());

        TextFlow dataFineFlow = getDataInizioFlow("Data Fine: ", attivita.getDataFine().toString());

        TextFlow coltivatoriFlow = getColtivatoriFlow(coltivatori);

        TextFlow colturaFlow = getDataInizioFlow("Coltura: ", colture.getTitolo());

        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #ffffff; -fx-pref-height: 2;"); // opzionale, per renderlo più visibile

        vBoxInfoAttivita.getChildren().addAll(tipoAttivitaFlow, statoAttivitaFlow, dataInizioFlow, dataFineFlow, coltivatoriFlow, colturaFlow,separator);

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
        TextFlow statoAttivitaFlow = getDataInizioFlow(s, attivita);
        return statoAttivitaFlow;
    }

    private static TextFlow getTipoAttivitaFlow(Attivita attivita) {
        TextFlow tipoAttivitaFlow = getDataInizioFlow("Tipo di Attività: ", String.valueOf(attivita.getTipo()));
        return tipoAttivitaFlow;
    }
}
