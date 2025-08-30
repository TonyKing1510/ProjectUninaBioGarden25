package it.unina.Stats;

import it.unina.model.Attivita;

import java.util.List;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticheColtura {
    private int totaleRaccolte;
    private int min;
    private int max;
    private double media;

    public StatisticheColtura(int totaleRaccolte, int min, int max, double media) {
        this.totaleRaccolte = totaleRaccolte;
        this.min = min;
        this.max = max;
        this.media = media;
    }

    // Getter
    public int getTotaleRaccolte() { return totaleRaccolte; }
    public int getMin() { return min; }
    public int getMax() { return max; }
    public double getMedia() { return media; }

    /**
     * Crea un oggetto StatisticheColtura a partire da una lista di Attivita.
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
        System.out.println("Calcolate statistiche: totale=" + totale + ", min=" + min + ", max=" + max + ", media=" + media);

        return new StatisticheColtura(totale, min, max, media);
    }
}

