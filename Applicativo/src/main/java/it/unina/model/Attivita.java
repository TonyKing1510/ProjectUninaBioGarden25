package it.unina.model;

import java.sql.Date;

/**
 * Rappresenta un'attività svolta su una coltura all'interno di un progetto.
 * <p>
 * Un'attività può avere uno stato ({@link StatoAttivita}) e un tipo ({@link TipoAttivita}),
 * quantità raccolta, quantità usata, data di inizio e data di fine.
 * <p>
 * Questa classe fornisce getter e setter per tutti i campi e un metodo {@link #toString()}
 * per la rappresentazione testuale dell'oggetto.
 *
 * @author entn
 */
public class Attivita {

    /** Identificativo univoco dell'attività */
    private int idAttivita;

    /** Stato dell'attività (es. IN_CORSO, COMPLETATA, PROGRAMMATA) */
    private StatoAttivita stato;

    /** Tipo di attività (es. SEMINA, RACCOLTA, IRRIGAZIONE) */
    private TipoAttivita tipo;

    /** Quantità raccolta durante l'attività (se applicabile) */
    private int quantitaRaccolta;

    /** Quantità di materiale usata durante l'attività (se applicabile) */
    private int quantitaUsata;

    /** Data di inizio dell'attività */
    private Date dataInizio;

    /** Data di fine dell'attività */
    private Date dataFine;

    private Utente responsabile;
    public Utente getResponsabile(){return responsabile;}
    public void setResponsabile(Utente u){
        this.responsabile = u;
    }

    private Colture coltura;

    public Colture getColtura() {
        return coltura;
    }

    public int setColtura(Colture coltura) {
        this.coltura = coltura;
        return coltura.getIdColture();
    }


    /**
     * Costruisce un oggetto {@code Attivita} completo.
     *
     * @param stato stato dell'attività
     * @param tipo tipo di attività
     * @param quantitaRaccolta quantità raccolta (se applicabile)
     * @param quantitaUsata quantità di materiale usato (se applicabile)
     * @param dataInizio data di inizio dell'attività
     * @param dataFine data di fine dell'attività
     */
    public Attivita(StatoAttivita stato, TipoAttivita tipo, int quantitaRaccolta, int quantitaUsata, Date dataInizio, Date dataFine) {
        this.stato = stato;
        this.tipo = tipo;
        this.quantitaRaccolta = quantitaRaccolta;
        this.quantitaUsata = quantitaUsata;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    /**
     * Costruttore vuoto per creare un'attività senza inizializzare i campi.
     */
    public Attivita() {
    }

    /**
     * Restituisce lo stato dell'attività.
     *
     * @return stato dell'attività
     */
    public StatoAttivita getStato() {
        return stato;
    }

    /**
     * Imposta lo stato dell'attività.
     *
     * @param stato nuovo stato dell'attività
     */
    public void setStato(StatoAttivita stato) {
        this.stato = stato;
    }

    /**
     * Restituisce il tipo di attività.
     *
     * @return tipo di attività
     */
    public TipoAttivita getTipo() {
        return tipo;
    }

    /**
     * Imposta il tipo di attività.
     *
     * @param tipo nuovo tipo di attività
     */
    public void setTipo(TipoAttivita tipo) {
        this.tipo = tipo;
    }

    /**
     * Restituisce la quantità raccolta.
     *
     * @return quantità raccolta
     */
    public int getQuantitaRaccolta() {
        return quantitaRaccolta;
    }

    /**
     * Imposta la quantità raccolta.
     *
     * @param quantitaRaccolta nuova quantità raccolta
     */
    public void setQuantitaRaccolta(int quantitaRaccolta) {
        this.quantitaRaccolta = quantitaRaccolta;
    }

    /**
     * Restituisce la quantità di materiale usata.
     *
     * @return quantità usata
     */
    public int getQuantitaUsata() {
        return quantitaUsata;
    }

    /**
     * Imposta la quantità di materiale usata.
     *
     * @param quantitaUsata nuova quantità usata
     */
    public void setQuantitaUsata(int quantitaUsata) {
        this.quantitaUsata = quantitaUsata;
    }

    /**
     * Restituisce la data di inizio dell'attività.
     *
     * @return data di inizio
     */
    public Date getDataInizio() {
        return dataInizio;
    }

    /**
     * Imposta la data di inizio dell'attività.
     *
     * @param dataInizio nuova data di inizio
     */
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    /**
     * Restituisce la data di fine dell'attività.
     *
     * @return data di fine
     */
    public Date getDataFine() {
        return dataFine;
    }

    /**
     * Imposta la data di fine dell'attività.
     *
     * @param dataFine nuova data di fine
     */
    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    /**
     * Restituisce l'identificativo dell'attività.
     *
     * @return id dell'attività
     */
    public int getIdAttivita() {
        return idAttivita;
    }

    /**
     * Imposta l'identificativo dell'attività.
     *
     * @param idAttivita nuovo id dell'attività
     */
    public void setIdAttivita(int idAttivita) {
        this.idAttivita = idAttivita;
    }

    /**
     * Restituisce una rappresentazione testuale dell'attività.
     *
     * @return stringa descrittiva dell'attività
     */
    @Override
    public String toString() {
        return "Attivita{" +
                "stato=" + stato +
                ", tipo=" + tipo +
                ", quantitaRaccolta=" + quantitaRaccolta +
                ", quantitaUsata=" + quantitaUsata +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                '}';
    }
}
