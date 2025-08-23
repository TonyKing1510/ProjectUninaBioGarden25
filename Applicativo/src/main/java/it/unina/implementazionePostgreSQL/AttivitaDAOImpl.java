package it.unina.implementazionePostgreSQL;

import it.unina.connessioneDB.ConnessioneDatabase;
import it.unina.dao.AttivitaDAO;
import it.unina.model.*;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AttivitaDAOImpl implements AttivitaDAO {
            @Override
            public boolean addAttivita (Attivita attivita,int idColtura, int idColtivatore, int idProprietario){
            String insertQuery = "INSERT INTO Attivita (stato, tipo, quantita_raccolta, quantita_usata, datai, dataf) VALUES (?::stato_attivita, ?::tipoattivitaenum, ?, ?, ?, ?)";
            String insertUtenteColturaQuery = "INSERT INTO utente_coltura (id_proprietario, id_coltivatore, id_colture) VALUES (?, ?, ?)";
            String insertAttivitaUtente = "INSERT INTO attivita_utente (id_attivita, id_utente) VALUES (?, ?)";
            System.out.println("Debug: Coltura ID: " + idColtura);
            try (var connection = ConnessioneDatabase.getConnection();
                 var preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

                // 1. Inserisco Attivita
                preparedStatement.setString(1, attivita.getStato().name().toLowerCase());
                preparedStatement.setString(2, attivita.getTipo().name().toLowerCase());
                preparedStatement.setInt(3, attivita.getQuantitaRaccolta());
                preparedStatement.setInt(4, attivita.getQuantitaUsata());
                preparedStatement.setDate(5, attivita.getDataInizio());
                preparedStatement.setDate(6, attivita.getDataFine());

                int rows = preparedStatement.executeUpdate();

                if (rows > 0) {
                    // Recupero l'id generato dell'attività
                    try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                        if (rs.next()) {
                            int idAttivita = rs.getInt(1);

                            // 2. Inserisco su utente_coltura
                            try (var ps2 = connection.prepareStatement(insertUtenteColturaQuery)) {
                                ps2.setInt(1, idProprietario);
                                ps2.setInt(2, idColtivatore);
                                ps2.setInt(3, idColtura);
                                ps2.executeUpdate();
                            }

                            // 3. Inserisco su attivita_utente
                            try (var ps3 = connection.prepareStatement(insertAttivitaUtente)) {
                                ps3.setInt(1, idAttivita);
                                ps3.setInt(2, idColtivatore);
                                ps3.executeUpdate();
                            }
                        }
                    }
                }

                return true;

            } catch (Exception e) {
                System.err.println("Errore durante l'inserimento dell'attività");
                e.printStackTrace();
                return false;
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
