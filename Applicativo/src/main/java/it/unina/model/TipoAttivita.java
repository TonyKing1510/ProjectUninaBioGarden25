package it.unina.model;

public enum TipoAttivita {
    SEMINA,RACCOLTA,IRRIGAZIONE;

    @Override
    public String toString() {
        return switch (this) {
            case SEMINA -> "Semina";
            case RACCOLTA -> "Raccolta";
            case IRRIGAZIONE -> "Irrigazione";
            default -> super.toString();
        };
    }
}
