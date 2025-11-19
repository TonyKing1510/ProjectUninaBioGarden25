package it.unina.model;

import java.time.LocalDateTime;

/**
 * Rappresenta una notifica creata da un proprietario di lotti.
 * Le notifiche possono essere indirizzate a un singolo coltivatore o a tutti.
 * Possono riguardare attività imminenti o anomalie sui progetti.
 * La colonna "lettura" non è necessaria perché solo i proprietari accedono al sistema.
 */
public class Notifica {

    /** Identificativo univoco della notifica */
    private int id;

    /** Utente proprietario che ha creato la notifica */
    private Utente owner;

    /** Lotto opzionale a cui la notifica fa riferimento */
    private Lotto lotto;

    /** Utente destinatario singolo (opzionale) */
    private Utente destinatario;

    /** Se true, la notifica è destinata a tutti i coltivatori */
    private boolean tutti;

    /** Titolo della notifica */
    private String titolo;

    /** Descrizione dettagliata della notifica */
    private String descrizione;

    /** Numero di giorni entro cui la notifica è rilevante */
    private int giorniScadenza;

    /** Tipo di notifica (ad es. "attività", "anomalia") */
    private String tipo;

    /** Data e ora di creazione della notifica */
    private LocalDateTime dataCreazione;

    /** Costruttore vuoto */
    public Notifica() {
        this.tutti = false;
        this.dataCreazione = LocalDateTime.now();
    }

    /**
     * Costruttore completo
     * @param owner proprietario che crea la notifica
     * @param lotto lotto associato (opzionale)
     * @param destinatario destinatario singolo (opzionale)
     * @param tutti true se destinata a tutti i coltivatori
     * @param titolo titolo della notifica
     * @param descrizione descrizione dettagliata
     * @param giorniScadenza giorni entro cui è rilevante
     * @param tipo tipo di notifica
     */
    public Notifica(Utente owner, Lotto lotto, Utente destinatario, boolean tutti,
                    String titolo, String descrizione, int giorniScadenza, String tipo) {
        this.owner = owner;
        this.lotto = lotto;
        this.destinatario = destinatario;
        this.tutti = tutti;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.giorniScadenza = giorniScadenza;
        this.tipo = tipo;
        this.dataCreazione = LocalDateTime.now();
    }

    // Getter e Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Utente getOwner() { return owner; }
    public void setOwner(Utente owner) { this.owner = owner; }

    public Lotto getLotto() { return lotto; }
    public void setLotto(Lotto lotto) { this.lotto = lotto; }

    public Utente getDestinatario() { return destinatario; }
    public void setDestinatario(Utente destinatario) { this.destinatario = destinatario; }

    public boolean isTutti() { return tutti; }
    public void setTutti(boolean tutti) { this.tutti = tutti; }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public int getGiorniScadenza() { return giorniScadenza; }
    public void setGiorniScadenza(int giorniScadenza) { this.giorniScadenza = giorniScadenza; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDateTime getDataCreazione() { return dataCreazione; }
    public void setDataCreazione(LocalDateTime dataCreazione) { this.dataCreazione = dataCreazione; }

    @Override
    public String toString() {
        return "Notifica{" +
                "id=" + id +
                ", owner=" + (owner != null ? owner.getUsername() : "null") +
                ", lotto=" + (lotto != null ? lotto.getIdLotto() : "null") +
                ", destinatario=" + (destinatario != null ? destinatario.getUsername() : "null") +
                ", tutti=" + tutti +
                ", titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", giorniScadenza=" + giorniScadenza +
                ", tipo='" + tipo + '\'' +
                ", dataCreazione=" + dataCreazione +
                '}';
    }
}