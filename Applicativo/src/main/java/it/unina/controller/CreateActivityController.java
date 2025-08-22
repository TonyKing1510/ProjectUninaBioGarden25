package it.unina.controller;

import it.unina.model.Colture;
import it.unina.model.Utente;
import javafx.scene.control.CheckBox;

import java.util.List;

public class CreateActivityController {
    private Utente utenteLoggato;
    private List<CheckBox> coltureList;


    public CreateActivityController(Utente utenteLoggato, List<CheckBox> coltureList) {
        this.utenteLoggato = utenteLoggato;
        this.coltureList = coltureList;
    }
    CreateActivityController(List<CheckBox> coltureList) {
        this.coltureList = coltureList;
        this.utenteLoggato = null;
    }

    public void setUtenteLoggato(Utente utente) {
        this.utenteLoggato = utente;
    }

    public void setColtureCheckBox(List<CheckBox> coltureList) {
        this.coltureList = coltureList;
    }


}
