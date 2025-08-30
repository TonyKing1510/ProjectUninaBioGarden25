package it.unina.model;

/**
 * Rappresenta il tipo di attività eseguibile su un lotto o una coltura.
 * <p>
 * I tipi possibili sono:
 * <ul>
 *     <li>{@link #SEMINA} - attività di semina</li>
 *     <li>{@link #RACCOLTA} - attività di raccolta</li>
 *     <li>{@link #IRRIGAZIONE} - attività di irrigazione</li>
 * </ul>
 * La rappresentazione testuale è personalizzata tramite il metodo {@link #toString()}.
 *
 * @author entn
 */
public enum TipoAttivita {

    /** Attività di semina */
    SEMINA,

    /** Attività di raccolta */
    RACCOLTA,

    /** Attività di irrigazione */
    IRRIGAZIONE;

    /**
     * Restituisce una rappresentazione testuale leggibile del tipo di attività.
     *
     * @return stringa descrittiva del tipo di attività
     * @author entn
     */
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
