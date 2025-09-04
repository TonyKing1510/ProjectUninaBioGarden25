package it.unina.dao;

import it.unina.model.Colture;
import it.unina.model.Lotto;
import it.unina.model.Progetto;
import it.unina.model.Stagione;

import java.util.List;

/**
 * Interfaccia per la gestione dei progetti.
 * Contiene i metodi per aggiungere, aggiornare e recuperare progetti dal database.
 * Fornisce anche funzionalità di filtro e ricerca dei progetti.
 *
 * @author entn
 */
public interface ProgettoDAO {

    /**
     * Aggiunge un nuovo progetto al database e aggiorna i lotti selezionati associandoli
     * al progetto appena creato.
     * <p>
     * L’operazione dovrebbe essere atomica: se l’inserimento del progetto o
     * l’aggiornamento dei lotti fallisce, entrambe le operazioni vengono annullate.
     * </p>
     *
     * @param progetto Il progetto da aggiungere.
     * @param lotti    Lista dei lotti da associare al progetto.
     * @return true se il progetto è stato aggiunto e i lotti aggiornati con successo,
     *         false in caso di errore.
     * @author entn
     */
    boolean addProgettoAndUpdateLotto(Progetto progetto, List<Lotto> lotti);

    /**
     * Recupera tutti i progetti creati da un determinato utente.
     *
     * @param idUtente ID dell’utente creatore.
     * @return Lista dei progetti associati all’utente. La lista è vuota
     *         se non ci sono progetti.
     * @author entn
     */
    List<Progetto> getProgettiByIdUtente(int idUtente);

    /**
     * Recupera l’ID di un progetto a partire dal suo titolo.
     *
     * @param titolo Titolo del progetto.
     * @return ID del progetto se trovato, -1 altrimenti.
     * @author entn
     */
    int getIdProgettoByTitolo(String titolo);

    /**
     * Aggiunge un nuovo progetto associandolo a un proprietario specifico.
     *
     * @param progetto     Il progetto da aggiungere.
     * @param idProprietario ID del proprietario del progetto.
     * @return true se l’inserimento è andato a buon fine, false altrimenti.
     * @author entn
     */
    boolean addProgetto(Progetto progetto, int idProprietario);

    /**
     * Recupera i progetti filtrandoli in base al proprietario, alla stagione
     * e alla superficie minima dei lotti associati.
     *
     * @param idProprietario ID del proprietario dei progetti.
     * @param stagione       Stagione dei progetti (può essere null per ignorare il filtro).
     * @param superficie     Superficie minima dei lotti (può essere null per ignorare il filtro).
     * @return Lista dei progetti che soddisfano i criteri di filtro.
     * @author entn
     */
    List<Progetto> getProgettiWithFilter(int idProprietario, Stagione stagione, Double superficie);
}
