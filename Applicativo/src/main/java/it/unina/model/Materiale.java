package it.unina.model;

public class Materiale {
    private String nome;
    private TipoMateriale tipo;
    private int quantita;
    private Magazzino magazzino; // Associazione con Magazzino



    // Costruttore
    public Materiale(String nome, TipoMateriale tipo, int quantita, Magazzino magazzino) {
        this.nome = nome;
        this.tipo = tipo;
        this.quantita = quantita;
        this.magazzino = magazzino;
    }

    public Materiale() {
        // Costruttore di default
    }


    // Getter per nome
    public String getNome() {
        return nome;
    }

    // Setter per nome
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter per tipo
    public TipoMateriale getTipo() {
        return tipo;
    }

    // Setter per tipo
    public void setTipo(TipoMateriale tipo) {
        this.tipo = tipo;
    }

    // Getter per quantita
    public int getQuantita() {
        return quantita;
    }

    // Setter per quantita
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    // Getter per magazzino
    public Magazzino getMagazzino() {
        return magazzino;
    }
    // Setter per magazzino
    public void setMagazzino(Magazzino magazzino) {
        this.magazzino = magazzino;
    }


    // Possibile toString per la rappresentazione della classe
    @Override
    public String toString() {
        return "Materiale{" +
                "nome='" + nome + '\'' +
                ", tipo=" + tipo +
                ", quantita=" + quantita +
                '}';
    }
}
