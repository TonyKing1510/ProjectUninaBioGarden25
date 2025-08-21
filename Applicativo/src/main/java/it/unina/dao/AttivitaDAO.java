package it.unina.dao;

import it.unina.model.Attivita;
import it.unina.model.StatoAttivita;
import it.unina.model.TipoAttivita;

import java.sql.Date;
import java.util.List;

public interface AttivitaDAO {
    void addAttivita(Attivita attivita);
    List<Attivita> getAttivitaCreate();
}
