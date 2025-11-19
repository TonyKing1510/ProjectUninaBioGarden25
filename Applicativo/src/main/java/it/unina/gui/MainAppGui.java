package it.unina.gui;

import it.unina.controller.MainPageColtivatoreController;
import it.unina.controller.MainPageController;
import it.unina.model.Utente;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MainAppGui {
    private MainAppGui() {
        // Prevent instantiation
    }


    /**
     * Inizializza la schermata principale dell'applicazione dopo il login.
     * <p>
     * Carica il file FXML della schermata principale, imposta l'utente loggato
     * nel controller e chiude lo stage precedente (tipicamente quello del login).
     *
     * @param previouStage Lo {@link Stage} attuale da chiudere (es. Login).
     * @param utente L'oggetto {@link Utente} che ha effettuato l'accesso.
     * @author entn
     */

    public static void initializeMainApp(Stage previouStage, Utente utente) {
        try {
            FXMLLoader loader = new FXMLLoader(MainAppGui.class.getResource("/it/unina/MainPage.fxml"));
            Parent mainRoot = loader.load();
            Scene scene = new Scene(mainRoot, 1540, 790);


            MainPageController controller = loader.getController();
            controller.setUtenteLoggato(utente);
            controller.setPreviousStage(previouStage);


            Stage newStage = new Stage();

            newStage.setTitle("MainApp");
            newStage.setScene(scene);
            newStage.show();

            Stage currentStage = previouStage;
            currentStage.close();
        }catch (IOException e) {
            e.printStackTrace();

            e.printStackTrace();


            showErrorDialog("Errore durante il caricamento della schermata principale.", e.getMessage());
        }
    }

    public static void initializeMainAppColtivatore(Stage previouStage,Utente utente) {
        try {
            FXMLLoader loader = new FXMLLoader(MainAppGui.class.getResource("/it/unina/MainPageColtivatore.fxml"));
            Parent mainRoot = loader.load();
            Scene scene = new Scene(mainRoot, 1540, 790);


            MainPageColtivatoreController controller = loader.getController();
            controller.setUtenteLoggato(utente);
            controller.setPreviousStage(previouStage);


            Stage newStage = new Stage();

            newStage.setTitle("MainApp");
            newStage.setScene(scene);
            newStage.show();

            Stage currentStage = previouStage;
            currentStage.close();
        }catch (IOException e) {
            e.printStackTrace();

            e.printStackTrace();


            showErrorDialog("Errore durante il caricamento della schermata principale.", e.getMessage());
        }
    }

    /**
     * Mostra un dialogo di errore con un titolo e un messaggio specificati.
     *
     * @param header Il titolo dell'errore.
     * @param content Il contenuto del messaggio di errore.
     * @author entn
     */
    private static void showErrorDialog(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
