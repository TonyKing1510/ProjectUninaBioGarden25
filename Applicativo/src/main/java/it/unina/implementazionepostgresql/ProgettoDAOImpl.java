package it.unina.implementazionepostgresql;

import it.unina.connessioneDB.ConnessioneDatabase;
import it.unina.dao.ProgettoDAO;
import it.unina.model.Lotto;
import it.unina.model.Progetto;
import it.unina.model.Stagione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementazione dell'interfaccia {@link ProgettoDAO} per PostgreSQL.
 * <p>
 * Questa classe fornisce i metodi per la gestione dei progetti
 * e la loro associazione ai lotti tramite JDBC.
 *
 * @author entn
 */
public class ProgettoDAOImpl implements ProgettoDAO {

    private static final String COLONNA_ID_PROGETTO = "id_progetto";

    /**
     * Inserisce un nuovo progetto nel database e aggiorna i lotti selezionati
     * associandoli al progetto appena creato. L’operazione viene gestita
     * tramite transazione: in caso di errore, viene effettuato un rollback.
     *
     * @param progetto        Il progetto da inserire (senza ID, con tutti i dati necessari).
     * @param lottiSelezonato Lista di lotti che devono essere aggiornati con il nuovo progetto.
     * @return {@code true} se l’inserimento e l’aggiornamento sono andati a buon fine,
     *         {@code false} in caso di errore.
     *         @author entn
     */
    public boolean addProgettoAndUpdateLotto(Progetto progetto, List<Lotto> lottiSelezonato) {
        String insertQuery = """
                INSERT INTO progetto (stagione, id_utentecreatore, "dataInizio", "dataFine", titolo)
                VALUES (?::stagione, ?, ?, ?, ?)
                RETURNING id_progetto
                """;

        String updateQuery = "UPDATE lotto SET id_progetto = ? WHERE id_lotto = ?";
        Logger logger2 = Logger.getLogger(getClass().getName());

        try (Connection conn = ConnessioneDatabase.getConnection()) {
            conn.setAutoCommit(false);  // Inizio transazione
            int progettoId;

            // Inserimento progetto
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setObject(1, capitalize(progetto.getStagione().toString()));
                insertStmt.setInt(2, progetto.getIdUtenteCreatore());
                insertStmt.setDate(3, java.sql.Date.valueOf(progetto.getDataInizio().toString()));
                insertStmt.setDate(4, java.sql.Date.valueOf(progetto.getDataFine().toString()));
                insertStmt.setString(5, progetto.getTitolo());

                try (ResultSet rs = insertStmt.executeQuery()) {
                    if (rs.next()) {
                        progettoId = rs.getInt(COLONNA_ID_PROGETTO);
                    } else {
                        conn.rollback();
                        logger2.log(Level.SEVERE, "Inserimento progetto fallito: nessun ID restituito.");
                        return false;
                    }
                }
            }

            // Aggiornamento lotti associati
            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                updateStmt.setInt(1, progettoId);

                for (Lotto lotto : lottiSelezonato) {
                    updateStmt.setInt(2, lotto.getIdLotto());
                    updateStmt.addBatch();
                }

                int[] results = updateStmt.executeBatch();
                Logger logger = Logger.getLogger(getClass().getName());

                for (int result : results) {
                    if (result == 0) {
                        conn.rollback();
                        logger.log(Level.SEVERE, () -> "Errore nell'aggiornamento del lotto.");
                        return false;
                    }
                }
            }

            conn.commit();  // Commit transazione
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Recupera tutti i progetti creati da un determinato utente.
     *
     * @param idUtente ID dell’utente creatore.
     * @return Lista di progetti associati all’utente. Può essere vuota se non ci sono risultati.
     * @author entn
     */
    @Override
    public List<Progetto> getProgettiByIdUtente(int idUtente) {
        List<Progetto> progetti = new ArrayList<>();
        String query = "SELECT id_progetto, stagione, id_utentecreatore, \"dataInizio\", \"dataFine\", titolo " +
                "FROM Progetto WHERE id_utentecreatore = ?";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUtente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Progetto progetto = new Progetto();
                progetto.setIdProgetto(rs.getInt(COLONNA_ID_PROGETTO));
                progetto.setTitolo(rs.getString("titolo"));
                progetto.setDataInizio(rs.getDate("dataInizio"));
                progetto.setDataFine(rs.getDate("dataFine"));
                progetto.setStagione(Stagione.valueOf(rs.getString("stagione").toUpperCase()));
                progetti.add(progetto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return progetti;
    }

    /**
     * Converte la stringa in formato "Capitalized" (prima lettera maiuscola, resto minuscolo).
     *
     * @param input La stringa da capitalizzare.
     * @return La stringa capitalizzata.
     * @author entn
     */
    private String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    /**
     * Restituisce l’ID di un progetto dato il titolo.
     *
     * @param titolo Titolo del progetto.
     * @return ID del progetto se trovato, -1 altrimenti.
     * @author entn
     */
    @Override
    public int getIdProgettoByTitolo(String titolo) {
        String query = "SELECT id_progetto FROM Progetto WHERE titolo = ?";
        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, titolo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(COLONNA_ID_PROGETTO);
            } else {
                return -1; // Progetto non trovato
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Errore durante l'esecuzione della query
        }
    }

    /**
     * Inserisce un nuovo progetto con associazione a un proprietario.
     * <p>
     * Non ancora implementato.
     *
     * @param progetto      Progetto da inserire.
     * @param idProprietario ID del proprietario.
     * @return {@code false} in quanto non implementato.
     * @author entn
     */
    @Override
    public boolean addProgetto(Progetto progetto, int idProprietario) {
        return false;
    }

    /**
     * Recupera i progetti di un proprietario applicando filtri opzionali su stagione e superficie.
     *
     * @param idProprietario ID del proprietario.
     * @param stagione       Filtro sulla stagione (può essere {@code null}).
     * @param superficie     Filtro sulla superficie minima dei lotti (può essere {@code null}).
     * @return Lista di progetti che rispettano i criteri. Può essere vuota.
     * @author entn
     */
    @Override
    public List<Progetto> getProgettiWithFilter(int idProprietario, Stagione stagione, Double superficie) {
        List<Progetto> progetti = new ArrayList<>();
        StringBuilder query = new StringBuilder(
                "SELECT p.id_progetto, p.stagione, p.id_utentecreatore, p.\"dataInizio\", p.\"dataFine\", p.titolo " +
                        "FROM Progetto p " +
                        "JOIN Lotto l ON p.id_progetto = l.id_progetto " +
                        "WHERE p.id_utentecreatore = ?"
        );

        List<Object> params = new ArrayList<>();
        params.add(idProprietario);

        if (stagione != null) {
            query.append(" AND p.stagione = ?::stagione");
            params.add(stagione);
        }

        if (superficie != null) {
            query.append(" AND l.superficie >= ?");
            params.add(superficie);
        }

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof Integer integer) {
                    stmt.setInt(i + 1, integer);
                } else if (param instanceof Stagione stagioneInstance) {
                    String stagioneStr = stagioneInstance.name().toLowerCase();
                    stagioneStr = stagioneStr.substring(0, 1).toUpperCase() + stagioneStr.substring(1);
                    stmt.setString(i + 1, stagioneStr);
                } else if (param instanceof Double d) {
                    stmt.setDouble(i + 1, d);
                }
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Progetto progetto = new Progetto();
                progetto.setIdProgetto(rs.getInt(COLONNA_ID_PROGETTO));
                progetto.setTitolo(rs.getString("titolo"));
                progetto.setDataInizio(rs.getDate("dataInizio"));
                progetto.setDataFine(rs.getDate("dataFine"));
                progetto.setStagione(Stagione.valueOf(rs.getString("stagione").toUpperCase()));
                progetti.add(progetto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return progetti;
    }
}
