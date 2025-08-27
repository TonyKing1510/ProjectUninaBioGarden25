package it.unina.controller;

import it.unina.Factory.LottoCardFactory;
import it.unina.dao.LottoDAO;
import it.unina.dao.ProgettoDAO;
import it.unina.implementazionePostgreSQL.LottoDAOImpl;
import it.unina.implementazionePostgreSQL.ProgettoDAOImpl;
import it.unina.model.Lotto;
import it.unina.model.Progetto;
import it.unina.model.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

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





    public ProgettiDetailsController() {

    }

    private Progetto progetto;
    private Utente utente;
    private final LottoDAO lottoDao = new LottoDAOImpl();
    private List<Lotto> lottiAssociati = new ArrayList<>();


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
    }


}
