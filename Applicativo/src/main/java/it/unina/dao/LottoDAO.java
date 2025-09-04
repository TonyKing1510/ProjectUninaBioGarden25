package it.unina.dao;

import it.unina.model.Lotto;

import java.util.List;

/**
 * Interfaccia per la gestione dei lotti.
 * Fornisce metodi per recuperare lotti disponibili, lotti per ID,
 * lotti associati a un progetto o a un proprietario specifico.
 *
 * Tutti i metodi di recupero restituiscono liste vuote se non ci sono risultati.
 * Ogni metodo può sollevare eccezioni interne per errori di connessione al database.
 *
 * @author entn
 */
public interface LottoDAO {

    /**
     * Recupera i lotti disponibili di un proprietario (ossia non associati a nessun progetto).
     *
     * @param id ID del proprietario.
     * @return Lista dei lotti disponibili di quell’utente.
     * @author entn
     */
    List<Lotto> getLottiDisponibili(int id);

    /**
     * Recupera un lotto a partire dal suo ID.
     *
     * @param id ID del lotto.
     * @return Il lotto corrispondente all’ID, oppure null se non trovato.
     * @author entn
     */
    Lotto getLottoById(int id);

    /**
     * Recupera un lotto associato a un progetto specifico.
     *
     * @param idProgetto ID del progetto.
     * @return Il lotto associato al progetto, oppure null se non esiste.
     * @author entn
     */
    Lotto getLottoByIdProgetto(int idProgetto);

    /**
     * Recupera tutti i lotti appartenenti a un determinato proprietario.
     *
     * @param idProprietario ID del proprietario.
     * @return Lista dei lotti di quel proprietario.
     * @author entn
     */
    List<Lotto> getLottoByIdProprietario(int idProprietario);

    /**
     * Recupera tutti i lotti associati a un progetto specifico.
     *
     * @param idProgetto ID del progetto.
     * @return Lista dei lotti collegati al progetto.
     * @author entn
     */
    List<Lotto> getLottiByIdProgetto(int idProgetto);
}
