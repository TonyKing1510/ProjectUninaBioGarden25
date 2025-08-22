package it.unina.implementazionePostgreSQL;

import it.unina.connessioneDB.ConnessioneDatabase;
import it.unina.dao.AttivitaDAO;
import it.unina.model.*;

import java.util.ArrayList;
import java.util.List;

public class AttivitaDAOImpl implements AttivitaDAO {
    @Override
    public void addAttivita(Attivita attivita) {
        String insertQuery = "INSERT INTO Attivita (stato, tipo, quantita_raccolta, quantita_usata, data_inizio, data_fine) VALUES (?, ?, ?, ?, ?, ?)";
        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(insertQuery)) {

            // Conversione enum a stringa
            preparedStatement.setString(1, attivita.getStato().name());
            preparedStatement.setString(2, attivita.getTipo().name());
            preparedStatement.setInt(3, attivita.getQuantitaRaccolta());
            preparedStatement.setInt(4, attivita.getQuantitaUsata());
            preparedStatement.setDate(5, attivita.getDataInizio());
            preparedStatement.setDate(6, attivita.getDataFine());

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.err.println("Errore durante l'inserimento dell'attività");
            e.printStackTrace();
        }
    }

    @Override
    public List<Attivita> getAttivitaCreate() {
        List<Attivita> attivitaList = new ArrayList<>();
        String query = "SELECT * FROM Attivita";
        try(var connection = ConnessioneDatabase.getConnection();
            var preparedStatement = connection.prepareStatement(query);
            var resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Attivita attivita = new Attivita();
                // Conversione enum con controllo null/sicurezza
                String statoStr = resultSet.getString("stato").toUpperCase();
                if (statoStr != null) {
                    attivita.setStato(StatoAttivita.valueOf(statoStr));
                }

                String tipoStr = resultSet.getString("tipo").toUpperCase();
                if (tipoStr != null) {
                    attivita.setTipo(TipoAttivita.valueOf(tipoStr));
                }

                attivita.setQuantitaRaccolta(resultSet.getInt("quantita_raccolta"));
                attivita.setQuantitaUsata(resultSet.getInt("quantita_usata"));
                attivita.setDataInizio(resultSet.getDate("data_inizio"));
                attivita.setDataFine(resultSet.getDate("data_fine"));


                attivitaList.add(attivita);
            }

        } catch (Exception e) {
            System.err.println("Errore durante il recupero delle colture disponibili");
            e.printStackTrace();
        }
        return attivitaList;
    }

}
