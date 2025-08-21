package it.unina.implementazionePostgreSQL;

import it.unina.connessioneDB.ConnessioneDatabase;
import it.unina.dao.AttivitaDAO;
import it.unina.model.*;

import java.util.ArrayList;
import java.util.List;

public class AttivitaDAOImpl implements AttivitaDAO {
    @Override
    public void addAttivita(Attivita attivita) {
        // Implementazione per aggiungere un'attività al database
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
