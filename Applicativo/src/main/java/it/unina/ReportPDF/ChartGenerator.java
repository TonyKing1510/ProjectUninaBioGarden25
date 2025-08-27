package it.unina.ReportPDF;

import it.unina.Stats.StatisticheColtura;
import it.unina.model.Colture;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.Map;

public class ChartGenerator {

    public static JFreeChart creaBarChart(String titolo, Map<Colture, StatisticheColtura> datiColtura) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<Colture, StatisticheColtura> entry : datiColtura.entrySet()) {
            Colture coltura = entry.getKey();
            System.out.println("Elaborando coltura: " + entry.getKey().getTitolo());
            StatisticheColtura stats = entry.getValue();

            String nomeColtura = coltura.getTitolo();
            System.out.println("Aggiungendo dati per coltura: " + nomeColtura);

            dataset.addValue(stats.getMedia(), "Media", nomeColtura);
            System.out.println(dataset.getValue("Media", nomeColtura));
            dataset.addValue(stats.getMin(), "Minimo", nomeColtura);
            dataset.addValue(stats.getMax(), "Massimo", nomeColtura);
        }

        return ChartFactory.createBarChart(
                titolo,              // titolo grafico
                "Colture",           // asse X
                "Quantità",          // asse Y
                dataset
        );
    }
}