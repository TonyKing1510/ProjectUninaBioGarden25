package it.unina.model;

import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Rappresenta una coltura all'interno di un lotto.
 * <p>
 * Ogni coltura ha un identificativo univoco, un titolo, una stagionalità, un tempo di maturazione
 * e una data di inizio coltura. È associata a un {@link Lotto}.
 * <p>
 * I metodi {@link #equals(Object)} e {@link #hashCode()} sono ridefiniti in base all'id della coltura
 * per evitare duplicati nella gestione delle statistiche.
 *
 * @author entn
 */
public class Colture {

    /** Titolo della coltura */
    private String titolo;

    /** Identificativo univoco della coltura */
    private int id_colture;

    /** Stagionalità della coltura */
    private Stagione stagionalita;

    /** Tempo di maturazione della coltura */
    private Duration tempoMaturazione;

    /** Data di inizio della coltura */
    private Date dataInizioColtura;

    /** Lotto a cui appartiene la coltura */
    private Lotto lotto;

    private List<Attivita> attivita = new ArrayList<>();

        public void addAttivita(Attivita a) {
            a.setColtura(this);
            attivita.add(a);
        }

        public List<Attivita> getAttivita() {
            return attivita;
        }



    /**
     * Costruttore vuoto.
     */
    public Colture() {
    }

    /**
     * Costruttore completo della coltura.
     *
     * @param id_colture identificativo della coltura
     * @param stagionalita stagione di coltivazione
     * @param tempoMaturazione tempo necessario per la maturazione
     */
    public Colture(int id_colture, Stagione stagionalita, Duration tempoMaturazione) {
        this.id_colture = id_colture;
        this.stagionalita = stagionalita;
        this.tempoMaturazione = tempoMaturazione;
    }

    /**
     * Restituisce l'identificativo della coltura.
     *
     * @return id della coltura
     */
    public int getIdColture() {
        return id_colture;
    }

    /**
     * Imposta l'identificativo della coltura.
     *
     * @param idColture nuovo id della coltura
     */
    public void setIdColture(int idColture) {
        this.id_colture = idColture;
    }

    /**
     * Restituisce la stagionalità della coltura.
     *
     * @return stagionalità della coltura
     */
    public Stagione getStagionalita() {
        return stagionalita;
    }

    /**
     * Imposta la stagionalità della coltura.
     *
     * @param stagionalita nuova stagionalità
     */
    public void setStagionalita(Stagione stagionalita) {
        this.stagionalita = stagionalita;
    }

    /**
     * Restituisce il tempo di maturazione della coltura.
     *
     * @return tempo di maturazione
     */
    public Duration getTempoMaturazione() {
        return tempoMaturazione;
    }

    /**
     * Imposta il tempo di maturazione della coltura.
     *
     * @param tempoMaturazione nuovo tempo di maturazione
     */
    public void setTempoMaturazione(Duration tempoMaturazione) {
        this.tempoMaturazione = tempoMaturazione;
    }

    /**
     * Restituisce il titolo della coltura.
     *
     * @return titolo della coltura
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Imposta il titolo della coltura.
     *
     * @param titolo nuovo titolo
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * Restituisce il lotto a cui appartiene la coltura.
     *
     * @return lotto associato
     */
    public Lotto getLotto() {
        return lotto;
    }

    /**
     * Imposta il lotto a cui appartiene la coltura.
     *
     * @param lotto nuovo lotto
     */
    public void setLotto(Lotto lotto) {
        this.lotto = lotto;
    }

    /**
     * Restituisce la data di inizio coltura.
     *
     * @return data di inizio
     */
    public Date getDataInizioColtura() {
        return dataInizioColtura;
    }

    /**
     * Imposta la data di inizio coltura.
     *
     * @param dataInizioColtura nuova data di inizio
     */
    public void setDataInizioColtura(Date dataInizioColtura) {
        this.dataInizioColtura = dataInizioColtura;
    }

    /**
     * Due colture sono considerate uguali se hanno lo stesso {@link #id_colture}.
     *
     * @param o oggetto da confrontare
     * @return true se gli oggetti sono uguali, false altrimenti
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Colture colture = (Colture) o;
        return id_colture == colture.id_colture;
    }

    /**
     * Restituisce l'hash code basato sull'id della coltura.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(id_colture);
    }

    /**
     * Restituisce una rappresentazione testuale della coltura.
     *
     * @return stringa descrittiva
     */
    @Override
    public String toString() {
        return "Colture{" +
                "idColture=" + id_colture +
                ", stagionalita=" + stagionalita +
                ", tempoMaturazione=" + tempoMaturazione +
                '}';
    }
}
