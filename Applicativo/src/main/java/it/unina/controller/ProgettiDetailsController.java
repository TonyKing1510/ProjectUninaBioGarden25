package it.unina.controller;

import it.unina.Factory.AttivitaCardFactory;
import it.unina.Factory.ColtureCardFactory;
import it.unina.Factory.LottoCardFactory;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProgettiDetailsController {

    @FXML
    public ScrollPane scrollPaneDettagli;
    @FXML
    public Pane paneInfoProgetto;
    @FXML
    public VBox vBoxInfoLotti;
    @FXML
    public VBox vBoxInfoAttivita;
    @FXML
    public VBox vBoxInfoColture;
    @FXML
    public Label nomeProgettoLabel;
    @FXML
    public Label stagioneProgettoLabel;
    @FXML
    public Label dataInizioLabel;
    @FXML
    public Label dataFineLabel;
    @FXML
    public Label lottiAssociatiLabel;
    @FXML
    public VBox vBoxInfoGenerali;
    @FXML
    public AnchorPane anchorPaneInfo;





    public ProgettiDetailsController() {

    }

    private Progetto progetto;
    private Utente utente;
    private final LottoDAO lottoDao = new LottoDAOImpl();
    private final ColtureDAO coltureDao = new ColtureDAOImpl();
    private final AttivitaDAO attivitaDao = new AttivitaDAOImpl();
    private final UtenteDAO utenteDao = new UtenteDAOImpl();
    private List<Lotto> lottiAssociati = new ArrayList<>();
    private List<Colture> coltureAssociate = new ArrayList<>();


    public void setUtente(Utente utente) {
        this.utente = utente;
    }



    private Progetto getProgetto() {
        return progetto;
    }

    private Utente getUtente() {
        return utente;
    }

    public void setProgetto(Progetto progetto) {
        this.progetto = progetto;
    }

    public void setProgettoDetails(Progetto progetto) {
        System.out.println("Impostazione dettagli del progetto: " + progetto.getTitolo());
        nomeProgettoLabel.setText(progetto.getTitolo());
        stagioneProgettoLabel.setText(progetto.getStagione().toString()); // Enum in camelCase?
        dataInizioLabel.setText(progetto.getDataInizio().toString());
        dataFineLabel.setText(progetto.getDataFine().toString());
        List<Lotto> lotti = lottoDao.getLottiByIdProgetto(progetto.getIdProgetto());
        lottiAssociati = lotti;
        System.out.println("Lotti associati al progetto: " + lotti.size());
        StringBuilder lottiBuilder = new StringBuilder(" ");
        for(Lotto lotto : lotti){
            lottiBuilder.append(lotto.getIdLotto()).append(",");
        }
        lottiBuilder.setLength(lottiBuilder.length() - 1);
        lottiAssociatiLabel.setText(lottiBuilder.toString());
    }

    public void setLottiDetails(){
        vBoxInfoLotti.getChildren().clear();
        for (Lotto lotto : lottiAssociati) {
            vBoxInfoLotti.getChildren().add(LottoCardFactory.createLottoCard(lotto));
        }
        anchorPaneInfo.setPrefHeight(anchorPaneInfo.getPrefHeight() + vBoxInfoLotti.getChildren().size() * 150 + 50);
    }

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

    public void setAttivitaDetails(){
        List<Colture> coltureList = coltureAssociate;
        vBoxInfoAttivita.getChildren().clear();
        for(Colture coltura : coltureList){
            Map<Attivita, List<Utente>> attivitaMap = attivitaDao.getAttivitaByIdColture(coltura.getIdColture());
            for(Map.Entry<Attivita, List<Utente>> entry : attivitaMap.entrySet()){
                Attivita attivita = entry.getKey();
                List<Utente> utenti = entry.getValue();
                System.out.println("Id_utente " + utenti.getFirst().getIdUtente());
                vBoxInfoAttivita.getChildren().add(AttivitaCardFactory.createAttivitaCard(attivita, utenti, coltura));
            }
        }
        anchorPaneInfo.setPrefHeight(anchorPaneInfo.getPrefHeight() + vBoxInfoAttivita.getChildren().size() * 200 + 50);

    }

    @FXML
    private void onScaricaDatiClicked() throws StatisticheColtura.StatisticheException {

        // Recupera le statistiche per tutti i lotti
        Map<Lotto, Map<Colture, StatisticheColtura>> statistiche = attivitaDao.getStatistichePerLottiEColtureByIdProgetto(progetto);
        // Debug: stampa i dati in console per capire se funziona
        statistiche.forEach((lotto, coltureStats) -> {
            System.out.println("📌 Lotto ID: " + lotto.getIdLotto());
            coltureStats.forEach((coltura, stats) -> {
                System.out.println("   🌱 Coltura ID: " + coltura.getIdColture());
                System.out.println("      Totale raccolte: " + stats.getTotaleRaccolte());
                System.out.println("      Media: " + stats.getMedia());
                System.out.println("      Min: " + stats.getMin());
                System.out.println("      Max: " + stats.getMax());
            });
        });
        // Genera il PDF
        ReportGenerator.generaReportPDF(statistiche, "report_statistiche.pdf");
    }


}
