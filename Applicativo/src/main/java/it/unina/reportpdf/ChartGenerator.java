package it.unina.reportpdf;

import it.unina.stats.StatisticheColtura;
import it.unina.model.Colture;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.Map;

/**
 * Classe utility per generare grafici a barre (BarChart) a partire dai dati delle colture.
 * <p>
 * Utilizza JFreeChart per creare grafici basati sulle statistiche di raccolta delle colture,
 * visualizzando valori come media, minimo e massimo delle quantità raccolte.
 *
 * @author entn
 */
public class ChartGenerator {
    ChartGenerator(){}
    /**
     * Crea un grafico a barre (BarChart) con le statistiche delle colture fornite.
     * <p>
     * Per ogni coltura nel dataset, il grafico mostrerà tre serie:
     * <ul>
     *   <li>Media delle quantità raccolte</li>
     *   <li>Valore minimo delle quantità raccolte</li>
     *   <li>Valore massimo delle quantità raccolte</li>
     * </ul>
     *
     * @param titolo titolo del grafico
     * @param datiColtura mappa che associa ogni {@link Colture} alle relative {@link StatisticheColtura}
     * @return un oggetto {@link JFreeChart} rappresentante il grafico a barre
     */
    public static JFreeChart creaBarChart(String titolo, Map<Colture, StatisticheColtura> datiColtura) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<Colture, StatisticheColtura> entry : datiColtura.entrySet()) {
            Colture coltura = entry.getKey();
            StatisticheColtura stats = entry.getValue();

            String nomeColtura = coltura.getTitolo();

            dataset.addValue(stats.getMedia(), "Media", nomeColtura);
            dataset.addValue(stats.getMin(), "Minimo", nomeColtura);
            dataset.addValue(stats.getMax(), "Massimo", nomeColtura);
        }

        return ChartFactory.createBarChart(
                titolo,              // titolo grafico
                "Colture",           // asse X
                "Quantità raccolta", // asse Y
                dataset
        );
    }
}
