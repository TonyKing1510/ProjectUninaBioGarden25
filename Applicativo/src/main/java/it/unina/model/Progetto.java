package it.unina.model;

import java.sql.Date;

/**
 * Rappresenta un Progetto creato da un utente proprietario, associato a una stagione.
 *
 * @author entn
 */
public class Progetto {

    /** Identificativo univoco del Progetto
     *  @author entn
     */
    private int idProgetto;

    /** Stagione associata al Progetto
     *  @author entn
     */
    private Stagione stagione;

    /** Utente che ha creato il Progetto, deve avere ruolo PROPRIETARIO
     *  @author entn
     */
    private Utente creatore;

    private Date dataInizio;
    private Date dataFine;
    private String titolo;
    private Lotto lotto;

    /**
     * Costruttore con validazione che assicura che solo un utente con ruolo PROPRIETARIO
     * possa creare un progetto.
     *
     *
     * @param stagione stagione associata al progetto
     * @param creatore utente creatore del progetto (ruolo PROPRIETARIO richiesto)
     * @param dataInizio data di inizio del progetto
     * @param dataFine data di fine del progetto
     *
     * @throws IllegalArgumentException se l'utente creatore non ha ruolo PROPRIETARIO
     *
     * @author entn
     */
    public Progetto(String titolo, Stagione stagione, Utente creatore, Date dataInizio, Date dataFine, Lotto lotto) {
        if (creatore.getRuolo() != Ruolo.PROPRIETARIO) {
            throw new IllegalArgumentException("Solo un utente con ruolo PROPRIETARIO può creare un progetto.");
        }
        this.titolo = titolo;
        this.stagione = stagione;
        this.creatore = creatore;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.lotto = lotto;
    }

    /**
     * Costruttore di default per Progetto.
     * Utilizzato principalmente per la serializzazione/deserializzazione.
     *
     * @author entn
     */
    public Progetto() {
        // Costruttore vuoto per serializzazione
    }

    /**
     * Restituisce l'identificativo univoco del Progetto.
     *
     * @return id del progetto
     *
     * @author entn
     */
    public int getIdProgetto() {
        return idProgetto;
    }

    /**
     * Restituisce la stagione associata al Progetto.
     *
     * @return stagione del progetto
     *
     * @author entn
     */
    public Stagione getStagione() {
        return stagione;
    }

    /**
     * Restituisce l'utente creatore del Progetto.
     *
     * @return utente creatore
     *
     * @author entn
     */
    public Utente getCreatore() {
        return creatore;
    }

    /**
     * Imposta l'identificativo univoco del Progetto.
     *
     * @param idProgetto nuovo id del progetto
     *
     * @author entn
     */
    public void setIdProgetto(int idProgetto) {
        this.idProgetto = idProgetto;
    }

    /**
     * Imposta la stagione associata al Progetto.
     *
     * @param stagione nuova stagione del progetto
     *
     * @author entn
     */
    public void setStagione(Stagione stagione) {
        this.stagione = stagione;
    }

    /**
     * Imposta l'utente creatore del Progetto.
     *
     * @param creatore nuovo utente creatore
     *
     * @author entn
     */
    public void setCreatore(Utente creatore) {
        this.creatore = creatore;
    }

    /**
     * Restituisce la data di inizio del Progetto.
     *
     * @return data di inizio del progetto
     *
     * @author entn
     */
    public Date getDataInizio() {
        return dataInizio;
    }
    /**
     * Imposta la data di inizio del Progetto.
     *
     * @param dataInizio nuova data di inizio del progetto
     *
     * @author entn
     */
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }
    /**
     * Restituisce la data di fine del Progetto.
     *
     * @return data di fine del progetto
     *
     * @author entn
     */
    public Date getDataFine() {
        return dataFine;
    }
    /**
     * Imposta la data di fine del Progetto.
     *
     * @param dataFine nuova data di fine del progetto
     *
     * @author entn
     */
    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    /**
     * Restituisce una rappresentazione in formato stringa del Progetto.
     *
     * @return stringa descrittiva del progetto
     *
     * @author entn
     */
    @Override
    public String toString() {
        return "Progetto{" +
                "idProgetto='" + idProgetto + '\'' +
                ", stagione='" + stagione + '\'' +
                ", creatore=" + (creatore != null ? creatore.getUsername() : "null") +
                '}';
    }

    public int getIdUtenteCreatore() {
        if (creatore != null) {
            return creatore.getIdUtente();
        } else {
            throw new IllegalStateException("Il creatore del progetto non è stato impostato.");
        }
    }

    public String getTitolo() {
        return titolo;
    }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
}
