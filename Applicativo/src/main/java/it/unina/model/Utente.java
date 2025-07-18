package it.unina.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un utente registrato nella piattaforma UninaBioGarden.
 * Ogni utente può avere il ruolo di PROPRIETARIO o COLTIVATORE.
 * Contiene le informazioni anagrafiche e le credenziali di accesso.
 *
 * @author entn
 * @version 1.0
 */
public class Utente {
    private int idUtente;
    private String nome;
    private String cognome;
    private String mail;
    private String password;
    private Ruolo ruolo;
    private String username;

    private List<Progetto> progettiCreati = new ArrayList<>();

    public List<Progetto> getProgettiCreati() {
        return progettiCreati;
    }

    public void aggiungiProgettoCreato(Progetto progetto) {
        progettiCreati.add(progetto);
    }

    private List<Attivita> attivitaColtivatore = new ArrayList<>();

    /**
     * Costruttore per creare un oggetto Utente completo.
     *
     * @param idUtente identificatore univoco dell'utente
     * @param nome     nome dell'utente
     * @param cognome  cognome dell'utente
     * @param mail     indirizzo email univoco dell'utente
     * @param password password dell'utente (preferibilmente hashata)
     * @param ruolo    ruolo assegnato all'utente (PROPRIETARIO o COLTIVATORE)
     * @param username
     * @author entn
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
     * @param nome    nome dell'utente
     * @param cognome cognome dell'utente
     * @param mail    indirizzo email univoco dell'utente
     * @param password password dell'utente (preferibilmente hashata)
     * @param ruolo   ruolo assegnato all'utente (PROPRIETARIO o COLTIVATORE)
     * @param username
     * @author entn
     */
    public Utente(String nome, String cognome, String mail, String password, Ruolo ruolo, String username) {
        this.nome = nome;
        this.cognome = cognome;
        this.mail = mail;
        this.password = password;
        this.ruolo = ruolo;
        this.username = username;
    }

    public Utente(String nome,String cognome, String mail, String password,Ruolo ruolo,String username,List<Attivita> attivitaColtivatore) {
        this.nome = nome;
        this.cognome = cognome;
        this.mail = mail;
        this.password = password;
        this.ruolo = ruolo;
        this.username = username;
        if(ruolo == Ruolo.COLTIVATORE) {
            this.attivitaColtivatore = attivitaColtivatore;
        } else {
            System.out.println("L'utente non è un coltivatore, non sono previste attività.");
        }
    }

    /**
     * Restituisce l'ID dell'utente.
     *
     * @return idUtente
     * @author entn
     */
    public int getIdUtente() {
        return idUtente;
    }

    /**
     * Imposta l'ID dell'utente.
     *
     * @param idUtente nuovo ID da assegnare
     * @author entn
     */
    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    /**
     * Restituisce il nome dell'utente.
     *
     * @return nome
     * @author entn
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome dell'utente.
     *
     * @param nome nuovo nome
     * @author entn
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il cognome dell'utente.
     *
     * @return cognome
     * @author entn
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il cognome dell'utente.
     *
     * @param cognome nuovo cognome
     * @author entn
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Restituisce l'indirizzo email dell'utente.
     *
     * @return email
     * @author entn
     */
    public String getMail() {
        return mail;
    }

    /**
     * Imposta l'indirizzo email dell'utente.
     *
     * @param mail nuovo indirizzo email
     * @author entn
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Restituisce la password dell'utente.
     *
     * @return password
     * @author entn
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password dell'utente.
     *
     * @param password nuova password (hashata)
     * @author entn
     */
    private void setPassword(String password) {
        this.password = password;
    }

    /**
     * Restituisce il ruolo dell'utente.
     *
     * @return ruolo
     * @author entn
     */
    public Ruolo getRuolo() {
        return ruolo;
    }

    /**
     * Imposta il ruolo dell'utente.
     *
     * @param ruolo ruolo da assegnare (PROPRIETARIO o COLTIVATORE)
     * @author entn
     */
    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
