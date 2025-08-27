package it.unina.ReportPDF;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import it.unina.Stats.StatisticheColtura;
import it.unina.model.Colture;
import it.unina.model.Lotto;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Map;

public class ReportGenerator {

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
                System.out.println("📌 Generating report for Lotto ID: " + statsColture);

                //  Creo il grafico per questo lotto
                JFreeChart chart = ChartGenerator.creaBarChart("Statistiche Lotto " + lotto.getIdLotto(), statsColture);

                //  Converto in immagine
                BufferedImage bufferedImage = chart.createBufferedImage(500, 300);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ChartUtils.writeBufferedImageAsPNG(baos, bufferedImage);

                //  Inserisco nel PDF
                Image image = Image.getInstance(baos.toByteArray());
                document.add(image);

                // Spazio tra un lotto e l’altro
                document.add(new Paragraph("\n\n"));
            }

            document.close();
            System.out.println("✅ Report PDF generato in: " + outputPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
