package it.unina.implementazionePostgreSQL;

import it.unina.connessioneDB.ConnessioneDatabase;
import it.unina.dao.ColtureDAO;
import it.unina.dao.LottoDAO;
import it.unina.model.Colture;
import it.unina.model.Lotto;
import it.unina.model.Stagione;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ColtureDAOImpl implements ColtureDAO {


    @Override
    public List<Colture> getColtureByIdLotto(int idLotto) {
        List<Colture> coltureList = new ArrayList<>();
        String query = "SELECT * FROM colture WHERE id_lotto = ?";

        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idLotto);
            var resultSet = preparedStatement.executeQuery();

            // Recupero lotto una sola volta
            LottoDAO lottoDAO = new LottoDAOImpl();
            Lotto lotto = lottoDAO.getLottoById(idLotto);

            while (resultSet.next()) {
                Colture coltura = new Colture();

                coltura.setIdColture(resultSet.getInt("id_colture"));
                coltura.setTitolo(resultSet.getString("titolo"));

                // Conversione enum con controllo null/sicurezza
                String stagStr = resultSet.getString("stagionalita").toUpperCase();
                if (stagStr != null) {
                    coltura.setStagionalita(Stagione.valueOf(stagStr));
                }

                String intervallo = resultSet.getString("tempo_maturazione");
                Duration durata = parsePostgresInterval(intervallo); // metodo sotto
                coltura.setTempoMaturazione(durata);

                // Setto direttamente il lotto ottenuto sopra
                coltura.setLotto(lotto);

                coltureList.add(coltura);
            }

        } catch (Exception e) {
            System.err.println("Errore durante il recupero delle colture per il lotto con id " + idLotto);
            e.printStackTrace();
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
        List<Colture> coltureList = new ArrayList<>();
        String query = "SELECT * FROM Colture WHERE id_lotto IS NULL";
        try (var connection = ConnessioneDatabase.getConnection();
             var preparedStatement = connection.prepareStatement(query);
             var resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Colture coltura = new Colture();
                coltura.setIdColture(resultSet.getInt("id_colture"));
                coltura.setTitolo(resultSet.getString("titolo"));

                // Conversione enum con controllo null/sicurezza
                String stagStr = resultSet.getString("stagionalita").toUpperCase();
                if (stagStr != null) {
                    coltura.setStagionalita(Stagione.valueOf(stagStr));
                }

                String intervallo = resultSet.getString("tempo_maturazione");
                Duration durata = parsePostgresInterval(intervallo); // metodo sotto
                coltura.setTempoMaturazione(durata);

                coltureList.add(coltura);
            }

        } catch (Exception e) {
            System.err.println("Errore durante il recupero delle colture disponibili");
            e.printStackTrace();
        }
        return coltureList;
    }
}
