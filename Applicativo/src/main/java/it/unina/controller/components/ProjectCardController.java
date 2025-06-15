package it.unina.controller.components;

import it.unina.model.Progetto;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ProjectCardController {

    @FXML private AnchorPane cardPane;
    @FXML private void onProjectClick() {
        System.out.println("Hello! I'm a project card!");
    }

    @FXML
    private Label nomeProgetto;
    @FXML
    private Label stagioneLabel;
    @FXML
    private Label dataInizioLabel;
    @FXML
    private Label dataFineLabel;
    @FXML
    private ProgressBar progressBar;

    public void setProgetto(Progetto progetto) {
        nomeProgetto.setText(progetto.getTitolo());
        stagioneLabel.setText(progetto.getStagione().toString()); // Enum in camelCase?
        dataInizioLabel.setText(progetto.getDataInizio().toString());
        dataFineLabel.setText(progetto.getDataFine().toString());

        double progresso = calcolaAvanzamento(progetto);

        progressBar.setProgress(progresso);

    }

    // Questo è un esempio, puoi basarlo sulla data odierna
    private double calcolaAvanzamento(Progetto progetto) {
        LocalDate oggi = LocalDate.now();
        LocalDate inizio = progetto.getDataInizio().toLocalDate();
        LocalDate fine = progetto.getDataFine().toLocalDate();

        System.out.println("Oggi: " + oggi);
        System.out.println("Inizio: " + inizio);
        System.out.println("Fine: " + fine);
        if (oggi.isBefore(inizio)) {
            System.out.println("Il progetto non è ancora iniziato.");
            return 0.0; // Il progetto non è ancora iniziato
        } else if (oggi.isAfter(fine)) {
            System.out.println("Il progetto è già terminato.");
            return 1.0; // Il progetto è già terminato
        }


        long totale = ChronoUnit.DAYS.between(inizio, fine);
        long trascorsi = ChronoUnit.DAYS.between(inizio, oggi);

        System.out.println("Totale giorni: " + totale);
        System.out.println("Giorni trascorsi: " + trascorsi);

        return totale == 0 ? 1.0 : (double) trascorsi / totale;
    }
}
