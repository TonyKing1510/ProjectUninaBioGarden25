package it.unina.Factory;

import it.unina.model.Colture;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ColtureCardFactory {
    ColtureCardFactory() {
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

    public static VBox createColturaCard(Colture coltura) {
        VBox vBoxInfoColtura = new VBox(5);

        TextFlow nomeColturaFlow = getColturaFlow("Nome della coltura: ", String.valueOf(coltura.getTitolo()));

        TextFlow stagioneColturaFlow = getColturaFlow("Stagione: ", coltura.getStagionalita().toString());

        TextFlow tempoMaturazioneFlow = getColturaFlow("Tempo di maturazione: ", coltura.getTempoMaturazione().toDays() + " giorni");

        TextFlow lottoAssociatoFlow = getColturaFlow("Lotto associato: ", String.valueOf(coltura.getLotto().getIdLotto()));

        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #ffffff; -fx-pref-height: 2;"); // opzionale, per renderlo più visibile

        vBoxInfoColtura.getChildren().addAll(nomeColturaFlow, stagioneColturaFlow, tempoMaturazioneFlow,lottoAssociatoFlow,separator);
        return vBoxInfoColtura;
    }

    private static TextFlow getColturaFlow(String s, String coltura) {
        TextFlow ColturaFlow = new TextFlow();
        Text labelPart = new Text(s);
        labelPart.setStyle(STILE_TEXT);
        Text valuePart = new Text(coltura);
        valuePart.setStyle(STILE_TEXT_DATI);
        ColturaFlow.getChildren().addAll(labelPart, valuePart);
        return ColturaFlow;
    }
}
