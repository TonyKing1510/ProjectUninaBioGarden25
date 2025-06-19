package it.unina.implementazionePostgreSQL;

import it.unina.connessioneDB.ConnessioneDatabase;
import it.unina.dao.LottoDAO;
import it.unina.model.Lotto;

import java.util.ArrayList;
import java.util.List;

public class LottoDAOImpl implements LottoDAO {
    @Override
    public List<Lotto> getLottiDisponibili(int idUtenteColtivatore) {
        String query = "SELECT * FROM lotto WHERE id_progetto IS NULL AND id_utentecoltivatore = ?";
        List<Lotto> lotti = new ArrayList<>();

        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            // Imposta il parametro nella query
            preparedStatement.setInt(1, idUtenteColtivatore);

            try (var resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Lotto lotto = new Lotto();
                    lotto.setIdLotto(resultSet.getInt("id_lotto"));
                    lotto.setNome(resultSet.getString("nome"));
                    lotto.setVia(resultSet.getString("via"));
                    lotto.setIndirizzo(resultSet.getString("indirizzo"));
                    lotto.setCap(resultSet.getString("cap"));
                    lotto.setSuperficie(resultSet.getDouble("superficie"));
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
                    return new Lotto(
                            resultSet.getInt("id_lotto"),
                            resultSet.getString("nome"),
                            resultSet.getDouble("superficie"),
                            resultSet.getString("via"),
                            resultSet.getString("indirizzo"),
                            resultSet.getString("cap")
                    );
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
                    return new Lotto(
                            resultSet.getInt("id_lotto"),
                            resultSet.getString("nome"),
                            resultSet.getDouble("superficie"),
                            resultSet.getString("via"),
                            resultSet.getString("indirizzo"),
                            resultSet.getString("cap")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
