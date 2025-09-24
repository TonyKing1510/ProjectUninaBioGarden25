package it.unina.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un utente registrato nella piattaforma UninaBioGarden.
 * Ogni utente può avere il ruolo di PROPRIETARIO o COLTIVATORE.
 * Contiene le informazioni anagrafiche e le credenziali di accesso.
 * <p>
 * Mantiene una lista dei progetti creati dall'utente.
 *
 * @author entn
 * @version 1.0
 */
public class Utente {

    /** Identificativo univoco dell'utente */
    private int idUtente;

    /** Nome dell'utente */
    private String nome;

    /** Cognome dell'utente */
    private String cognome;

    /** Indirizzo email dell'utente */
    private String mail;

    /** Password dell'utente (preferibilmente hashata) */
    private String password;

    /** Ruolo dell'utente (PROPRIETARIO o COLTIVATORE) */
    private Ruolo ruolo;

    /** Username dell'utente */
    private String username;

    /** Lista dei progetti creati dall'utente */
    private List<Progetto> progettiCreati = new ArrayList<>();

    public Attivita getAttivita() {
        return attivita;
    }

    public void setAttivita(Attivita attivita) {
        this.attivita = attivita;
    }

    private Attivita attivita;

    /**
     * Costruttore vuoto.
     */
    public Utente() {
    }

    /**
     * Restituisce la lista dei progetti creati dall'utente.
     *
     * @return lista di progetti creati
     */
    public List<Progetto> getProgettiCreati() {
        return progettiCreati;
    }

    /**
     * Aggiunge un progetto alla lista dei progetti creati dall'utente.
     *
     * @param progetto progetto da aggiungere
     */
    public void aggiungiProgettoCreato(Progetto progetto) {
        progettiCreati.add(progetto);
    }

    /**
     * Costruttore completo per creare un oggetto Utente con tutti i campi.
     *
     * @param idUtente identificatore univoco dell'utente
     * @param nome     nome dell'utente
     * @param cognome  cognome dell'utente
     * @param mail     indirizzo email univoco
     * @param password password dell'utente (hashata)
     * @param ruolo    ruolo assegnato all'utente
     * @param username username dell'utente
     */
    public Utente(int idUtente, String nome, String cognome, String mail, String password, Ruolo ruolo, String username) {
        this.idUtente = idUtente;
        this.nome = nome;
        this.cognome = cognome;
        this.mail = mail;
        this.password = password;
        this.ruolo = ruolo;
        this.username = username;
    }

    /**
     * Costruttore per creare un oggetto Utente senza ID.
     * Utilizzato per la registrazione di nuovi utenti.
     *
     * @param nome     nome dell'utente
     * @param cognome  cognome dell'utente
     * @param mail     indirizzo email univoco
     * @param password password dell'utente (hashata)
     * @param ruolo    ruolo assegnato all'utente
     * @param username username dell'utente
     */
    public Utente(String nome, String cognome, String mail, String password, Ruolo ruolo, String username) {
        this.nome = nome;
        this.cognome = cognome;
        this.mail = mail;
        this.password = password;
        this.ruolo = ruolo;
        this.username = username;
    }

    /**
     * Restituisce l'ID dell'utente.
     *
     * @return identificatore univoco dell'utente
     */
    public int getIdUtente() {
        return idUtente;
    }

    /**
     * Imposta l'ID dell'utente.
     *
     * @param idUtente nuovo ID da assegnare
     */
    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    /**
     * Restituisce il nome dell'utente.
     *
     * @return nome dell'utente
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome dell'utente.
     *
     * @param nome nuovo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il cognome dell'utente.
     *
     * @return cognome dell'utente
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il cognome dell'utente.
     *
     * @param cognome nuovo cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Restituisce l'indirizzo email dell'utente.
     *
     * @return email dell'utente
     */
    public String getMail() {
        return mail;
    }

    /**
     * Imposta l'indirizzo email dell'utente.
     *
     * @param mail nuovo indirizzo email
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Restituisce la password dell'utente.
     *
     * @return password dell'utente
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password dell'utente.
     *
     * @param password nuova password (hashata)
     */
    private void setPassword(String password) {
        this.password = password;
    }

    /**
     * Restituisce il ruolo dell'utente.
     *
     * @return ruolo dell'utente
     */
    public Ruolo getRuolo() {
        return ruolo;
    }

    /**
     * Imposta il ruolo dell'utente.
     *
     * @param ruolo ruolo da assegnare (PROPRIETARIO o COLTIVATORE)
     */
    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    /**
     * Restituisce lo username dell'utente.
     *
     * @return username dell'utente
     */
    public String getUsername() {
        return username;
    }

    /**
     * Imposta lo username dell'utente.
     *
     * @param username nuovo username
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
