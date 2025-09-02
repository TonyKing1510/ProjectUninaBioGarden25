package it.unina.implementazionepostgresql;

import it.unina.connessioneDB.ConnessioneDatabase;
import it.unina.dao.LottoDAO;
import it.unina.dao.UtenteDAO;
import it.unina.model.Lotto;

import java.util.ArrayList;
import java.util.List;

public class LottoDAOImpl implements LottoDAO {

    private final UtenteDAO utenteDAO = new UtenteDAOImpl();

    private static final String COLONNA_ID_LOTTO = "id_lotto";
    private static final String COLONNA_INDIRIZZO = "indirizzo";
    private static final String COLONNA_SUPERFICIE = "superficie";
    private static final String COLONNA_ID_PROPRIETARIO = "id_proprietario";

    @Override
    public List<Lotto> getLottiDisponibili(int idUtenteProprietario) {
        String query = "SELECT l.* FROM lotto l WHERE l.id_progetto IS NULL AND l.id_proprietario = ?";
        List<Lotto> lotti = new ArrayList<>();

        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            // Imposta il parametro nella query
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
                    // Set proprietario
                    lotto.setProprietario(utenteDAO.getUtenteProprietario(resultSet.getInt(COLONNA_ID_PROPRIETARIO)));
                    return lotto;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

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
                    // Set proprietario
                    lotto.setProprietario(utenteDAO.getUtenteProprietario(resultSet.getInt(COLONNA_ID_PROPRIETARIO)));
                    return lotto;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

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
