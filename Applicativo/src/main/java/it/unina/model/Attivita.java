package it.unina.model;

import java.sql.Date;

public class Attivita {
    private int idAttivita;
    private StatoAttivita stato;
    private TipoAttivita tipo;
    private int quantitaRaccolta;
    private int quantitaUsata;
    private Date dataInizio;
    private Date dataFine;

    /**
     * Costruttore per creare un'attività completa.
     *
     * @param stato         stato dell'attività (IN_CORSO, CONCLUSA, ANNULLATA)
     * @param tipo          tipo di attività (PIANTAGIONE, RACCOLTA, MANUTENZIONE)
     * @param quantitaRaccolta quantità raccolta (se applicabile)
     * @param quantitaUsata quantità di materiale usato (se applicabile)
     * @param dataInizio    data di inizio dell'attività
     * @param dataFine      data di fine dell'attività
     */
    public Attivita(StatoAttivita stato, TipoAttivita tipo, int quantitaRaccolta, int quantitaUsata, Date dataInizio, Date dataFine) {
        this.stato = stato;
        this.tipo = tipo;
        this.quantitaRaccolta = quantitaRaccolta;
        this.quantitaUsata = quantitaUsata;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public Attivita() {

    }

    public StatoAttivita getStato() {
        return stato;
    }
    public void setStato(StatoAttivita stato) {
        this.stato = stato;
    }
    public TipoAttivita getTipo() {
        return tipo;
    }
    public void setTipo(TipoAttivita tipo) {
        this.tipo = tipo;
    }
    public int getQuantitaRaccolta() {
        return quantitaRaccolta;
    }
    public void setQuantitaRaccolta(int quantitaRaccolta) {
        this.quantitaRaccolta = quantitaRaccolta;
    }
    public int getQuantitaUsata() {
        return quantitaUsata;
    }
    public void setQuantitaUsata(int quantitaUsata) {
        this.quantitaUsata = quantitaUsata;
    }
    public Date getDataInizio() {
        return dataInizio;
    }
    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }
    public Date getDataFine() {
        return dataFine;
    }
    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }
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
    public void setIdAttivita(int idAttivita) {
        this.idAttivita = idAttivita;
    }
    public int getIdAttivita() {
        return idAttivita;
    }
}
