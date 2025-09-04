package it.unina.reportpdf;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import it.unina.stats.StatisticheColtura;
import it.unina.model.Colture;
import it.unina.model.Lotto;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe utility per generare report PDF contenenti grafici a barre delle statistiche
 * di raccolta per ogni lotto e le relative colture.
 * <p>
 * Per ogni lotto nel dataset, la classe genera un grafico a barre che mostra
 * le statistiche di ogni coltura associata (media, minimo, massimo) e lo inserisce
 * nel PDF insieme al titolo del lotto.
 * <p>
 * Utilizza JFreeChart per creare i grafici e iText (com.lowagie) per generare il PDF.
 *
 * @author entn
 */
public class ReportGenerator {

    /**
     * Costruttore privato per impedire l'instanziazione della classe utility.
     */
    ReportGenerator(){}

    private static final Logger logger = Logger.getLogger(ReportGenerator.class.getName());

    /**
     * Genera un report PDF contenente le statistiche di raccolta per ogni lotto.
     * <p>
     * Per ogni lotto, viene creato un grafico a barre con le statistiche delle colture
     * associate e inserito nel PDF. Viene aggiunto anche un titolo con l'ID del lotto.
     *
     * @param statistiche mappa che associa ogni {@link Lotto} ad una mappa di {@link Colture}
     *                    con le relative {@link StatisticheColtura}
     * @param outputPath percorso completo del file PDF di output
     */
    public static void generaReportPDF(
            Map<Lotto, Map<Colture, StatisticheColtura>> statistiche,
            String outputPath
    ) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            for (Map.Entry<Lotto, Map<Colture, StatisticheColtura>> entry : statistiche.entrySet()) {
                Lotto lotto = entry.getKey();
                Map<Colture, StatisticheColtura> statsColture = entry.getValue();

                document.add(new Paragraph("Lotto ID: " + lotto.getIdLotto()));
                // Creo il grafico per questo lotto
                JFreeChart chart = ChartGenerator.creaBarChart("Statistiche Lotto " + lotto.getIdLotto(), statsColture);

                // Converto in immagine
                BufferedImage bufferedImage = chart.createBufferedImage(500, 300);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ChartUtils.writeBufferedImageAsPNG(baos, bufferedImage);

                // Inserisco nel PDF
                Image image = Image.getInstance(baos.toByteArray());
                document.add(image);

                // Spazio tra un lotto e l’altro
                document.add(new Paragraph("\n\n"));
            }

            document.close();
            // log dell'evento
            logger.log(Level.INFO, "Report PDF generato in: {0}", outputPath);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
