package it.unina.implementazionepostgresql;

import it.unina.connessioneDB.ConnessioneDatabase;
import it.unina.dao.ColtureDAO;
import it.unina.dao.LottoDAO;
import it.unina.model.Colture;
import it.unina.model.Lotto;
import it.unina.model.Stagione;

import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ColtureDAOImpl implements ColtureDAO {

    private static final String COLONNA_ID_COLTURE = "id_colture";
    private static final String COLONNA_STAGIONALITA_COLTURE = "stagionalita";
    private static final String COLONNA_TEMPOMATURAZIONE_COLTURE = "tempo_maturazione";
    private static final String COLONNA_TITOLO_COLTURE = "titolo";

    @Override
    public List<Colture> getColtureByIdLotto(int idLotto) {
        Logger logger = Logger.getLogger(getClass().getName());
        List<Colture> coltureList = new ArrayList<>();
        String query = "SELECT c.* FROM colture c WHERE c.id_lotto = ?";

        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idLotto);
            var resultSet = preparedStatement.executeQuery();

            // Recupero lotto una sola volta
            LottoDAO lottoDAO = new LottoDAOImpl();
            Lotto lotto = lottoDAO.getLottoById(idLotto);

            while (resultSet.next()) {
                Colture coltura = new Colture();

                coltura.setIdColture(resultSet.getInt(COLONNA_ID_COLTURE));
                coltura.setTitolo(resultSet.getString(COLONNA_TITOLO_COLTURE));

                // Conversione enum con controllo null/sicurezza
                String stagStr = resultSet.getString(COLONNA_STAGIONALITA_COLTURE).toUpperCase();
                coltura.setStagionalita(Stagione.valueOf(stagStr));

                String intervallo = resultSet.getString(COLONNA_TEMPOMATURAZIONE_COLTURE);
                Duration durata = parsePostgresInterval(intervallo); // metodo sotto
                coltura.setTempoMaturazione(durata);

                // Setto direttamente il lotto ottenuto sopra
                coltura.setLotto(lotto);

                coltureList.add(coltura);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, e , () -> "Errore durante il recupero delle colture per il lotto con id " + idLotto);
        }

        return coltureList;
    }


    private Duration parsePostgresInterval(String intervallo) {
        // Esempio: "60 days" → Duration.ofDays(60)
        if (intervallo.contains("day")) {
            String[] parts = intervallo.split(" ");
            int giorni = Integer.parseInt(parts[0]);
            return Duration.ofDays(giorni);
        }
        return Duration.ZERO; // fallback
    }

    @Override
    public List<Colture> getColtureDisponibili() {
        Logger logger = Logger.getLogger(getClass().getName());
        List<Colture> coltureList = new ArrayList<>();
        String query = "SELECT c.* FROM Colture c WHERE c.id_lotto IS NULL";
        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query);
             var resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Colture coltura = new Colture();
                coltura.setIdColture(resultSet.getInt(COLONNA_ID_COLTURE));
                coltura.setTitolo(resultSet.getString(COLONNA_TITOLO_COLTURE));

                // Conversione enum con controllo null/sicurezza
                String stagStr = resultSet.getString(COLONNA_STAGIONALITA_COLTURE).toUpperCase();
                coltura.setStagionalita(Stagione.valueOf(stagStr));

                String intervallo = resultSet.getString(COLONNA_TEMPOMATURAZIONE_COLTURE);
                Duration durata = parsePostgresInterval(intervallo); // metodo sotto
                coltura.setTempoMaturazione(durata);

                coltureList.add(coltura);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, e , () -> "Errore durante il recupero delle colture disponibili");
        }
        return coltureList;
    }

    @Override
    public List<Colture> getColturaById(int idColtura) {
        Logger logger = Logger.getLogger(getClass().getName());
        List<Colture> coltureList = new ArrayList<>();
        String query = "SELECT c.* FROM Colture c WHERE c.id_colture = ?";
        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idColtura);
            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Colture coltura = new Colture();
                coltura.setIdColture(resultSet.getInt(COLONNA_ID_COLTURE));
                coltura.setTitolo(resultSet.getString(COLONNA_TITOLO_COLTURE));

                // Conversione enum con controllo null/sicurezza
                String stagStr = resultSet.getString(COLONNA_STAGIONALITA_COLTURE).toUpperCase();
                coltura.setStagionalita(Stagione.valueOf(stagStr));

                String intervallo = resultSet.getString(COLONNA_TEMPOMATURAZIONE_COLTURE);
                Duration durata = parsePostgresInterval(intervallo); // metodo sotto
                coltura.setTempoMaturazione(durata);

                Date dataInizioColtura = resultSet.getDate("datainizio_coltura");
                coltura.setDataInizioColtura(dataInizioColtura);

                coltureList.add(coltura);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, e , () -> "Errore durante il recupero delle colture per id " + idColtura);
        }
        return coltureList;
    }

    @Override
    public boolean salvaAssociazioni(Map<Colture, Integer> associazioni) {
        Logger logger = Logger.getLogger(getClass().getName());
        String sql = "UPDATE colture SET id_lotto = ? WHERE id_colture = ?";
        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            for (var entry : associazioni.entrySet()) {
                Colture coltura = entry.getKey();
                Integer idLotto = entry.getValue();

                preparedStatement.setObject(1, idLotto); // usa setObject per gestire null
                preparedStatement.setInt(2, coltura.getIdColture());
                preparedStatement.addBatch();
            }

            int[] results = preparedStatement.executeBatch();
            for (int res : results) {
                if (res == 0) {
                    logger.log(Level.WARNING,"Nessuna riga aggiornata per una delle associazioni.");
                    return false;
                }
            }
            return true;

        } catch (Exception e) {
            logger.log(Level.SEVERE, e , () -> "Errore durante il salvataggio delle associazioni colture-lotti");
            return false;
        }
    }
}
