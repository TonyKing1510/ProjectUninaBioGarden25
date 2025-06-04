package it.unina.dao;
import it.unina.model.Utente;

import java.util.List;

public interface UtenteDAO {

    /**
     * Inserisce un nuovo utente nel database. (serve per la registrazione)
     * @param utente L'utente da inserire.
     */
    void addUtente(Utente utente);

    /**
     * Restituisce tutti gli utenti presenti nel database.
     * @return Lista di utenti.
     */
    List<Utente> getTuttiGliUtenti();

    /**
     * Cerca un utente per email e password (per login).
     * @param username L'email dell'utente.
     * @param password La password dell'utente.
     * @return L'utente trovato o null.
     */
    Utente login(String username, String password);

    /**
     * Controlla se esiste un utente con la mail fornita.
     * @param email Email da verificare.
     * @return true se l'utente esiste.
     */
    boolean esisteUtente(String email);
}

