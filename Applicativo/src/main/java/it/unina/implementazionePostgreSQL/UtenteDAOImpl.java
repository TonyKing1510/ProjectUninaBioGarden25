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

public class UtenteDAOImpl implements UtenteDAO {

    /**
     * Aggiunge un nuovo utente al database.
     *
     * @param utente L'oggetto Utente da aggiungere.
     */
    @Override
    public void addUtente(Utente utente) {
        String sql = "INSERT INTO Utente (nome, cognome, mail, password, ruolo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, utente.getNome());
            stmt.setString(2, utente.getCognome());
            stmt.setString(3, utente.getMail());
            stmt.setString(4, utente.getPassword());
            stmt.setString(5, utente.getRuolo().name());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento dell'utente:");
            e.printStackTrace();
        }
    }

    @Override
    public List<Utente> getTuttiGliUtenti() {
        return List.of();
    }


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
                            Ruolo.valueOf(rs.getString("ruolo"))
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore nella login:");
            e.printStackTrace();
        }

        return null;
    }


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
}
