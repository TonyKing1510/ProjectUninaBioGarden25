package it.unina.model;

import java.util.ArrayList;
import java.util.List;

public class Magazzino {
    private String nome;
    private String via;
    private String indirizzo;
    private int cap;

    private List<Materiale> materiali = new ArrayList<>();
    private Utente proprietario; // 1 a 1 con Utente

    // Getters, Setters, Costruttore...
    public Magazzino(String nome, String via, String indirizzo, int cap, Utente proprietario) {
        this.nome = nome;
        this.via = via;
        this.indirizzo = indirizzo;
        this.cap = cap;
        this.proprietario = proprietario;
    }

    public Magazzino() {
        // Costruttore di default
        this.materiali = new ArrayList<>();
    }

    public Magazzino(String nome, String via, String indirizzo, int cap) {
        this.nome = nome;
        this.via = via;
        this.indirizzo = indirizzo;
        this.cap = cap;
        this.materiali = new ArrayList<>();
    }

    public Magazzino(String nome, String via, String indirizzo, int cap,Utente proprietario, List<Materiale> materiali) {
        this.nome = nome;
        this.via = via;
        this.indirizzo = indirizzo;
        this.cap = cap;
        this.materiali = materiali;
        this.proprietario = proprietario;
    }
}
