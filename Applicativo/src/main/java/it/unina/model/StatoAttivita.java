package it.unina.model;

public enum StatoAttivita {
    IN_CORSO,PROGRAMMATA,COMPLETATA;

    @Override
    public String toString() {
        return switch (this) {
            case IN_CORSO -> "In Corso";
            case PROGRAMMATA -> "Programmata";
            case COMPLETATA -> "Completata";
            default -> super.toString();
        };
    }
}
