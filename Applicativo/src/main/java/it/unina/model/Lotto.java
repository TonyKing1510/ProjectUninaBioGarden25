package it.unina.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongToDoubleFunction;

/**
 * Rappresenta un Lotto associato a un Progetto.
 * Un Lotto contiene informazioni sulla superficie, posizione e il riferimento al Progetto a cui appartiene.
 *
 * @author entn
 */
public class Lotto {

    /** Identificativo univoco del Lotto
     *  @author entn
     */
    private int idLotto;

    /** Nome identificativo del Lotto
     *  @author entn
     */
    private String nome;

    /** Superficie del Lotto (in metri quadrati)
     *  @author entn
     */
    private double superficie;

    /** Via del Lotto
     *  @author entn
     */
    private String via;

    /** Indirizzo completo del Lotto
     *  @author entn
     */
    private String indirizzo;

    /** CAP (codice di avviamento postale) del Lotto
     *  @author entn
     */
    private String cap;

    /** Riferimento all'oggetto Progetto associato al Lotto
     *  @author entn
     */
    private Progetto progetto;

    public List<Colture>  getColture() {
        return colture;
    }

    private List<Colture> colture;

    private Utente proprietario;

    /**
     * Costruttore di default.
     *
     * @author entn
     */
    public Lotto() {
        this.colture = new ArrayList<>();
    }

    /**
     * Costruttore con parametri per inizializzare tutte le proprietà del Lotto.
     *
     *
     * @param nome        nome del Lotto
     * @param superficie  superficie del Lotto in metri quadrati
     * @param via         via del Lotto
     * @param indirizzo   indirizzo completo del Lotto
     * @param cap         codice di avviamento postale del Lotto
     * @param progetto    riferimento all'oggetto Progetto associato
     * @param proprietario  utente proprietario del Lotto
     *
     * @author entn
     */
    public Lotto( String nome, double superficie, String via, String indirizzo, String cap, Progetto progetto , Utente proprietario) {
        this.nome = nome;
        this.superficie = superficie;
        this.via = via;
        this.indirizzo = indirizzo;
        this.cap = cap;
        this.progetto = progetto;
        this.proprietario = proprietario;
    }

    public Lotto(int idLotto,String nome, double superficie, String via, String indirizzo, String cap) {
        this.idLotto = idLotto;
        this.nome = nome;
        this.superficie = superficie;
        this.via = via;
        this.indirizzo = indirizzo;
        this.cap = cap;
    }
    public Lotto(int idLotto, String nome, double superficie, String via, String indirizzo, String cap, Progetto progetto, List<Colture> colture) {
        this.idLotto = idLotto;
        this.nome = nome;
        this.superficie = superficie;
        this.via = via;
        this.indirizzo = indirizzo;
        this.cap = cap;
        this.progetto = progetto;
        this.colture = colture;
    }

    /**
     * Restituisce l'identificativo univoco del Lotto.
     * @return id del Lotto
     * @author entn
     */
    public int getIdLotto() {
        return idLotto;
    }

    /**
     * Imposta l'identificativo univoco del Lotto.
     * @param idLotto nuovo id del Lotto
     * @author entn
     */
    public void setIdLotto(int idLotto) {
        this.idLotto = idLotto;
    }

    /**
     * Restituisce il nome del Lotto.
     * @return nome del Lotto
     * @author entn
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome del Lotto.
     * @param nome nuovo nome del Lotto
     * @author entn
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce la superficie del Lotto in metri quadrati.
     * @return superficie del Lotto
     * @author entn
     */
    public double getSuperficie() {
        return superficie;
    }

    /**
     * Imposta la superficie del Lotto in metri quadrati.
     * @param superficie nuova superficie del Lotto
     * @author entn
     */
    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    /**
     * Restituisce la via del Lotto.
     * @return via del Lotto
     * @author entn
     */
    public String getVia() {
        return via;
    }

    /**
     * Imposta la via del Lotto.
     * @param via nuova via del Lotto
     * @author entn
     */
    public void setVia(String via) {
        this.via = via;
    }

    /**
     * Restituisce l'indirizzo completo del Lotto.
     * @return indirizzo del Lotto
     * @author entn
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Imposta l'indirizzo completo del Lotto.
     * @param indirizzo nuovo indirizzo del Lotto
     * @author entn
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    /**
     * Restituisce il codice di avviamento postale (CAP) del Lotto.
     * @return CAP del Lotto
     * @author entn
     */
    public String getCap() {
        return cap;
    }

    /**
     * Imposta il codice di avviamento postale (CAP) del Lotto.
     * @param cap nuovo CAP del Lotto
     * @author entn
     */
    public void setCap(String cap) {
        this.cap = cap;
    }

    /**
     * Restituisce il Progetto associato al Lotto.
     * @return oggetto Progetto associato
     * @author entn
     */
    public Progetto getProgetto() {
        return progetto;
    }

    /**
     * Imposta il Progetto associato al Lotto.
     * @param progetto nuovo Progetto associato
     * @author entn
     */
    public void setProgetto(Progetto progetto) {
        this.progetto = progetto;
    }



    public void setColture(List<Colture> colture) {
        this.colture = colture;
    }

    public void addColtura(Colture coltura) {
        this.colture.add(coltura);}

    public Utente getProprietario() {
        return proprietario;
    }
    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
    }


}
