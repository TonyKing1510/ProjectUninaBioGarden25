package it.unina.controller;

import it.unina.dao.LottoDAO;
import it.unina.gui.LottiGUI;
import it.unina.implementazionePostgreSQL.LottoDAOImpl;
import it.unina.model.Lotto;
import it.unina.model.Utente;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class LottiController {
    @FXML
    private VBox contentBox;

    private Utente utenteproprietario;

    private LottoDAO lottoDAO = new LottoDAOImpl();

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            try {
                loadLotti();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void setUtente(Utente utente) {
        this.utenteproprietario = utente;
    }

    public void loadLotti() throws IOException {
        List<Lotto> lotti = lottoDAO.getLottoByIdProprietario(utenteproprietario.getIdUtente());
        for (Lotto lotto : lotti) {
            LottiGUI.initLottiCard(contentBox,utenteproprietario,lotto);
        }
    }
}
