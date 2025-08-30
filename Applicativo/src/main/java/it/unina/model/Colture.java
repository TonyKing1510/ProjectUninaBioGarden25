package it.unina.model;

import java.sql.Date;
import java.time.Duration;
import java.util.Objects;

public class Colture {
    private String titolo;
    private int id_colture;
    private Stagione stagionalita;
    private Duration tempoMaturazione;
    private Date dataInizioColtura;

    private Lotto lotto;

    // Costruttore vuoto
    public Colture() {
    }

    // Costruttore completo
    public Colture(int id_colture, Stagione stagionalita, Duration tempoMaturazione) {
        this.id_colture = id_colture;
        this.stagionalita = stagionalita;
        this.tempoMaturazione = tempoMaturazione;
    }

    // Getter e Setter
    public int getIdColture() {
        return id_colture;
    }

    public void setIdColture(int idColture) {
        this.id_colture = idColture;
    }

    public Stagione getStagionalita() {
        return stagionalita;
    }

    public void setStagionalita(Stagione stagionalita) {
        this.stagionalita = stagionalita;
    }

    public Duration getTempoMaturazione() {
        return tempoMaturazione;
    }

    public void setTempoMaturazione(Duration tempoMaturazione) {
        this.tempoMaturazione = tempoMaturazione;
    }

    @Override
    public String toString() {
        return "Colture{" +
                "idColture=" + id_colture +
                ", stagionalita=" + stagionalita +
                ", tempoMaturazione=" + tempoMaturazione +
                '}';
    }

    public String getTitolo() {
        return titolo;
    }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
    public Lotto getLotto() {
        return lotto;
    }
    public void setLotto(Lotto lotto) {
        this.lotto = lotto;
    }
    public Date getDataInizioColtura() {
        return dataInizioColtura;
    }
    public void setDataInizioColtura(Date dataInizioColtura) {
        this.dataInizioColtura = dataInizioColtura;
    }

    // Override di equals e hashCode basati su id_colture le ridefinisco per farsi che quando creo le statistiche non mi conti due volte la stessa coltura
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Colture colture = (Colture) o;
        return id_colture == colture.id_colture;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_colture);
    }
}
