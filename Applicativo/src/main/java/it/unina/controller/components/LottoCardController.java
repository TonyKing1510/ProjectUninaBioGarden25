package it.unina.controller.components;

import it.unina.dao.LottoDAO;
import it.unina.implementazionePostgreSQL.LottoDAOImpl;
import it.unina.model.Lotto;
import it.unina.model.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class LottoCardController {

    @FXML
    private Label lottoLabelTitle;
    @FXML
    private Label superficieLabel;
    @FXML
    private Label IndirizzoLabel;
    @FXML
    private Label coltureLabel;

    private Utente utente;
    private Lotto lotto;


    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public void setLotti(Lotto Lotto) {
        this.lotto = Lotto;
        lottoLabelTitle.setText("Lotto : #" + lotto.getIdLotto());
        superficieLabel.setText(String.valueOf(lotto.getSuperficie()));
        IndirizzoLabel.setText(lotto.getIndirizzo());
        coltureLabel.setText("Colture: " + String.join(", ", (CharSequence) lotto.getColture()));
    }

}
