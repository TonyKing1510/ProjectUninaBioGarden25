package it.unina.dao;

import it.unina.model.Attivita;
import it.unina.model.StatoAttivita;
import it.unina.model.TipoAttivita;

import java.sql.Date;
import java.util.List;

public interface AttivitaDAO {
    boolean addAttivita(Attivita attivita,int idColtura, int idColtivatore,int idProprietario);
    List<Attivita> getAttivitaCreate();
}
