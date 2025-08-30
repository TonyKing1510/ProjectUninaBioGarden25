package it.unina.model;

public enum StatoAttivita {
    IN_CORSO,PROGRAMMATA,COMPLETATA;

    @Override
    public String toString() {
        return switch (this) {
            case IN_CORSO -> "in corso";
            case PROGRAMMATA -> "programmata";
            case COMPLETATA -> "completata";
            default -> super.toString();
        };
    }
}
