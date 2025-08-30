package it.unina.implementazionepostgresql;

import it.unina.connessioneDB.ConnessioneDatabase;
import it.unina.dao.LottoDAO;
import it.unina.dao.UtenteDAO;
import it.unina.model.Lotto;

import java.util.ArrayList;
import java.util.List;

public class LottoDAOImpl implements LottoDAO {

    private UtenteDAO utenteDAO = new UtenteDAOImpl();

    @Override
    public List<Lotto> getLottiDisponibili(int idUtenteProprietario) {
        String query = "SELECT * FROM lotto WHERE id_progetto IS NULL AND id_proprietario = ?";
        List<Lotto> lotti = new ArrayList<>();

        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            // Imposta il parametro nella query
            preparedStatement.setInt(1, idUtenteProprietario);

            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    Lotto lotto = new Lotto();
                    lotto.setIdLotto(resultSet.getInt("id_lotto"));
                    lotto.setNome(resultSet.getString("nome"));
                    lotto.setVia(resultSet.getString("via"));
                    lotto.setIndirizzo(resultSet.getString("indirizzo"));
                    lotto.setCap(resultSet.getString("cap"));
                    lotto.setSuperficie(resultSet.getDouble("superficie"));
                    lotto.setProprietario(utenteDAO.getUtenteProprietario(resultSet.getInt("id_proprietario")));
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
        String query = "SELECT * FROM lotto WHERE id_lotto = ?";
        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idLotto);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Lotto lotto = new Lotto(
                            resultSet.getInt("id_lotto"),
                            resultSet.getString("nome"),
                            resultSet.getDouble("superficie"),
                            resultSet.getString("via"),
                            resultSet.getString("indirizzo"),
                            resultSet.getString("cap")
                    );
                    // Set proprietario
                    lotto.setProprietario(utenteDAO.getUtenteProprietario(resultSet.getInt("id_proprietario")));
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
        System.out.println("LottoDAOImpl: getLottoByIdProgetto called with idProgetto = " + idProgetto);
        String query = "SELECT * FROM lotto WHERE id_progetto = ?";
        System.out.println("LottoDAOImpl: Query = " + query);
        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idProgetto);
            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Lotto lotto = new Lotto(
                            resultSet.getInt("id_lotto"),
                            resultSet.getString("nome"),
                            resultSet.getDouble("superficie"),
                            resultSet.getString("via"),
                            resultSet.getString("indirizzo"),
                            resultSet.getString("cap")
                    );
                    // Set proprietario
                    lotto.setProprietario(utenteDAO.getUtenteProprietario(resultSet.getInt("id_proprietario")));
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
        String query = "SELECT * FROM lotto WHERE id_proprietario = ?";
        List<Lotto> lotti = new ArrayList<>();

        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idProprietario);

            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Lotto lotto = new Lotto();
                    lotto.setIdLotto(resultSet.getInt("id_lotto"));
                    lotto.setNome(resultSet.getString("nome"));
                    lotto.setVia(resultSet.getString("via"));
                    lotto.setIndirizzo(resultSet.getString("indirizzo"));
                    lotto.setCap(resultSet.getString("cap"));
                    lotto.setSuperficie(resultSet.getDouble("superficie"));
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
        String query = "SELECT * FROM lotto WHERE id_progetto = ?";
        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idProgetto);

            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Lotto lotto = new Lotto();
                    lotto.setIdLotto(resultSet.getInt("id_lotto"));
                    lotto.setNome(resultSet.getString("nome"));
                    lotto.setVia(resultSet.getString("via"));
                    lotto.setIndirizzo(resultSet.getString("indirizzo"));
                    lotto.setCap(resultSet.getString("cap"));
                    lotto.setSuperficie(resultSet.getDouble("superficie"));
                    lotto.setProprietario(utenteDAO.getUtenteProprietario(resultSet.getInt("id_proprietario")));
                    lotti.add(lotto);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return lotti;
    }
}
