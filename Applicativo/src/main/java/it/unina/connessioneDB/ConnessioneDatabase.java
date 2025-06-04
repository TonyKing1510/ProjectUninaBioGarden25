package it.unina.connessioneDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestisce la connessione al database PostgreSQL per l'applicazione UninaBioGarden.
 * Utilizza il pattern Singleton per garantire una singola connessione condivisa.
 * La connessione avviene tramite i parametri forniti da Neon DB o un altro provider PostgreSQL.
 *
 * @author entn
 * @version 1.0
 */
public class ConnessioneDatabase {
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
        if (conn == null || conn.isClosed()) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connessione al database riuscita.");
            } catch (SQLException e) {
                System.err.println(" Errore di connessione al database:");
                e.printStackTrace();
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
        if (conn != null) {
            try {
                conn.close();
                System.out.println("🔌 Connessione chiusa.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
