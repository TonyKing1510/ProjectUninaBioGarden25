package it.unina.implementazionePostgreSQL;


import it.unina.connessioneDB.ConnessioneDatabase;
import it.unina.dao.ProgettoDAO;
import it.unina.model.Colture;
import it.unina.model.Lotto;
import it.unina.model.Progetto;
import it.unina.model.Stagione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgettoDAOImpl implements ProgettoDAO {

    /**
     * Inserisce un nuovo progetto nel database e aggiorna il lotto selezionato
     * associandolo al progetto appena creato.
     *
     * La transazione assicura che entrambe le operazioni vengano eseguite
     * atomicamente: se una fallisce, entrambe vengono annullate.
     *
     * @param progetto Il progetto da inserire (senza id, con tutti i dati necessari)
     * @param lottiSelezionato I lotti che devono essere aggiornati con il nuovo progetto
     * @return true se operazioni riuscite, false in caso di errore o nessuna riga aggiornata
     */
    public boolean addProgettoAndUpdateLotto(Progetto progetto, List<Lotto> lottiSelezionato) {
        String insertQuery = """
        INSERT INTO progetto (stagione, id_utentecreatore, "dataInizio", "dataFine", titolo)
        VALUES (?::stagione, ?, ?, ?, ?)
        RETURNING id_progetto
        """;

        String updateQuery = "UPDATE lotto SET id_progetto = ? WHERE id_lotto = ?";

        try (Connection conn = ConnessioneDatabase.getConnection()) {
            conn.setAutoCommit(false);  // Inizio transazione

            int progettoId;

            // Inserimento progetto e recupero id generato
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setObject(1, capitalize(progetto.getStagione().toString()));
                insertStmt.setInt(2, progetto.getIdUtenteCreatore());
                insertStmt.setDate(3, java.sql.Date.valueOf(progetto.getDataInizio().toString()));
                insertStmt.setDate(4, java.sql.Date.valueOf(progetto.getDataFine().toString()));
                insertStmt.setString(5, progetto.getTitolo());

                try (ResultSet rs = insertStmt.executeQuery()) {
                    if (rs.next()) {
                        progettoId = rs.getInt("id_progetto");
                    } else {
                        conn.rollback();
                        System.err.println("Inserimento progetto fallito: nessun ID restituito.");
                        return false;
                    }
                }
            }

            // Aggiorna lotto con il nuovo progetto
            try( PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                for (Lotto lotto : lottiSelezionato) {
                    updateStmt.setInt(1, progettoId);
                    updateStmt.setInt(2, lotto.getIdLotto());
                    int affectedRows = updateStmt.executeUpdate();
                    if (affectedRows == 0) {
                        conn.rollback();
                        System.err.println("Aggiornamento lotto fallito per id: " + lotto.getIdLotto());
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





    @Override
    public List<Progetto> getProgettiByIdUtente(int idUtente) {
        List<Progetto> progetti = new ArrayList<>();
        String query = "SELECT id_progetto, stagione, id_utentecreatore, \"dataInizio\", \"dataFine\",titolo FROM Progetto WHERE id_utentecreatore = ?";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUtente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Progetto progetto = new Progetto();
                progetto.setIdProgetto(rs.getInt("id_progetto"));
                progetto.setTitolo(rs.getString("titolo"));
                progetto.setDataInizio(rs.getDate("dataInizio"));
                progetto.setDataFine(rs.getDate("dataFine"));
                progetto.setStagione(Stagione.valueOf(rs.getString("stagione").toUpperCase())); // Enum Stagione
                progetti.add(progetto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // gestione errori: log, rilancio, ecc.
        }

        return progetti;
    }





    private String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }


    @Override
    public int getIdProgettoByTitolo(String titolo) {
        String query = "SELECT id_progetto FROM Progetto WHERE titolo = ?";
        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, titolo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_progetto");
            } else {
                return -1; // Progetto non trovato
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // Errore durante l'esecuzione della query
        }
    }

    @Override
    public boolean addProgetto(Progetto progetto, int idProprietario) {
         return false;
    }

}
