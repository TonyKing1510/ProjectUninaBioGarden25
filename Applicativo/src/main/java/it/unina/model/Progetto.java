package it.unina.model;

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

    /**
     * Costruttore con validazione che assicura che solo un utente con ruolo PROPRIETARIO
     * possa creare un progetto.
     *
     * @param idProgetto identificativo univoco del progetto
     * @param stagione stagione associata al progetto
     * @param creatore utente creatore del progetto (ruolo PROPRIETARIO richiesto)
     *
     * @throws IllegalArgumentException se l'utente creatore non ha ruolo PROPRIETARIO
     *
     * @author entn
     */
    public Progetto(int idProgetto, Stagione stagione, Utente creatore) {
        if (creatore.getRuolo() != Ruolo.PROPRIETARIO) {
            throw new IllegalArgumentException("Solo un utente con ruolo PROPRIETARIO può creare un progetto.");
        }
        this.idProgetto = idProgetto;
        this.stagione = stagione;
        this.creatore = creatore;
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
}
