package it.unina.connessioneDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Gestisce la connessione al database PostgreSQL per l'applicazione UninaBioGarden.
 * Utilizza il pattern Singleton per garantire una singola connessione condivisa.
 * La connessione avviene tramite i parametri forniti da Neon DB o un altro provider PostgreSQL.
 *
 * @author entn
 * @version 1.0
 */
public class ConnessioneDatabase {
    ConnessioneDatabase(){};
    private static final String URL = "jdbc:postgresql://ep-rough-thunder-abcu1orj-pooler.eu-west-2.aws.neon.tech/UninaGarden25?sslmode=require";
    private static final String USER = "UninaGarden25_owner";
    private static final String PASSWORD = "npg_ydSrY90LDifh";

    private static Connection conn = null;

    /**
     * Restituisce l'istanza di connessione attiva.
     * Se la connessione non è attiva o è chiusa, ne crea una nuova.
     *
     * @return istanza attiva di {@link Connection}
     * @throws SQLException se si verifica un errore durante la connessione
     * @author entn
     */
    public static Connection getConnection() throws SQLException {
        Logger logger = Logger.getLogger(ConnessioneDatabase.class.getName());
        if (conn == null || conn.isClosed()) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                logger.info("Connessione al database riuscita.");
            } catch (SQLException e) {
                logger.severe("Errore di connessione al database: " + e.getMessage());
                throw e;
            }
        }
        return conn;
    }

    /**
     * Chiude la connessione al database se attiva.
     * @author entn
     */
    public static void chiudiConnessione() {
        Logger logger = Logger.getLogger(ConnessioneDatabase.class.getName());
        if (conn != null) {
            try {
                conn.close();
                logger.info("Connessione al database chiusa.");
            } catch (SQLException e) {
                logger.severe("Errore durante la chiusura della connessione: " + e.getMessage());
              
            }
        }
    }
}
