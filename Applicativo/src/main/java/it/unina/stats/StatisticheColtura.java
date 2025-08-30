package it.unina.stats;

import it.unina.model.Attivita;
import java.util.List;

/**
 * Rappresenta un insieme di statistiche calcolate sulle attività di una coltura,
 * in particolare sulle quantità raccolte.
 * <p>
 * Le statistiche includono:
 * <ul>
 *   <li>Il numero totale di raccolte registrate</li>
 *   <li>Il valore minimo di raccolto</li>
 *   <li>Il valore massimo di raccolto</li>
 *   <li>La media delle quantità raccolte</li>
 * </ul>
 *
 * Questa classe fornisce inoltre un metodo statico {@link #fromAttivita(List)}
 * per costruire automaticamente le statistiche a partire da una lista di
 * oggetti {@link Attivita}.
 *
 * @author entn
 */
public class StatisticheColtura {
    private final int totaleRaccolte;
    private final int min;
    private final int max;
    private final double media;

    /**
     * Costruisce un nuovo oggetto {@code StatisticheColtura} con i valori forniti.
     *
     * @param totaleRaccolte il numero totale di raccolte registrate
     * @param min il valore minimo delle quantità raccolte
     * @param max il valore massimo delle quantità raccolte
     * @param media la media delle quantità raccolte
     */
    public StatisticheColtura(int totaleRaccolte, int min, int max, double media) {
        this.totaleRaccolte = totaleRaccolte;
        this.min = min;
        this.max = max;
        this.media = media;
    }

    /**
     * Restituisce il numero totale di raccolte.
     *
     * @return numero totale di raccolte
     */
    public int getTotaleRaccolte() { return totaleRaccolte; }

    /**
     * Restituisce il valore minimo tra le quantità raccolte.
     *
     * @return valore minimo
     */
    public int getMin() { return min; }

    /**
     * Restituisce il valore massimo tra le quantità raccolte.
     *
     * @return valore massimo
     */
    public int getMax() { return max; }

    /**
     * Restituisce la media delle quantità raccolte.
     *
     * @return media
     */
    public double getMedia() { return media; }

    /**
     * Crea un oggetto {@code StatisticheColtura} a partire da una lista di {@link Attivita}.
     * <p>
     * Se la lista è vuota o nulla, restituisce una statistica con valori pari a zero.
     * Altrimenti calcola:
     * <ul>
     *   <li>Il numero totale di attività come {@code totaleRaccolte}</li>
     *   <li>Il minimo delle quantità raccolte</li>
     *   <li>Il massimo delle quantità raccolte</li>
     *   <li>La media delle quantità raccolte</li>
     * </ul>
     *
     * @param attivitaList lista di attività da cui calcolare le statistiche
     * @return un oggetto {@code StatisticheColtura} con i valori calcolati
     */
    public static StatisticheColtura fromAttivita(List<Attivita> attivitaList) {
        if (attivitaList == null || attivitaList.isEmpty()) {
            return new StatisticheColtura(0, 0, 0, 0);
        }

        int totale = attivitaList.size();
        int min = attivitaList.stream()
                .mapToInt(Attivita::getQuantitaRaccolta)
                .min()
                .orElse(0);
        int max = attivitaList.stream()
                .mapToInt(Attivita::getQuantitaRaccolta)
                .max()
                .orElse(0);
        double media = attivitaList.stream()
                .mapToInt(Attivita::getQuantitaRaccolta)
                .average()
                .orElse(0);

        return new StatisticheColtura(totale, min, max, media);
    }
}
