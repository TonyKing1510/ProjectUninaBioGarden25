package it.unina.implementazionepostgresql;

import it.unina.connessionedb.ConnessioneDatabase;
import it.unina.dao.LottoDAO;
import it.unina.dao.UtenteDAO;
import it.unina.model.Lotto;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione dell'interfaccia {@link LottoDAO} per PostgreSQL.
 * <p>
 * Questa classe fornisce i metodi per la gestione e il recupero dei lotti,
 * sfruttando JDBC per le operazioni sul database.
 *
 * @author entn
 */
public class LottoDAOImpl implements LottoDAO {

    private final UtenteDAO utenteDAO = new UtenteDAOImpl();

    private static final String COLONNA_ID_LOTTO = "id_lotto";
    private static final String COLONNA_INDIRIZZO = "indirizzo";
    private static final String COLONNA_SUPERFICIE = "superficie";
    private static final String COLONNA_ID_PROPRIETARIO = "id_proprietario";

    /**
     * Restituisce tutti i lotti disponibili (cioè senza progetto associato)
     * di un determinato proprietario.
     *
     * @param idUtenteProprietario ID del proprietario dei lotti.
     * @return Lista di lotti disponibili. Può essere vuota.
     * @author entn
     */
    @Override
    public List<Lotto> getLottiDisponibili(int idUtenteProprietario) {
        String query = "SELECT l.* FROM lotto l WHERE l.id_progetto IS NULL AND l.id_proprietario = ?";
        List<Lotto> lotti = new ArrayList<>();

        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idUtenteProprietario);

            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Lotto lotto = new Lotto();
                    lotto.setIdLotto(resultSet.getInt(COLONNA_ID_LOTTO));
                    lotto.setNome(resultSet.getString("nome"));
                    lotto.setVia(resultSet.getString("via"));
                    lotto.setIndirizzo(resultSet.getString(COLONNA_INDIRIZZO));
                    lotto.setCap(resultSet.getString("cap"));
                    lotto.setSuperficie(resultSet.getDouble(COLONNA_SUPERFICIE));
                    lotto.setProprietario(utenteDAO.getUtenteProprietario(resultSet.getInt(COLONNA_ID_PROPRIETARIO)));
                    lotti.add(lotto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lotti;
    }

    /**
     * Restituisce un lotto dato il suo ID.
     *
     * @param idLotto ID del lotto da cercare.
     * @return Lotto trovato, oppure {@code null} se non esiste.
     * @author entn
     */
    @Override
    public Lotto getLottoById(int idLotto) {
        String query = "SELECT l.* FROM lotto l WHERE l.id_lotto = ?";
        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idLotto);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Lotto lotto = new Lotto(
                            resultSet.getInt(COLONNA_ID_LOTTO),
                            resultSet.getString("nome"),
                            resultSet.getDouble(COLONNA_SUPERFICIE),
                            resultSet.getString("via"),
                            resultSet.getString(COLONNA_INDIRIZZO),
                            resultSet.getString("cap")
                    );
                    lotto.setProprietario(utenteDAO.getUtenteProprietario(resultSet.getInt(COLONNA_ID_PROPRIETARIO)));
                    return lotto;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Restituisce un lotto associato a un determinato progetto.
     *
     * @param idProgetto ID del progetto.
     * @return Lotto associato al progetto, oppure {@code null} se non trovato.
     * @author entn
     */
    @Override
    public Lotto getLottoByIdProgetto(int idProgetto) {
        String query = "SELECT l.* FROM lotto l WHERE l.id_progetto = ?";
        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idProgetto);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Lotto lotto = new Lotto(
                            resultSet.getInt(COLONNA_ID_LOTTO),
                            resultSet.getString("nome"),
                            resultSet.getDouble(COLONNA_SUPERFICIE),
                            resultSet.getString("via"),
                            resultSet.getString(COLONNA_INDIRIZZO),
                            resultSet.getString("cap")
                    );
                    lotto.setProprietario(utenteDAO.getUtenteProprietario(resultSet.getInt(COLONNA_ID_PROPRIETARIO)));
                    return lotto;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Restituisce tutti i lotti appartenenti a un determinato proprietario.
     *
     * @param idProprietario ID del proprietario.
     * @return Lista di lotti. Può essere vuota.
     * @author entn
     */
    @Override
    public List<Lotto> getLottoByIdProprietario(int idProprietario) {
        String query = "SELECT l.* FROM lotto l WHERE l.id_proprietario = ?";
        List<Lotto> lotti = new ArrayList<>();

        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idProprietario);

            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Lotto lotto = new Lotto();
                    lotto.setIdLotto(resultSet.getInt(COLONNA_ID_LOTTO));
                    lotto.setNome(resultSet.getString("nome"));
                    lotto.setVia(resultSet.getString("via"));
                    lotto.setIndirizzo(resultSet.getString(COLONNA_INDIRIZZO));
                    lotto.setCap(resultSet.getString("cap"));
                    lotto.setSuperficie(resultSet.getDouble(COLONNA_SUPERFICIE));
                    lotto.setProprietario(utenteDAO.getUtenteProprietario(idProprietario));
                    lotti.add(lotto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lotti;
    }

    /**
     * Restituisce tutti i lotti associati a un determinato progetto.
     *
     * @param idProgetto ID del progetto.
     * @return Lista di lotti associati. Può essere vuota.
     * @author entn
     */
    @Override
    public List<Lotto> getLottiByIdProgetto(int idProgetto) {
        List<Lotto> lotti = new ArrayList<>();
        String query = "SELECT l.* FROM lotto l WHERE l.id_progetto = ?";
        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idProgetto);

            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Lotto lotto = new Lotto();
                    lotto.setIdLotto(resultSet.getInt(COLONNA_ID_LOTTO));
                    lotto.setNome(resultSet.getString("nome"));
                    lotto.setVia(resultSet.getString("via"));
                    lotto.setIndirizzo(resultSet.getString(COLONNA_INDIRIZZO));
                    lotto.setCap(resultSet.getString("cap"));
                    lotto.setSuperficie(resultSet.getDouble(COLONNA_SUPERFICIE));
                    lotto.setProprietario(utenteDAO.getUtenteProprietario(resultSet.getInt(COLONNA_ID_PROPRIETARIO)));
                    lotti.add(lotto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lotti;
    }
}
