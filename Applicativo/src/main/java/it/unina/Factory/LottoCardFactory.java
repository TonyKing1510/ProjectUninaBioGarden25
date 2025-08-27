package it.unina.Factory;

import it.unina.model.Lotto;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class LottoCardFactory {
    LottoCardFactory(){}
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




    public static VBox createLottoCard(Lotto lotto) {
        VBox vBoxInfoLotto = new VBox(5);

        TextFlow lottoIdFlow = new TextFlow();
        Text labelPart = new Text("Lotto ID: ");
        labelPart.setStyle(STILE_TEXT);
        Text valuePart = new Text(String.valueOf(lotto.getIdLotto()));
        valuePart.setStyle(STILE_TEXT_DATI);
        lottoIdFlow.getChildren().addAll(labelPart, valuePart);

        TextFlow superficieFlow = new TextFlow();
        Text superficieLabelPart = new Text("Superficie: ");
        superficieLabelPart.setStyle(STILE_TEXT);
        Text superficieValuePart = new Text(lotto.getSuperficie() + " mq");
        superficieValuePart.setStyle(STILE_TEXT_DATI);
        superficieFlow.getChildren().addAll(superficieLabelPart, superficieValuePart);

        TextFlow indirizzoFlow = new TextFlow();
        Text indirizzoLabelPart = new Text("Indirizzo: ");
        indirizzoLabelPart.setStyle(STILE_TEXT);
        Text indirizzoValuePart = new Text(lotto.getIndirizzo() + " " + lotto.getCap());
        indirizzoValuePart.setStyle(STILE_TEXT_DATI);
        indirizzoFlow.getChildren().addAll(indirizzoLabelPart, indirizzoValuePart);


        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #ffffff; -fx-pref-height: 2;"); // opzionale, per renderlo più visibile
        vBoxInfoLotto.getChildren().addAll(lottoIdFlow,superficieFlow, indirizzoFlow, separator);

        return vBoxInfoLotto;
    }
}