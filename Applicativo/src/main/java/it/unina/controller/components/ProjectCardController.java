package it.unina.controller.components;

import it.unina.gui.ProgettoDetailsGUI;
import it.unina.model.Progetto;
import it.unina.gui.VisualizeProjectGUI;
import it.unina.model.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


import java.io.IOException;


public class ProjectCardController {

    @FXML
    private Label nomeProgetto;
    @FXML
    private Label stagioneLabel;
    @FXML
    private Label dataInizioLabel;
    @FXML
    private Label dataFineLabel;

    private Progetto progetto;
    private Utente utente;

    public void setProgetto(Progetto progetto){
        this.progetto= progetto;
    }

    public void setUtente(Utente utente){
        this.utente = utente;
    }

    public void setProgettoDetails(Progetto progetto) {
        System.out.println("Setting project details for: " + progetto.getIdProgetto());
        nomeProgetto.setText(progetto.getTitolo());
        stagioneLabel.setText(progetto.getStagione().toString()); // Enum in camelCase?
        dataInizioLabel.setText(progetto.getDataInizio().toString());
        dataFineLabel.setText(progetto.getDataFine().toString());
    }


    @FXML private void onProjectClick() throws IOException {
        ProgettoDetailsGUI.openPageProgettoDetailsGUI(progetto, utente);
    }

}
