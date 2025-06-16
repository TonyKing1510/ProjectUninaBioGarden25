package it.unina.dao;

import it.unina.model.Lotto;

import java.util.List;

public interface LottoDAO {
    public List<Lotto> getLottiDisponibili();

    public Lotto getLottoById(int id);


}
