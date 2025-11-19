package it.unina.dao;

import it.unina.model.Colture;

import java.util.List;
import java.util.Map;

/**
 * Interfaccia per la gestione delle colture.
 * Fornisce metodi per recuperare colture associate a lotti, colture disponibili,
 * e per salvare le associazioni tra colture e altre entità (es. progetti o attività).
 *
 * Tutti i metodi di recupero restituiscono liste vuote se non ci sono risultati.
 * Il metodo di salvataggio restituisce true se l’operazione è andata a buon fine.
 *
 * @author entn
 */
public interface ColtureDAO {

    /**
     * Recupera tutte le colture associate a un determinato lotto.
     *
     * @param idLotto ID del lotto.
     * @return Lista di colture presenti nel lotto specificato.
     * @author entn
     */
    List<Colture> getColtureByIdLotto(int idLotto);

    /**
     * Recupera tutte le colture disponibili nel sistema.
     *
     * @return Lista di tutte le colture disponibili.
     * @author entn
     */
    List<Colture> getColtureDisponibili();

    /**
     * Recupera una coltura specifica a partire dal suo ID.
     *
     * @param idColtura ID della coltura.
     * @return Lista contenente la coltura trovata, vuota se non esiste.
     * @author entn
     */
    Colture getColturaById(int idColtura);

    /**
     * Salva le associazioni tra colture e altre entità (ad esempio lotti o progetti).
     *
     * @param associazioni Mappa con chiave la coltura e valore l’ID dell’entità associata.
     * @return true se tutte le associazioni sono state salvate con successo, false in caso di errore.
     * @author entn
     */
    boolean salvaAssociazioni(Map<Colture, Integer> associazioni);
}
