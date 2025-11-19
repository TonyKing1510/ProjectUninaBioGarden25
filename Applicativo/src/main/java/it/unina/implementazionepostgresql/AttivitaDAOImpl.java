package it.unina.implementazionepostgresql;

import it.unina.dao.ColtureDAO;
import it.unina.stats.StatisticheColtura;
import it.unina.connessionedb.ConnessioneDatabase;
import it.unina.dao.AttivitaDAO;
import it.unina.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Implementazione dell'interfaccia {@link AttivitaDAO} per il database PostgreSQL.
 * Gestisce le operazioni CRUD relative alle attività, come inserimento, aggiornamento
 * e recupero delle attività, associando utenti, colture e progetti.
 *
 * @author entn, Sderr12
 */
public class AttivitaDAOImpl implements AttivitaDAO {

    private static final String COLONNA_ID_ATTIVITA = "id_attivita";
    private static final String COLONNA_STATO_ATTIVITA = "stato";
    private static final String COLONNA_QUANTITA_RACCOLTA = "quantita_raccolta";
    private static final String COLONNA_QUANTITA_USATA = "quantita_usata";


    /**
     * Aggiunge una nuova attività nel database, associandola al coltivatore, al proprietario e alla coltura.
     *
     * @param attivita       oggetto Attivita da inserire
     * @param idColtura      ID della coltura associata all'attività
     * @param idColtivatore  ID dell'utente coltivatore
     * @param idProprietario ID dell'utente proprietario
     * @return true se l'inserimento è andato a buon fine, false altrimenti
     * @author entn
     */
    @Override
    public boolean addAttivita(Attivita attivita, int idColtura, int idColtivatore, int idProprietario) {
        Logger logger = Logger.getLogger(getClass().getName());

        String insertQuery = "INSERT INTO Attivita (stato, tipo, quantita_raccolta, quantita_usata, datai, dataf, id_coltura) VALUES (?::stato_attivita, ?::tipoattivitaenum, ?, ?, ?, ?,?)";
        String insertUtenteColturaQuery = "INSERT INTO utente_coltura (id_proprietario, id_coltivatore, id_colture) VALUES (?, ?, ?)";
        String insertAttivitaUtente = "INSERT INTO attivita_utente (id_attivita, id_utente) VALUES (?, ?)";

        // Debug log
        logger.log(Level.INFO, () -> "Debug: Coltura ID: " + idColtura);

        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            // 1. Inserisco Attivita
            System.out.println("Stato Attivita: " + attivita.getStato());
            preparedStatement.setString(1, String.valueOf(attivita.getStato()).toLowerCase());
            preparedStatement.setString(2, attivita.getTipo().name().toLowerCase());
            preparedStatement.setInt(3, attivita.getQuantitaRaccolta());
            preparedStatement.setInt(4, attivita.getQuantitaUsata());
            preparedStatement.setDate(5, attivita.getDataInizio());
            preparedStatement.setDate(6, attivita.getDataFine());
            ColtureDAO coltureDAO = new ColtureDAOImpl();
            Colture coltura = coltureDAO.getColturaById(idColtura);
            coltura.addAttivita(attivita);
            preparedStatement.setInt(7, attivita.setColtura(coltura));

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
            logger.log(Level.SEVERE, e, () -> "Errore durante l'inserimento dell'attività");
            return false;
        }
    }


    /**
     * Recupera tutte le attività presenti nel database.
     *
     * @return lista di tutte le attività create
     * @author entn, Sderr12
     */
    @Override
    public List<Attivita> getAttivitaCreate() {
        Logger logger = Logger.getLogger(getClass().getName());
        List<Attivita> attivitaList = new ArrayList<>();
        String query = "SELECT a.* FROM Attivita a";

        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query);
             var resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Attivita attivita = new Attivita();
                int idAttivita = resultSet.getInt(COLONNA_ID_ATTIVITA);
                attivita.setIdAttivita(idAttivita);

                String statoStr = resultSet.getString(COLONNA_STATO_ATTIVITA);
                if (statoStr != null) {
                    attivita.setStato(StatoAttivita.valueOf(statoStr.toUpperCase()));
                }

                String tipoStr = resultSet.getString("tipo");
                if (tipoStr != null) {
                    attivita.setTipo(TipoAttivita.valueOf(tipoStr.toUpperCase()));
                }

                attivita.setQuantitaRaccolta(resultSet.getInt(COLONNA_QUANTITA_RACCOLTA));
                attivita.setQuantitaUsata(resultSet.getInt(COLONNA_QUANTITA_USATA));
                attivita.setDataInizio(resultSet.getDate("data_inizio"));
                attivita.setDataFine(resultSet.getDate("data_fine"));
                attivita.setColtura(new Colture());

                attivitaList.add(attivita);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, e, () -> "Errore durante il recupero delle colture disponibili");
        }

        return attivitaList;
    }

    /**
     * Recupera le attività associate a una determinata coltura, raggruppando gli utenti assegnati.
     *
     * @param idColtura ID della coltura
     * @return mappa con chiave {@link Attivita} e lista di utenti {@link Utente} assegnati
     * @author entn, Sderr12
     */
    @Override
    public Map<Attivita, List<Utente>> getAttivitaByIdColture(int idColtura) {
        Logger logger = Logger.getLogger(getClass().getName());
        Map<Attivita, List<Utente>> result = new HashMap<>();
        String query = "SELECT a.*, au.id_utente,u.nome FROM Attivita a " +
                "JOIN attivita_utente au ON a.id_attivita = au.id_attivita " +
                "JOIN utente_coltura uc ON au.id_utente = uc.id_coltivatore " +
                "JOIN utente u ON uc.id_coltivatore = u.id_utente " +
                "WHERE uc.id_colture = ?";

        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idColtura);

            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Attivita attivita = new Attivita();

                    int idAttivita = resultSet.getInt(COLONNA_ID_ATTIVITA);
                    int idUtenteColtivatore = resultSet.getInt("id_utente");
                    Utente utenteColtivatore = new Utente();
                    utenteColtivatore.setIdUtente(idUtenteColtivatore);
                    attivita.setIdAttivita(idAttivita);
                    attivita.setResponsabile(utenteColtivatore);

                    String statoStr = resultSet.getString(COLONNA_STATO_ATTIVITA);
                    StatoAttivita stato = statoStr.equals("programmata") ? StatoAttivita.PROGRAMMATA :
                            statoStr.equals("in corso") ? StatoAttivita.IN_CORSO :
                                    StatoAttivita.COMPLETATA;
                    attivita.setStato(stato);

                    String tipoStr = resultSet.getString("tipo");
                    if (tipoStr != null) {
                        attivita.setTipo(TipoAttivita.valueOf(tipoStr.toUpperCase()));
                    }

                    attivita.setQuantitaRaccolta(resultSet.getInt(COLONNA_QUANTITA_RACCOLTA));
                    attivita.setQuantitaUsata(resultSet.getInt(COLONNA_QUANTITA_USATA));
                    attivita.setDataInizio(resultSet.getDate("datai"));
                    attivita.setDataFine(resultSet.getDate("dataf"));
                    ColtureDAO coltureDAO = new ColtureDAOImpl();
                    Colture coltura = coltureDAO.getColturaById(idColtura);
                    coltura.addAttivita(attivita);
                    attivita.setColtura(coltura);

                    Utente utente = new Utente();
                    utente.setIdUtente(resultSet.getInt("id_utente"));
                    utente.setNome(resultSet.getString("nome"));
                    utente.setAttivita(attivita);

                    result.computeIfAbsent(attivita, k -> new ArrayList<>()).add(utente);
                }
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, e, () -> "Errore durante il recupero delle attività per coltura ID: " + idColtura);
        }

        return result;
    }

    /**
     * Recupera le statistiche delle attività per ciascun lotto e coltura di un progetto.
     * La statistica considera solo le attività di tipo "raccolta".
     *
     * @param progetto oggetto {@link Progetto} di riferimento
     * @return mappa con chiave {@link Lotto} e valore mappa di {@link Colture} e relative {@link StatisticheColtura}
     * @author entn, Sderr12
     */
    @Override
    public Map<Lotto, Map<Colture, StatisticheColtura>> getStatistichePerLottiEColtureByIdProgetto(Progetto progetto) throws StatisticheColtura.StatisticheException{
            Map<Lotto, Map<Colture, List<Attivita>>> rawData = new HashMap<>();
            String query = "SELECT a.*,c.titolo ,c.id_colture, l.id_lotto, l.nome, uc.id_coltivatore " +
                    "FROM Attivita a " +
                    "JOIN attivita_utente au ON a.id_attivita = au.id_attivita " +
                    "JOIN utente_coltura uc ON au.id_utente = uc.id_coltivatore " +
                    "JOIN Colture c ON uc.id_colture = c.id_colture " +
                    "JOIN Lotto l ON c.id_lotto = l.id_lotto " +
                    "WHERE l.id_progetto = ? and a.tipo = 'raccolta'";

            try (var connection = ConnessioneDatabase.getConnection();
                 var preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setInt(1, progetto.getIdProgetto());

                try (var resultSet = preparedStatement.executeQuery()) {
                    populateRawData(resultSet, rawData);
                }

                // Conversione da liste di Attivita a StatisticheColtura
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
                throw new StatisticheColtura.StatisticheException(
                        "Errore durante il recupero delle statistiche per progetto ID: " + progetto.getIdProgetto(), e);
            }
        }

    /**
     * Aggiorna lo stato di un'attività esistente nel database.
     *
     * @param attivita oggetto {@link Attivita} da aggiornare
     * @author entn, Sderr12
     */
    @Override
    public void updateAttivita(Attivita attivita) {
        Logger logger = Logger.getLogger(getClass().getName());
        String updateQuery = "UPDATE Attivita SET stato = ?::stato_attivita WHERE id_attivita = ?";

        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, attivita.getStato().toString());
            preparedStatement.setInt(2, attivita.getIdAttivita());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                logger.log(Level.WARNING, "Nessuna attività trovata con ID: {0}", attivita.getIdAttivita());
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, e, () -> "Errore durante l'aggiornamento dell'attività con ID: " + attivita.getIdAttivita());
        }

    }

    /**
     * Popola la struttura dati rawData con i risultati di un {@link ResultSet} SQL.
     * Utilizzato internamente per il calcolo delle statistiche per lotti e colture.
     *
     * @param resultSet risultato della query SQL
     * @param rawData   mappa da popolarel con chiave {@link Lotto} e valore mappa di {@link Colture} e lista di {@link Attivita}
     * @throws SQLException in caso di errore durante l'accesso al ResultSet
     * @author entn, Sderr12
     */
    private void populateRawData(ResultSet resultSet, Map<Lotto, Map<Colture, List<Attivita>>> rawData) throws SQLException {
        while (resultSet.next()) {
            Lotto lotto = new Lotto();
            lotto.setIdLotto(resultSet.getInt("id_lotto"));
            lotto.setNome(resultSet.getString("nome"));

            Colture coltura = new Colture();
            coltura.setIdColture(resultSet.getInt("id_colture"));
            coltura.setTitolo(resultSet.getString("titolo"));


            Attivita attivita = new Attivita();
            attivita.setIdAttivita(resultSet.getInt(COLONNA_ID_ATTIVITA));
            attivita.setTipo(TipoAttivita.valueOf(resultSet.getString("tipo").toUpperCase()));
            attivita.setStato(StatoAttivita.fromString(resultSet.getString(COLONNA_STATO_ATTIVITA)));
            attivita.setQuantitaRaccolta(resultSet.getInt(COLONNA_QUANTITA_RACCOLTA));
            attivita.setQuantitaUsata(resultSet.getInt(COLONNA_QUANTITA_USATA));
            attivita.setDataInizio(resultSet.getDate("datai"));
            attivita.setDataFine(resultSet.getDate("dataf"));
            Utente coltivatore = new Utente();
            coltivatore.setIdUtente(resultSet.getInt("id_coltivatore"));
            coltivatore.setAttivita(attivita);
            attivita.setResponsabile(coltivatore);
            attivita.setColtura(coltura);
            coltura.addAttivita(attivita);

            rawData
                    .computeIfAbsent(lotto, k -> new HashMap<>())
                    .computeIfAbsent(coltura, k -> new ArrayList<>())
                    .add(attivita);
        }
    }

}