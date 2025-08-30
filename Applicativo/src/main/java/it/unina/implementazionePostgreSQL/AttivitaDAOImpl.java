package it.unina.implementazionePostgreSQL;

import it.unina.Stats.StatisticheColtura;
import it.unina.connessioneDB.ConnessioneDatabase;
import it.unina.dao.AttivitaDAO;
import it.unina.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttivitaDAOImpl implements AttivitaDAO {
    @Override
    public boolean addAttivita(Attivita attivita, int idColtura, int idColtivatore, int idProprietario) {
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
        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query);
             var resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Attivita attivita = new Attivita();
                int idAttivita = resultSet.getInt("id_attivita");
                attivita.setIdAttivita(idAttivita);
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

    @Override
    public Map<Attivita, List<Utente>> getAttivitaByIdColture(int idColtura) {
        Map<Attivita, List<Utente>> result = new HashMap<>();
        String query = "SELECT a.*, au.id_utente FROM Attivita a " +
                "JOIN attivita_utente au ON a.id_attivita = au.id_attivita " +
                "JOIN utente_coltura uc ON au.id_utente = uc.id_coltivatore " +
                "WHERE uc.id_colture = ?";

        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idColtura);

            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Attivita attivita = new Attivita();

                    int idAttivita = resultSet.getInt("id_attivita");
                    attivita.setIdAttivita(idAttivita);

                    // Conversione enum con controllo null/sicurezza
                    String statoStr = resultSet.getString("stato");
                    if (statoStr != null) {
                        attivita.setStato(StatoAttivita.valueOf(statoStr.toUpperCase()));
                    }

                    String tipoStr = resultSet.getString("tipo");
                    if (tipoStr != null) {
                        attivita.setTipo(TipoAttivita.valueOf(tipoStr.toUpperCase()));
                    }

                    attivita.setQuantitaRaccolta(resultSet.getInt("quantita_raccolta"));
                    attivita.setQuantitaUsata(resultSet.getInt("quantita_usata"));
                    attivita.setDataInizio(resultSet.getDate("datai"));
                    attivita.setDataFine(resultSet.getDate("dataf"));

                    Utente utente = new Utente();
                    utente.setIdUtente(resultSet.getInt("id_utente"));
                    // puoi popolare altri campi dell'utente se servono

                    // aggiunge l’utente alla lista associata all’attività
                    result.computeIfAbsent(attivita, k -> new ArrayList<>()).add(utente);
                }
            }

        } catch (Exception e) {
            System.err.println("Errore durante il recupero delle attività per coltura ID: " + idColtura);
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Map<Lotto, Map<Colture, StatisticheColtura>> getStatistichePerLottiEColtureByIdProgetto(Progetto progetto) {
        Map<Lotto, Map<Colture, List<Attivita>>> rawData = new HashMap<>();

        String query = "SELECT a.*,c.titolo ,c.id_colture, l.id_lotto " +
                "FROM Attivita a " +
                "JOIN attivita_utente au ON a.id_attivita = au.id_attivita " +
                "JOIN utente_coltura uc ON au.id_utente = uc.id_coltivatore " +  // spazio finale
                "JOIN Colture c ON uc.id_colture = c.id_colture " +
                "JOIN Lotto l ON c.id_lotto = l.id_lotto " +
                "WHERE l.id_progetto = ? and a.tipo = 'raccolta'";


        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, progetto.getIdProgetto());


            try (var resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    // Recupero entità base
                    Lotto lotto = new Lotto();
                    lotto.setIdLotto(resultSet.getInt("id_lotto"));
                    Colture coltura = new Colture();
                    coltura.setIdColture(resultSet.getInt("id_colture"));
                    coltura.setTitolo(resultSet.getString("titolo"));
                    Attivita attivita = new Attivita();

                    attivita.setIdAttivita(resultSet.getInt("id_attivita"));
                    attivita.setTipo(TipoAttivita.valueOf(resultSet.getString("tipo").toUpperCase()));
                    attivita.setStato(StatoAttivita.valueOf(resultSet.getString("stato").toUpperCase()));
                    attivita.setQuantitaRaccolta(resultSet.getInt("quantita_raccolta"));
                    attivita.setQuantitaUsata(resultSet.getInt("quantita_usata"));
                    attivita.setDataInizio(resultSet.getDate("datai"));
                    attivita.setDataFine(resultSet.getDate("dataf"));
                    // eventuali altri campi

                    // Inserisco in struttura grezza
                    rawData
                            .computeIfAbsent(lotto, k -> new HashMap<>())
                            .computeIfAbsent(coltura, k -> new ArrayList<>())
                            .add(attivita);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            // Ora converto la struttura con liste di Attivita in StatisticheColtura
            Map<Lotto, Map<Colture, StatisticheColtura>> result = new HashMap<>();
            for (var entryLotto : rawData.entrySet()) {
                Lotto lotto = entryLotto.getKey();
                Map<Colture, StatisticheColtura> coltureStats = new HashMap<>();

                for (var entryColtura : entryLotto.getValue().entrySet()) {
                    Colture coltura = entryColtura.getKey();
                    List<Attivita> attivitaList = entryColtura.getValue();
                    StatisticheColtura stats = StatisticheColtura.fromAttivita(attivitaList);
                    coltureStats.put(coltura, stats);
                }

                result.put(lotto, coltureStats);
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateAttivita(Attivita attivita) {
        String updateQuery = "UPDATE Attivita SET stato = ?::stato_attivita WHERE id_attivita = ?";

        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, attivita.getStato().toString());
            preparedStatement.setInt(2, attivita.getIdAttivita());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                System.err.println("Nessuna attività trovata con ID: " + attivita.getIdAttivita());
            }

        } catch (Exception e) {
            System.err.println("Errore durante l'aggiornamento dell'attività con ID: " + attivita.getIdAttivita());
            e.printStackTrace();
        }

    }
}