package it.unina.controller;

import it.unina.factory.AttivitaCardFactory;
import it.unina.factory.ColtureCardFactory;
import it.unina.factory.LottoCardFactory;
import it.unina.reportpdf.ReportGenerator;
import it.unina.stats.StatisticheColtura;
import it.unina.dao.*;
import it.unina.implementazionepostgresql.*;
import it.unina.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Controller per la visualizzazione dettagliata di un progetto.
 * Gestisce la visualizzazione di lotti, colture e attività associate a un progetto.
 * Permette anche di scaricare i dati statistici in formato PDF.
 *
 * @author entn
 */
public class ProgettiDetailsController {

    /** ScrollPane principale della view dei dettagli progetto */
    @FXML
    public ScrollPane scrollPaneDettagli;

    /** Pane contenente le informazioni generali del progetto */
    @FXML
    public Pane paneInfoProgetto;

    /** VBox contenente le card dei lotti */
    @FXML
    public VBox vBoxInfoLotti;

    /** VBox contenente le card delle attività */
    @FXML
    public VBox vBoxInfoAttivita;

    /** VBox contenente le card delle colture */
    @FXML
    public VBox vBoxInfoColture;

    /** Label per il nome del progetto */
    @FXML
    public Label nomeProgettoLabel;

    /** Label per la stagione del progetto */
    @FXML
    public Label stagioneProgettoLabel;

    /** Label per la data di inizio del progetto */
    @FXML
    public Label dataInizioLabel;

    /** Label per la data di fine del progetto */
    @FXML
    public Label dataFineLabel;

    /** Label contenente gli ID dei lotti associati */
    @FXML
    public Label lottiAssociatiLabel;

    /** VBox contenente informazioni generali (non utilizzata direttamente nel codice) */
    @FXML
    public VBox vBoxInfoGenerali;

    /** AnchorPane contenitore principale dei dettagli */
    @FXML
    public AnchorPane anchorPaneInfo;

    /** Progetto corrente visualizzato */
    private Progetto progetto;

    /** Utente attualmente loggato */
    private Utente utente;

    /** DAO per la gestione dei lotti */
    private final LottoDAO lottoDao = new LottoDAOImpl();

    /** DAO per la gestione delle colture */
    private final ColtureDAO coltureDao = new ColtureDAOImpl();

    /** DAO per la gestione delle attività */
    private final AttivitaDAO attivitaDao = new AttivitaDAOImpl();

    /** Lista dei lotti associati al progetto */
    private List<Lotto> lottiAssociati = new ArrayList<>();

    /** Lista delle colture associate ai lotti del progetto */
    private final List<Colture> coltureAssociate = new ArrayList<>();

    /**
     * Costruttore di default.
     */
    public ProgettiDetailsController() {
        // Costruttore di default
    }

    /**
     * Imposta l'utente attualmente loggato.
     * @param utente Utente loggato
     */
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    /**
     * Imposta il progetto corrente da visualizzare.
     * @param progetto Progetto selezionato
     */
    public void setProgetto(Progetto progetto) {
        this.progetto = progetto;
    }

    /**
     * Aggiorna le informazioni generali del progetto (nome, stagione, date, lotti associati)
     * e popola la label dei lotti associati.
     * @param progetto Progetto da visualizzare
     */
    public void setProgettoDetails(Progetto progetto) {
        nomeProgettoLabel.setText(progetto.getTitolo());
        stagioneProgettoLabel.setText(progetto.getStagione().toString());
        dataInizioLabel.setText(progetto.getDataInizio().toString());
        dataFineLabel.setText(progetto.getDataFine().toString());
        List<Lotto> lotti = lottoDao.getLottiByIdProgetto(progetto.getIdProgetto());
        lottiAssociati = lotti;
        StringBuilder lottiBuilder = new StringBuilder(" ");
        for(Lotto lotto : lotti){
            lottiBuilder.append(lotto.getIdLotto()).append(",");
        }
        lottiBuilder.setLength(lottiBuilder.length() - 1);
        lottiAssociatiLabel.setText(lottiBuilder.toString());
    }

    /**
     * Popola la VBox dei lotti con le card dei lotti associati al progetto.
     */
    public void setLottiDetails(){
        vBoxInfoLotti.getChildren().clear();
        for (Lotto lotto : lottiAssociati) {
            vBoxInfoLotti.getChildren().add(LottoCardFactory.createLottoCard(lotto));
        }
        anchorPaneInfo.setPrefHeight(anchorPaneInfo.getPrefHeight() + vBoxInfoLotti.getChildren().size() * 150 + 50);
    }

    /**
     * Popola la VBox delle colture con le card delle colture associate ai lotti del progetto.
     */
    public void setColtureDetails(){
        vBoxInfoColture.getChildren().clear();
        for(Lotto lotto : lottiAssociati){
            List<Colture> colture = coltureDao.getColtureByIdLotto(lotto.getIdLotto());
            for(Colture coltura : colture){
                vBoxInfoColture.getChildren().add(ColtureCardFactory.createColturaCard(coltura));
                coltureAssociate.add(coltura);
            }
        }
        anchorPaneInfo.setPrefHeight(anchorPaneInfo.getPrefHeight() + vBoxInfoColture.getChildren().size() * 150 + 50);
    }

    /**
     * Popola la VBox delle attività con le card delle attività associate alle colture del progetto.
     */
    public void setAttivitaDetails(){
        List<Colture> coltureList = coltureAssociate;
        vBoxInfoAttivita.getChildren().clear();
        for(Colture coltura : coltureList){
            Map<Attivita, List<Utente>> attivitaMap = attivitaDao.getAttivitaByIdColture(coltura.getIdColture());
            for(Map.Entry<Attivita, List<Utente>> entry : attivitaMap.entrySet()){
                Attivita attivita = entry.getKey();
                List<Utente> utenti = entry.getValue();
                vBoxInfoAttivita.getChildren().add(AttivitaCardFactory.createAttivitaCard(attivita, utenti, coltura));
            }
        }
        anchorPaneInfo.setPrefHeight(anchorPaneInfo.getPrefHeight() + vBoxInfoAttivita.getChildren().size() * 200 + 50);
    }

    /**
     * Scarica i dati statistici delle colture associate al progetto
     * e genera un report PDF.
     *
     * @throws StatisticheColtura.StatisticheException Se si verifica un errore nel calcolo delle statistiche
     */
    @FXML
    private void onScaricaDatiClicked() throws StatisticheColtura.StatisticheException, IOException {
        Logger logger = Logger.getLogger(getClass().getName());
        Map<Lotto, Map<Colture, StatisticheColtura>> statistiche = attivitaDao.getStatistichePerLottiEColtureByIdProgetto(progetto);
        statistiche.forEach((lotto, coltureStats) -> {
            logger.info("Lotto ID: " + lotto.getIdLotto());
            coltureStats.forEach((coltura, stats) -> {
                logger.info("   Coltura ID: " + coltura.getIdColture());
                logger.info("      Totale raccolte: " + stats.getTotaleRaccolte());
                logger.info("      Media: " + stats.getMedia());
                logger.info("      Min: " + stats.getMin());
                logger.info("      Max: " + stats.getMax());
            });
        });
        // Percorso completo nella directory di lavoro
        String outputPath = System.getProperty("user.dir") + "\\report.pdf";
        ReportGenerator.generaReportPDF(statistiche, outputPath);
        logger.info("Report PDF generato in: " + outputPath);
        // Apri la cartella contenente il PDF
        File file = new File(outputPath);
        if (file.exists() && java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop.getDesktop().open(file.getParentFile());
        }
    }
}
