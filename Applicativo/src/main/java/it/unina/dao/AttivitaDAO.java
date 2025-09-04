package it.unina.dao;

import it.unina.stats.StatisticheColtura;
import it.unina.model.*;

import java.util.List;
import java.util.Map;

/**
 * Interfaccia per la gestione delle attività.
 * Fornisce metodi per creare, recuperare, aggiornare attività e
 * ottenere statistiche relative a colture e lotti.
 *
 * Tutti i metodi di recupero restituiscono strutture vuote se non ci sono risultati.
 * Gli aggiornamenti modificano lo stato delle attività o altri attributi nel database.
 *
 * @author entn
 */
public interface AttivitaDAO {

    /**
     * Aggiunge una nuova attività al database, associandola a una coltura, a un coltivatore
     * e a un proprietario.
     *
     * @param attivita       L’attività da aggiungere.
     * @param idColtura      ID della coltura associata all’attività.
     * @param idColtivatore  ID del coltivatore responsabile dell’attività.
     * @param idProprietario ID del proprietario del lotto/progetto.
     * @return true se l’inserimento è andato a buon fine, false in caso di errore.
     * @author entn
     */
    boolean addAttivita(Attivita attivita, int idColtura, int idColtivatore, int idProprietario);

    /**
     * Recupera tutte le attività create nel sistema.
     *
     * @return Lista di tutte le attività presenti nel database.
     * @author entn
     */
    List<Attivita> getAttivitaCreate();

    /**
     * Recupera le attività associate a una determinata coltura e i rispettivi coltivatori.
     *
     * @param idColtura ID della coltura.
     * @return Mappa con chiave l’attività e valore la lista di coltivatori associati.
     * @author entn
     */
    Map<Attivita, List<Utente>> getAttivitaByIdColture(int idColtura);

    /**
     * Recupera le statistiche delle colture per tutti i lotti associati a un progetto.
     *
     * @param progetto Progetto per cui calcolare le statistiche.
     * @return Mappa annidata: chiave Lotto, valore mappa Colture → StatisticheColtura.
     * @throws StatisticheColtura.StatisticheException Se il calcolo delle statistiche fallisce.
     * @author entn
     */
    Map<Lotto, Map<Colture, StatisticheColtura>> getStatistichePerLottiEColtureByIdProgetto(Progetto progetto)
            throws StatisticheColtura.StatisticheException;

    /**
     * Aggiorna i dati di un’attività esistente, ad esempio lo stato o altre informazioni.
     *
     * @param attivita L’attività da aggiornare nel database.
     * @author entn
     */
    void updateAttivita(Attivita attivita);
}
