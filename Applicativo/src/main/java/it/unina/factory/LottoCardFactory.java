package it.unina.factory;

import it.unina.model.Lotto;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Factory per la creazione di card grafiche relative ai {@link Lotto}.
 * <p>
 * Fornisce metodi statici per generare componenti JavaFX (VBox)
 * che mostrano le principali informazioni di un lotto,
 * con uno stile coerente definito tramite costanti CSS inline.
 * </p>
 *
 * <h2>Esempio d’uso:</h2>
 * <pre>{@code
 * Lotto lotto = new Lotto();
 * VBox lottoCard = LottoCardFactory.createLottoCard(lotto);
 * }</pre>
 *
 * @author entn
 */
public class LottoCardFactory {

    /**
     * Costruttore package-private per impedire istanziazione esterna.
     */
    LottoCardFactory() {
        // Impedisce l'istanziazione
    }

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
     * Crea un componente grafico che rappresenta un {@link Lotto}.
     * <p>
     * Mostra ID lotto, superficie e indirizzo in una VBox stilizzata,
     * separati da una linea divisoria.
     * </p>
     *
     * @param lotto il lotto da rappresentare.
     * @return una {@link VBox} contenente la card informativa.
     * @author entn
     */
    public static VBox createLottoCard(Lotto lotto) {
        VBox vBoxInfoLotto = new VBox(5);

        TextFlow lottoIdFlow = getTextFlow("Lotto: ", lotto.getIdLotto() + "- " + lotto.getNome());
        TextFlow superficieFlow = getTextFlow("Superficie: ", lotto.getSuperficie() + " mq");
        TextFlow indirizzoFlow = getTextFlow("Indirizzo: ", lotto.getIndirizzo() + " " + lotto.getCap());

        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #ffffff; -fx-pref-height: 2;");

        vBoxInfoLotto.getChildren().addAll(lottoIdFlow, superficieFlow, indirizzoFlow, separator);

        return vBoxInfoLotto;
    }

    /**
     * Metodo di supporto per creare un {@link TextFlow} con etichetta e valore,
     * applicando gli stili predefiniti.
     *
     * @param label testo dell’etichetta.
     * @param value valore da mostrare.
     * @return un {@link TextFlow} pronto da inserire nella card.
     * @author entn
     */
    private static TextFlow getTextFlow(String label, String value) {
        TextFlow lottoFlow = new TextFlow();

        Text labelPart = new Text(label);
        labelPart.setStyle(STILE_TEXT);

        Text valuePart = new Text(value);
        valuePart.setStyle(STILE_TEXT_DATI);

        lottoFlow.getChildren().addAll(labelPart, valuePart);
        return lottoFlow;
    }
}
