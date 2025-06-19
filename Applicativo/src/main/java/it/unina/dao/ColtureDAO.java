package it.unina.dao;

import it.unina.model.Colture;

import java.util.List;

public interface ColtureDAO {

    List<Colture> getColtureByIdLotto(int idLotto);


}
