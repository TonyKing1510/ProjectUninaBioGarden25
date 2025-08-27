package it.unina.dao;

import it.unina.model.Lotto;

import java.util.List;

public interface LottoDAO {
    List<Lotto> getLottiDisponibili(int id);

    Lotto getLottoById(int id);

    Lotto getLottoByIdProgetto(int idProgetto);

    List<Lotto> getLottoByIdProprietario(int idProprietario);

    List<Lotto> getLottiByIdProgetto(int idProgetto);

}
