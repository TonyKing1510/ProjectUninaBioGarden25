package it.unina.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Attivita {
    private StatoAttivita stato;
    private TipoAttivita tipo;
    private int quantitaRaccolta;
    private int quantitaUsata;
    private Date dataInizio;
    private Date dataFine;

    private Utente utente;
    private List<Materiale> materialiUsati = new ArrayList<>();

    /**
     * Costruttore per creare un'attività completa.
     *
     * @param stato         stato dell'attività (IN_CORSO, CONCLUSA, ANNULLATA)
     * @param tipo          tipo di attività (PIANTAGIONE, RACCOLTA, MANUTENZIONE)
     * @param quantitaRaccolta quantità raccolta (se applicabile)
     * @param quantitaUsata quantità di materiale usato (se applicabile)
     * @param dataInizio    data di inizio dell'attività
     * @param dataFine      data di fine dell'attività
     * @param utente        utente che ha eseguito l'attività
     */
    public Attivita(StatoAttivita stato, TipoAttivita tipo, int quantitaRaccolta, int quantitaUsata, Date dataInizio, Date dataFine, Utente utente) {
        this.stato = stato;
        this.tipo = tipo;
        this.quantitaRaccolta = quantitaRaccolta;
        this.quantitaUsata = quantitaUsata;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.utente = utente;
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
    public Utente getUtente() {
        return utente;
    }
    public void setUtente(Utente utente) {
        this.utente = utente;
    }
    public List<Materiale> getMaterialiUsati() {
        return materialiUsati;
    }
    public void setMaterialiUsati(List<Materiale> materialiUsati) {
        this.materialiUsati = materialiUsati;
    }
    public void addMaterialeUsato(Materiale materiale) {
        this.materialiUsati.add(materiale);
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
                ", utente=" + utente.getNome() + " " + utente.getCognome() +
                ", materialiUsati=" + materialiUsati +
                '}';
    }
}
