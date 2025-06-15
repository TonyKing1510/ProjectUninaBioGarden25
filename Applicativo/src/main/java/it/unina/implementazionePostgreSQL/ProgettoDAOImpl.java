package it.unina.implementazionePostgreSQL;


import it.unina.connessioneDB.ConnessioneDatabase;
import it.unina.dao.ProgettoDAO;
import it.unina.model.Progetto;
import it.unina.model.Stagione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgettoDAOImpl implements ProgettoDAO {


    @Override
    public boolean addProgetto(Progetto progetto) {
        String query = "INSERT INTO progetto (stagione, id_utentecreatore, \"dataInizio\", \"dataFine\",titolo) VALUES (?::stagione, ?, ?, ?, ?)";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setObject(1, capitalize(progetto.getStagione().toString()));
            stmt.setInt(2, progetto.getIdUtenteCreatore());
            stmt.setDate(3, java.sql.Date.valueOf(progetto.getDataInizio().toString()));
            stmt.setDate(4, java.sql.Date.valueOf(progetto.getDataFine().toString()));
            stmt.setString(5, progetto.getTitolo());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;



        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }






    @Override
    public List<Progetto> getProgettiByIdUtente(int idUtente) {
        List<Progetto> progetti = new ArrayList<>();
        String query = "SELECT stagione, id_utentecreatore, \"dataInizio\", \"dataFine\",titolo FROM Progetto WHERE id_utentecreatore = ?";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idUtente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Progetto progetto = new Progetto();
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

}
