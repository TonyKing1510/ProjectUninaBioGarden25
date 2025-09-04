package it.unina.model;

/**
 * Rappresenta lo stato di un'attività.
 * <p>
 * Gli stati possibili sono:
 * <ul>
 *     <li>{@link #IN_CORSO} - attività attualmente in corso</li>
 *     <li>{@link #PROGRAMMATA} - attività programmata per il futuro</li>
 *     <li>{@link #COMPLETATA} - attività completata</li>
 * </ul>
 * La rappresentazione testuale è personalizzata tramite il metodo {@link #toString()}.
 *
 * @author entn
 */
public enum StatoAttivita {

    /** Attività attualmente in corso */
    IN_CORSO,

    /** Attività programmata per il futuro */
    PROGRAMMATA,

    /** Attività completata */
    COMPLETATA;

    /**
     * Restituisce una rappresentazione testuale leggibile dello stato.
     * @return stringa descrittiva dello stato dell'attività
     * @author entn
     */
    @Override
    public String toString() {
        return switch (this) {
            case IN_CORSO -> "in corso";
            case PROGRAMMATA -> "programmata";
            case COMPLETATA -> "completata";
            default -> super.toString();
        };
    }

    public static StatoAttivita fromString(String s) {
        return switch (s.toLowerCase()) {
            case "in corso" -> StatoAttivita.IN_CORSO;
            case "programmata" -> StatoAttivita.PROGRAMMATA;
            case "completata" -> StatoAttivita.COMPLETATA;
            default -> null; // oppure puoi lanciare un'eccezione
        };
    }

}
