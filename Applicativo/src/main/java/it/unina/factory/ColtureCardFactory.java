package it.unina.factory;

import it.unina.model.Colture;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Factory per la creazione di card grafiche relative alle {@link Colture}.
 * <p>
 * Fornisce metodi statici per generare componenti JavaFX (VBox)
 * che mostrano le principali informazioni di una coltura,
 * con uno stile coerente definito tramite costanti CSS inline.
 * </p>
 *
 * <h2>Esempio d’uso:</h2>
 * <pre>{@code
 * Colture coltura = new Colture();
 * VBox colturaCard = ColtureCardFactory.createColturaCard(coltura);
 * }</pre>
 *
 * @author entn
 */
public class ColtureCardFactory {

    /**
     * Costruttore package-private per impedire istanziazione esterna.
     */
    ColtureCardFactory() {
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
     * Crea un componente grafico che rappresenta una {@link Colture}.
     * <p>
     * Mostra titolo, stagione, tempo di maturazione e lotto associato
     * in una VBox stilizzata, separati da una linea divisoria.
     * </p>
     *
     * @param coltura la coltura da rappresentare.
     * @return una {@link VBox} contenente la card informativa.
     * @author entn
     */
    public static VBox createColturaCard(Colture coltura) {
        VBox vBoxInfoColtura = new VBox(5);

        TextFlow nomeColturaFlow = getColturaFlow("Nome della coltura: ", coltura.getTitolo());
        TextFlow stagioneColturaFlow = getColturaFlow("Stagione: ", coltura.getStagionalita().toString());
        TextFlow tempoMaturazioneFlow = getColturaFlow("Tempo di maturazione: ", coltura.getTempoMaturazione().toDays() + " giorni");
        TextFlow lottoAssociatoFlow = getColturaFlow("Lotto associato: ", coltura.getLotto().getIdLotto() + "- "+ coltura.getLotto().getNome());

        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #ffffff; -fx-pref-height: 2;");

        vBoxInfoColtura.getChildren().addAll(
                nomeColturaFlow,
                stagioneColturaFlow,
                tempoMaturazioneFlow,
                lottoAssociatoFlow,
                separator
        );
        return vBoxInfoColtura;
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
    private static TextFlow getColturaFlow(String label, String value) {
        TextFlow colturaFlow = new TextFlow();

        Text labelPart = new Text(label);
        labelPart.setStyle(STILE_TEXT);

        Text valuePart = new Text(value);
        valuePart.setStyle(STILE_TEXT_DATI);

        colturaFlow.getChildren().addAll(labelPart, valuePart);
        return colturaFlow;
    }
}
