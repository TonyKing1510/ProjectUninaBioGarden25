package it.unina.dao;

import it.unina.Stats.StatisticheColtura;
import it.unina.model.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface AttivitaDAO {
    boolean addAttivita(Attivita attivita,int idColtura, int idColtivatore,int idProprietario);
    List<Attivita> getAttivitaCreate();

    Map<Attivita, List<Utente>> getAttivitaByIdColture(int idColtura);

    Map<Lotto, Map<Colture, StatisticheColtura>> getStatistichePerLottiEColtureByIdProgetto(Progetto progetto);

    void updateAttivita(Attivita attivita);
}
