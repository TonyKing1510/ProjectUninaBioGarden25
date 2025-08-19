package it.unina.controller.components;

import it.unina.dao.LottoDAO;
import it.unina.dao.ProgettoDAO;
import it.unina.implementazionePostgreSQL.LottoDAOImpl;
import it.unina.implementazionePostgreSQL.ProgettoDAOImpl;
import it.unina.model.Lotto;
import it.unina.model.Progetto;
import it.unina.model.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ProjectCardDetailsController {
    @FXML
    private Label nomeProgetto;
    @FXML
    private Label stagioneLabel;
    @FXML
    private Label dataInizioLabel;
    @FXML
    private Label dataFineLabel;
    @FXML
    private Label lottoLabel;
    @FXML
    private ProgressBar progressBar;

    private Progetto progetto;
    private Utente utente;
    private LottoDAO lottoDao = new LottoDAOImpl();
    private ProgettoDAO progettoDao = new ProgettoDAOImpl();

    public void setProgetto(Progetto progetto){
        this.progetto = progetto;
        System.out.println("Progetto impostato: " + progetto.getTitolo());
    }

    public void setUtente(Utente utente){
        this.utente = utente;
        System.out.println("Utente impostato: " + utente.getNome() + " " + utente.getCognome());
    }

    public void setProgettoDetails(Progetto progetto) {
        System.out.println("Impostazione dettagli del progetto: " + progetto.getTitolo());
        nomeProgetto.setText(progetto.getTitolo());
        stagioneLabel.setText(progetto.getStagione().toString()); // Enum in camelCase?
        dataInizioLabel.setText(progetto.getDataInizio().toString());
        dataFineLabel.setText(progetto.getDataFine().toString());
        int idProgetto = progettoDao.getIdProgettoByTitolo(progetto.getTitolo());


        Lotto lotto = lottoDao.getLottoByIdProgetto(idProgetto);

        lottoLabel.setText(lotto != null ? String.valueOf(lotto.getIdLotto()) : "Nessun lotto associato");

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
