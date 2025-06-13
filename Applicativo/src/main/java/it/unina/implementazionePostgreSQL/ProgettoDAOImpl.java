package it.unina.implementazionePostgreSQL;


import it.unina.connessioneDB.ConnessioneDatabase;
import it.unina.dao.ProgettoDAO;
import it.unina.model.Progetto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProgettoDAOImpl implements ProgettoDAO {


    @Override
    public boolean addProgetto(Progetto progetto) {
        String query = "INSERT INTO progetto (stagione, id_utentecreatore, \"dataInizio\", \"dataFine\") VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnessioneDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, progetto.getStagione().toString().toLowerCase());
            stmt.setInt(2, progetto.getIdUtenteCreatore());
            stmt.setDate(3, java.sql.Date.valueOf(progetto.getDataInizio().toString()));
            stmt.setDate(4, java.sql.Date.valueOf(progetto.getDataFine().toString()));

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;



        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }





    @Override
    public boolean getProgettoById(Progetto progetto) {
        return false;
    }

}
