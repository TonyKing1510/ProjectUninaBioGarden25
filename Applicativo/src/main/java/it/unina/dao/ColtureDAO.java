package it.unina.dao;

import it.unina.model.Colture;

import java.util.List;
import java.util.Map;

public interface ColtureDAO {

    List<Colture> getColtureByIdLotto(int idLotto);

    List<Colture> getColtureDisponibili();
    List<Colture> getColturaById(int idColtura);

    boolean salvaAssociazioni(Map<Colture, Integer> associazioni);

}
