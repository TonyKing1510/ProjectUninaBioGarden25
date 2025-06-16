package it.unina.implementazionePostgreSQL;

import it.unina.connessioneDB.ConnessioneDatabase;
import it.unina.dao.UtenteDAO;
import it.unina.model.Ruolo;
import it.unina.model.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * Implementazione della classe UtenteDAO per la gestione degli utenti nel database PostgreSQL.
 * @author entn
 */
public class UtenteDAOImpl implements UtenteDAO {

    /**
     * Aggiunge un nuovo utente al database.
     *
     * @param utente L'oggetto Utente da aggiungere.
     * @Author entn
     */
    @Override
    public boolean addUtente(Utente utente) {
        String query = "INSERT INTO Utente (nome, cognome, mail, password, ruolo, username) VALUES (?, ?, ?, ?, ?::ruolo, ?)";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, utente.getNome());
            stmt.setString(2, utente.getCognome());
            stmt.setString(3, utente.getMail());
            stmt.setString(4, utente.getPassword());
            stmt.setString(5, utente.getRuolo().toString().toLowerCase());
            stmt.setString(6, utente.getUsername());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Recupera un utente dal database in base all'ID.
     *
     *
     * @return L'oggetto Utente corrispondente all'ID, o null se non trovato.
     * @Author entn
     */

    @Override
    public List<Utente> getTuttiGliUtenti() {
        return List.of();
    }

    /**
     * Permette di recuperare un utente dal database in base al nome utente e alla password per il login.
     *
     * @param username l'username dell'utente da recuperare
     * @param password la password dell'utente da recuperare
     * @return L'oggetto Utente corrispondente all'ID, o null se non trovato.
     * @Author entn
     */
    @Override
    public Utente login(String username, String password) {
        String sql = "SELECT * FROM Utente WHERE username = ? AND password = ?";
        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Utente(
                            rs.getInt("id_utente"),
                            rs.getString("nome"),
                            rs.getString("cognome"),
                            rs.getString("mail"),
                            rs.getString("password"),
                            Ruolo.valueOf(rs.getString("ruolo").toUpperCase()),
                            rs.getString("username")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore nella login:");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Controlla se un utente esiste nel database in base all'email.
     *
     * @param email L'email dell'utente da controllare.
     * @return true se l'utente esiste, false altrimenti.
     * @Author entn
     */

    @Override
    public boolean esisteUtente(String email) {
        String sql = "SELECT 1 FROM Utente WHERE mail = ?";
        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println("Errore nel controllo esistenza utente:");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Utente> getUtenteColtivatore(int idColtivatore) {
        String sql = "SELECT * FROM Utente WHERE id_utente = ? AND ruolo = 'coltivatore'";
        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idColtivatore);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return List.of(new Utente(
                            rs.getInt("id_utente"),
                            rs.getString("nome"),
                            rs.getString("cognome"),
                            rs.getString("mail"),
                            rs.getString("password"),
                            Ruolo.valueOf(rs.getString("ruolo").toUpperCase()),
                            rs.getString("username")
                    ));
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore nel recupero utente coltivatore:");
            e.printStackTrace();
        }
        return List.of();
    }
}
