package it.unina.model;

import java.time.Duration;

public class Colture {
    private String titolo;
    private int id_colture;
    private Stagione stagionalita;
    private Duration tempoMaturazione;

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
}
