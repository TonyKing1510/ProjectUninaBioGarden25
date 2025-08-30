package it.unina.controller.components;

import it.unina.dao.ColtureDAO;
import it.unina.implementazionepostgresql.ColtureDAOImpl;
import it.unina.model.Colture;
import it.unina.model.Lotto;
import it.unina.model.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
    @FXML
    private Label numeroColtivatoriLabel;

    private Utente utente;
    private Lotto lotto;

    private ColtureDAO coltureDAO = new ColtureDAOImpl();


    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public void setLotti(Lotto Lotto) {
        this.lotto = Lotto;
        lottoLabelTitle.setText("Lotto : #" + lotto.getIdLotto());
        superficieLabel.setText(String.valueOf(lotto.getSuperficie()));
        IndirizzoLabel.setText(lotto.getIndirizzo());

        setColtureInLotti(lotto);
    }

    public void setColtureInLotti(Lotto lotto){
        ColtureDAO coltureDAO = new ColtureDAOImpl();
        List<Colture> colture = coltureDAO.getColtureByIdLotto(lotto.getIdLotto());

        StringBuilder titoliBuilder = new StringBuilder(" ");

        for (int i = 0; i < colture.size(); i++) {
            titoliBuilder.append(colture.get(i).getTitolo());

            // Aggiungi la virgola se non è l'ultimo elemento
            if (i < colture.size() - 1) {
                titoliBuilder.append(", ");
            }
        }

        coltureLabel.setText(titoliBuilder.toString());
    }



}
