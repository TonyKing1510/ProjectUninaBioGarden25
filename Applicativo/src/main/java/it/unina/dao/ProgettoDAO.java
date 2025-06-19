package it.unina.dao;

import it.unina.model.Lotto;
import it.unina.model.Progetto;

import java.util.List;


/**
 * * Interfaccia per la gestione dei progetti.
 * Contiene i metodi per aggiungere un progetto e recuperare un progetto per ID.
 * @author entn
 */
public interface ProgettoDAO {
    /** Aggiunge un nuovo progetto al database.
     * @param progetto Il progetto da aggiungere.
     * @return true se il progetto è stato aggiunto con successo, false altrimenti.
     * @author entn
     */
    boolean addProgettoAndUpdateLotto(Progetto progetto, Lotto lotto);


    List<Progetto> getProgettiByIdUtente(int idUtente);

    int getIdProgettoByTitolo(String titolo);

}
