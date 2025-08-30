package it.unina.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Rappresenta un Lotto associato a un Progetto.
 * <p>
 * Un Lotto contiene informazioni sulla superficie, posizione e il riferimento al Progetto a cui appartiene.
 * Può avere una lista di colture e un utente proprietario.
 * <p>
 * I metodi {@link #equals(Object)} e {@link #hashCode()} sono ridefiniti basandosi sull'identificativo univoco {@link #idLotto}.
 *
 * @author entn
 */
public class Lotto {

    /** Identificativo univoco del Lotto */
    private int idLotto;

    /** Nome identificativo del Lotto */
    private String nome;

    /** Superficie del Lotto (in metri quadrati) */
    private double superficie;

    /** Via del Lotto */
    private String via;

    /** Indirizzo completo del Lotto */
    private String indirizzo;

    /** CAP (codice di avviamento postale) del Lotto */
    private String cap;

    /** Riferimento all'oggetto Progetto associato al Lotto */
    private Progetto progetto;

    /** Lista di colture presenti nel Lotto */
    private List<Colture> colture;

    /** Utente proprietario del Lotto */
    private Utente proprietario;

    /**
     * Costruttore di default.
     */
    public Lotto() {
        this.colture = new ArrayList<>();
    }

    /**
     * Costruttore completo per inizializzare tutte le proprietà del Lotto.
     * @param nome nome del Lotto
     * @param superficie superficie in metri quadrati
     * @param via via del Lotto
     * @param indirizzo indirizzo completo
     * @param cap CAP del Lotto
     * @param progetto progetto associato
     * @param proprietario utente proprietario del Lotto
     * @author entn
     */
    public Lotto(String nome, double superficie, String via, String indirizzo, String cap, Progetto progetto, Utente proprietario) {
        this.nome = nome;
        this.superficie = superficie;
        this.via = via;
        this.indirizzo = indirizzo;
        this.cap = cap;
        this.progetto = progetto;
        this.proprietario = proprietario;
        this.colture = new ArrayList<>();
    }

    /**
     * Costruttore base con parametri principali.
     * @param idLotto identificativo del Lotto
     * @param nome nome del Lotto
     * @param superficie superficie del Lotto
     * @param via via del Lotto
     * @param indirizzo indirizzo completo
     * @param cap CAP del Lotto
     * @author entn
     */
    public Lotto(int idLotto, String nome, double superficie, String via, String indirizzo, String cap) {
        this.idLotto = idLotto;
        this.nome = nome;
        this.superficie = superficie;
        this.via = via;
        this.indirizzo = indirizzo;
        this.cap = cap;
        this.colture = new ArrayList<>();
    }

    /**
     * Costruttore completo con lista di colture.
     * @param idLotto identificativo del Lotto
     * @param nome nome del Lotto
     * @param superficie superficie del Lotto
     * @param via via del Lotto
     * @param indirizzo indirizzo completo
     * @param cap CAP del Lotto
     * @param progetto progetto associato
     * @param colture lista di colture associate
     * @author entn
     */
    public Lotto(int idLotto, String nome, double superficie, String via, String indirizzo, String cap, Progetto progetto, List<Colture> colture) {
        this.idLotto = idLotto;
        this.nome = nome;
        this.superficie = superficie;
        this.via = via;
        this.indirizzo = indirizzo;
        this.cap = cap;
        this.progetto = progetto;
        this.colture = colture != null ? colture : new ArrayList<>();
    }

    /**
     * Restituisce l'identificativo del Lotto.
     * @return id del Lotto
     * @author entn
     */
    public int getIdLotto() { return idLotto; }

    /**
     * Imposta l'identificativo del Lotto.
     * @param idLotto nuovo id
     * @author entn
     */
    public void setIdLotto(int idLotto) { this.idLotto = idLotto; }

    /**
     * Restituisce il nome del Lotto.
     * @return nome del Lotto
     * @author entn
     */
    public String getNome() { return nome; }

    /**
     * Imposta il nome del Lotto.
     * @param nome nuovo nome
     * @author entn
     */
    public void setNome(String nome) { this.nome = nome; }

    /**
     * Restituisce la superficie del Lotto.
     * @return superficie in metri quadrati
     * @author entn
     */
    public double getSuperficie() { return superficie; }

    /**
     * Imposta la superficie del Lotto.
     * @param superficie nuova superficie
     * @author entn
     */
    public void setSuperficie(double superficie) { this.superficie = superficie; }

    /**
     * Restituisce la via del Lotto.
     * @return via del Lotto
     * @author entn
     */
    public String getVia() { return via; }

    /**
     * Imposta la via del Lotto.
     * @param via nuova via
     * @author entn
     */
    public void setVia(String via) { this.via = via; }

    /**
     * Restituisce l'indirizzo completo del Lotto.
     * @return indirizzo del Lotto
     * @author entn
     */
    public String getIndirizzo() { return indirizzo; }

    /**
     * Imposta l'indirizzo completo del Lotto.
     * @param indirizzo nuovo indirizzo
     * @author entn
     */
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }

    /**
     * Restituisce il CAP del Lotto.
     * @return CAP
     * @author entn
     */
    public String getCap() { return cap; }

    /**
     * Imposta il CAP del Lotto.
     * @param cap nuovo CAP
     * @author entn
     */
    public void setCap(String cap) { this.cap = cap; }

    /**
     * Restituisce il progetto associato al Lotto.
     * @return progetto associato
     * @author entn
     */
    public Progetto getProgetto() { return progetto; }

    /**
     * Imposta il progetto associato al Lotto.
     * @param progetto nuovo progetto
     * @author entn
     */
    public void setProgetto(Progetto progetto) { this.progetto = progetto; }

    /**
     * Restituisce la lista delle colture del Lotto.
     * @return lista di colture
     * @author entn
     */
    public List<Colture> getColture() { return colture; }

    /**
     * Imposta la lista delle colture.
     * @param colture nuova lista di colture
     * @author entn
     */
    public void setColture(List<Colture> colture) { this.colture = colture; }

    /**
     * Aggiunge una coltura alla lista del Lotto.
     * @param coltura coltura da aggiungere
     * @author entn
     */
    public void addColtura(Colture coltura) { this.colture.add(coltura); }

    /**
     * Restituisce l'utente proprietario del Lotto.
     * @return proprietario
     * @author entn
     */
    public Utente getProprietario() { return proprietario; }

    /**
     * Imposta l'utente proprietario del Lotto.
     * @param proprietario nuovo proprietario
     * @author entn
     */
    public void setProprietario(Utente proprietario) { this.proprietario = proprietario; }

    /**
     * Restituisce una rappresentazione testuale del Lotto.
     * @return stringa con id e nome del Lotto
     * @author entn
     */
    @Override
    public String toString() { return idLotto + " - " + nome; }

    /**
     * Confronta due oggetti Lotto basandosi sull'id.
     * @param o oggetto da confrontare
     * @return true se gli id sono uguali, false altrimenti
     * @author entn
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lotto)) return false;
        Lotto lotto = (Lotto) o;
        return idLotto == lotto.idLotto;
    }

    /**
     * Restituisce l'hashcode basato sull'id del Lotto.
     * @return hashcode dell'id
     * @author entn
     */
    @Override
    public int hashCode() { return Objects.hash(idLotto); }
}
