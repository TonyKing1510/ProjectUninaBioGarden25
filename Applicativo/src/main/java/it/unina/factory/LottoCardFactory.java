package it.unina.factory;

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

        TextFlow lottoIdFlow = getTextFlow("Lotto ID: ", String.valueOf(lotto.getIdLotto()));

        TextFlow superficieFlow = getTextFlow("Superficie: ", lotto.getSuperficie() + " mq");

        TextFlow indirizzoFlow = getTextFlow("Indirizzo: ", lotto.getIndirizzo() + " " + lotto.getCap());


        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #ffffff; -fx-pref-height: 2;"); // opzionale, per renderlo più visibile
        vBoxInfoLotto.getChildren().addAll(lottoIdFlow,superficieFlow, indirizzoFlow, separator);

        return vBoxInfoLotto;
    }

    private static TextFlow getTextFlow(String s, String lotto) {
        TextFlow lottoFlow = new TextFlow();
        Text labelPart = new Text(s);
        labelPart.setStyle(STILE_TEXT);
        Text valuePart = new Text(lotto);
        valuePart.setStyle(STILE_TEXT_DATI);
        lottoFlow.getChildren().addAll(labelPart, valuePart);
        return lottoFlow;
    }
}