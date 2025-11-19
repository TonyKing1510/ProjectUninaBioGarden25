package it.unina.implementazionepostgresql;

import it.unina.connessionedb.ConnessioneDatabase;
import it.unina.dao.NotificaDAO;
import it.unina.dao.ProgettoDAO;
import it.unina.dao.UtenteDAO;
import it.unina.model.Notifica;
import it.unina.model.Progetto;
import it.unina.model.Utente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementazione dell'interfaccia NotificaDAO per PostgreSQL.
 * Gestisce le operazioni CRUD delle notifiche.
 */
public class NotificaDAOImpl implements NotificaDAO {

    private static final Logger logger = Logger.getLogger(NotificaDAOImpl.class.getName());

    @Override
    public boolean inserisciNotifica(Notifica notifica) {
        String sql = "INSERT INTO notifiche " +
                "(id_proprietario, id_progetto, id_destinatario, tutti, titolo, descrizione, giorni_scadenza, tipo, data_creazione) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (var conn = ConnessioneDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, notifica.getOwner().getIdUtente());
            ps.setObject(2, notifica.getProgetto() != null ? notifica.getProgetto().getIdProgetto(): null);
            ps.setObject(3, notifica.getDestinatario() != null ? notifica.getDestinatario().getIdUtente() : null);
            ps.setBoolean(4, notifica.isTutti());
            ps.setString(5, notifica.getTitolo());
            ps.setString(6, notifica.getDescrizione());
            ps.setInt(7, notifica.getGiorniScadenza());
            ps.setString(8, notifica.getTipo());
            ps.setTimestamp(9, Timestamp.valueOf(notifica.getDataCreazione()));

            int result = ps.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Errore durante l'inserimento della notifica", e);
            return false;
        }
    }

    @Override
    public List<Notifica> getNotifichePerProprietario(int idProprietario) {
        List<Notifica> notifiche = new ArrayList<>();
        String sql = "SELECT * FROM notifiche WHERE id_proprietario = ? ORDER BY data_creazione DESC";

        try (var conn = ConnessioneDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProprietario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                notifiche.add(mappaNotifica(rs));
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Errore nel recupero delle notifiche per proprietario", e);
        }

        return notifiche;
    }

    @Override
    public List<Notifica> getNotifichePerProgetto(int idProgetto) {
        List<Notifica> notifiche = new ArrayList<>();
        String sql = "SELECT * FROM notifiche WHERE id_progetto = ? ORDER BY data_creazione DESC";

        try (var conn = ConnessioneDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProgetto);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                notifiche.add(mappaNotifica(rs));
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Errore nel recupero delle notifiche per lotto", e);
        }

        return notifiche;
    }

    @Override
    public List<Notifica> getNotifichePerDestinatario(int idDestinatario) {
        List<Notifica> notifiche = new ArrayList<>();
        String sql = "SELECT * FROM notifiche WHERE id_destinatario = ? OR tutti = TRUE ORDER BY data_creazione DESC";

        try (var conn = ConnessioneDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDestinatario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                notifiche.add(mappaNotifica(rs));
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Errore nel recupero delle notifiche per destinatario", e);
        }

        return notifiche;
    }

    @Override
    public boolean eliminaNotifica(int id) {
        String sql = "DELETE FROM notifiche WHERE id = ?";

        try (var conn = ConnessioneDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int result = ps.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Errore nell'eliminazione della notifica", e);
            return false;
        }
    }

    /**
     * Mappa un ResultSet su un oggetto Notifica
     * @param rs ResultSet dalla query
     * @return oggetto Notifica popolato
     */
    private Notifica mappaNotifica(ResultSet rs) throws Exception {
        Notifica n = new Notifica();

        n.setId(rs.getInt("id"));

        // Proprietario
        Utente owner;
        UtenteDAO utenteDAO = new UtenteDAOImpl();
        owner = utenteDAO.getUtenteById(rs.getInt("id_proprietario"));
        owner.aggiungiNotificaCreata(n);
        n.setOwner(owner);

        // Lotto (opzionale)
        int idProgetto = rs.getInt("id_progetto");
        if (!rs.wasNull()) {
            ProgettoDAO progettoDAO = new ProgettoDAOImpl();
            Progetto progetto;
            progetto = progettoDAO.getProgettoById(idProgetto);
            progetto.aggiungiNotifica(n);
            n.setProgetto(progetto);
        }

        // Destinatario (opzionale)
        int idDest = rs.getInt("id_destinatario");
        if (!rs.wasNull()) {
            Utente dest;
            UtenteDAO utenteDAODest = new UtenteDAOImpl();
            dest = utenteDAODest.getUtenteById(idDest);
            n.setDestinatario(dest);
        }

        n.setTutti(rs.getBoolean("tutti"));
        n.setTitolo(rs.getString("titolo"));
        n.setDescrizione(rs.getString("descrizione"));
        n.setGiorniScadenza(rs.getInt("giorni_scadenza"));
        n.setTipo(rs.getString("tipo"));
        Timestamp ts = rs.getTimestamp("data_creazione");
        if (ts != null) {
            n.setDataCreazione(ts.toLocalDateTime());
        }

        return n;
    }
}
