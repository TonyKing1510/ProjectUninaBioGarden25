package it.unina.dao;

import it.unina.model.Notifica;

import java.util.List;

/**
 * Interfaccia per la gestione delle operazioni CRUD delle notifiche.
 * Include metodi per inserire, aggiornare, eliminare e recuperare notifiche.
 */
public interface NotificaDAO {

    /**
     * Inserisce una nuova notifica nel database.
     * @param notifica oggetto Notifica da salvare
     * @return true se l'inserimento ha avuto successo, false altrimenti
     * @author Sderr12
     */
    boolean inserisciNotifica(Notifica notifica);

    /**
     * Recupera tutte le notifiche create da un determinato proprietario.
     * @param idProprietario ID del proprietario
     * @return lista di notifiche
     * @author Sderr12 
     */
    List<Notifica> getNotifichePerProprietario(int idProprietario);

    /**
     * Recupera tutte le notifiche associate a un determinato lotto.
     * @param idLotto ID del lotto
     * @return lista di notifiche
     * @author Sderr12
     */
    List<Notifica> getNotifichePerLotto(int idLotto);

    /**
     * Recupera tutte le notifiche indirizzate a un destinatario specifico.
     * @param idDestinatario ID del destinatario
     * @return lista di notifiche
     * @author Sderr12
     */
    List<Notifica> getNotifichePerDestinatario(int idDestinatario);

    /**
     * Elimina una notifica dal database tramite il suo ID.
     * @param id ID della notifica da eliminare
     * @return true se l'eliminazione ha avuto successo, false altrimenti
     * @author Sderr12
     */
    boolean eliminaNotifica(int id);
}
