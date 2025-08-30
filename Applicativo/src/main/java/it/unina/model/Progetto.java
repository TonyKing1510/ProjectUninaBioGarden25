package it.unina.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un Progetto creato da un utente proprietario, associato a una stagione.
 * Un Progetto può avere più Lotti associati e contiene informazioni su data di inizio, data di fine e titolo.
 * La validazione garantisce che solo utenti con ruolo PROPRIETARIO possano creare un progetto.
 *
 * @author entn
 */
public class Progetto {

    /** Identificativo univoco del Progetto */
    private int idProgetto;

    /** Stagione associata al Progetto */
    private Stagione stagione;

    /** Utente creatore del Progetto (deve avere ruolo PROPRIETARIO) */
    private Utente creatore;

    /** Data di inizio del Progetto */
    private Date dataInizio;

    /** Data di fine del Progetto */
    private Date dataFine;

    /** Titolo del Progetto */
    private String titolo;

    /** Lista dei Lotti ospitati nel Progetto */
    private List<Lotto> lottiOspitati = new ArrayList<>();

    /**
     * Costruttore completo che permette di creare un progetto con più lotti.
     * Valida che il creatore abbia ruolo PROPRIETARIO.
     *
     * @param titolo titolo del progetto
     * @param stagione stagione associata
     * @param creatore utente creatore (ruolo PROPRIETARIO richiesto)
     * @param dataInizio data di inizio
     * @param dataFine data di fine
     * @param lotti lista di lotti associati al progetto
     * @throws IllegalArgumentException se l'utente creatore non ha ruolo PROPRIETARIO
     * @author entn
     */
    public Progetto(String titolo, Stagione stagione, Utente creatore, Date dataInizio, Date dataFine, List<Lotto> lotti) {
        if (creatore.getRuolo() != Ruolo.PROPRIETARIO) {
            throw new IllegalArgumentException("Solo un utente con ruolo PROPRIETARIO può creare un progetto.");
        }
        this.titolo = titolo;
        this.stagione = stagione;
        this.creatore = creatore;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.lottiOspitati = lotti;
    }

    /**
     * Costruttore completo per creare un progetto con un solo lotto.
     * Valida che il lotto non sia null.
     *
     * @param titolo titolo del progetto
     * @param stagione stagione associata
     * @param creatore utente creatore (ruolo PROPRIETARIO richiesto)
     * @param dataInizio data di inizio
     * @param dataFine data di fine
     * @param lotto lotto da associare
     * @throws IllegalArgumentException se il lotto è null
     * @author entn
     */
    public Progetto(String titolo, Stagione stagione, Utente creatore, Date dataInizio, Date dataFine, Lotto lotto) {
        this.titolo = titolo;
        this.stagione = stagione;
        this.creatore = creatore;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.lottiOspitati = new ArrayList<>();
        if (lotto != null) {
            this.lottiOspitati.add(lotto);
        } else {
            throw new IllegalArgumentException("Il lotto non può essere null.");
        }
    }

    /**
     * Costruttore di default, utile per serializzazione/deserializzazione.
     * @author entn
     */
    public Progetto() {
        // Costruttore vuoto per serializzazione
    }

    /** Getter e Setter */

    /**
     * Restituisce l'identificativo univoco del progetto.
     * @return id del progetto
     * @author entn
     */
    public int getIdProgetto() {
        return idProgetto;
    }

    /**
     * Imposta l'identificativo univoco del progetto.
     * @param idProgetto nuovo id
     * @author entn
     */
    public void setIdProgetto(int idProgetto) {
        this.idProgetto = idProgetto;
    }

    /**
     * Restituisce la stagione associata al progetto.
     * @return stagione del progetto
     * @author entn
     */
    public Stagione getStagione() {
        return stagione;
    }

    /**
     * Imposta la stagione associata al progetto.
     * @param stagione nuova stagione
     * @author entn
     */
    public void setStagione(Stagione stagione) {
        this.stagione = stagione;
    }

    /**
     * Restituisce l'utente creatore del progetto.
     * @return creatore del progetto
     * @author entn
     */
    public Utente getCreatore() {
        return creatore;
    }

    /**
     * Imposta l'utente creatore del progetto.
     * @param creatore nuovo utente creatore
     * @author entn
     */
    public void setCreatore(Utente creatore) {
        this.creatore = creatore;
    }

    /**
     * Restituisce la data di inizio del progetto.
     * @return data di inizio
     * @author entn
     */
    public Date getDataInizio() {
        return dataInizio;
    }

    /**
     * Imposta la data di inizio del progetto.
     * @param dataInizio nuova data di inizio
     * @author entn
     */
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    /**
     * Restituisce la data di fine del progetto.
     * @return data di fine
     * @author entn
     */
    public Date getDataFine() {
        return dataFine;
    }

    /**
     * Imposta la data di fine del progetto.
     * @param dataFine nuova data di fine
     * @author entn
     */
    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    /**
     * Restituisce il titolo del progetto.
     * @return titolo del progetto
     * @author entn
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Imposta il titolo del progetto.
     * @param titolo nuovo titolo
     * @author entn
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * Restituisce la lista dei lotti ospitati nel progetto.
     * @return lista dei lotti
     * @author entn
     */
    public List<Lotto> getLottiOspitati() {
        return lottiOspitati;
    }

    /**
     * Imposta la lista dei lotti ospitati nel progetto.
     * @param lottiOspitati nuova lista di lotti
     * @author entn
     */
    public void setLottiOspitati(List<Lotto> lottiOspitati) {
        this.lottiOspitati = lottiOspitati;
    }

    /**
     * Aggiunge un lotto alla lista dei lotti ospitati.
     * @param lotto lotto da aggiungere (non null)
     * @throws IllegalArgumentException se il lotto è null
     * @author entn
     */
    public void addLotto(Lotto lotto) {
        if (lotto != null) {
            this.lottiOspitati.add(lotto);
        } else {
            throw new IllegalArgumentException("Il lotto non può essere null.");
        }
    }

    /**
     * Restituisce l'id dell'utente creatore.
     * @return id dell'utente creatore
     * @throws IllegalStateException se il creatore non è stato impostato
     * @author entn
     */
    public int getIdUtenteCreatore() {
        if (creatore != null) {
            return creatore.getIdUtente();
        } else {
            throw new IllegalStateException("Il creatore del progetto non è stato impostato.");
        }
    }

    /**
     * Restituisce una rappresentazione testuale del progetto.
     * @return stringa descrittiva
     * @author entn
     */
    @Override
    public String toString() {
        return "Progetto{" +
                "idProgetto=" + idProgetto +
                ", stagione=" + stagione +
                ", creatore=" + (creatore != null ? creatore.getUsername() : "null") +
                '}';
    }
}
