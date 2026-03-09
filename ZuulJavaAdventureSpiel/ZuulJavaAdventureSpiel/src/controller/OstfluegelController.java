package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import model.Player;
import model.Inventory;
import model.Key;


/**
 * Controller-Klasse für den Ostflügel.
 * Diese Klasse steuert die Navigation und Interaktionen im Ostflügel des Spiels.
 */
public class OstfluegelController {

	@FXML
    private Button northButton;
    @FXML
    private Button southButton;
    @FXML
    private Button eastButton;
    @FXML
    private Button westButton;
    @FXML
    private Button backButton;
    @FXML
    private Button showInventoryButton;
    @FXML
    private Button pickUpButton;
    @FXML
    private Button dropItemButton;
    
    private static Player player = BaseSceneController.getPlayer();

    
    /**
     * Initialisiert die Benutzeroberfläche und verknüpft die Buttons mit ihren Aktionen.
     */
    public void initialize() {
        northButton.setOnAction(e -> goToBibliothek());
        southButton.setOnAction(e -> goToMensa());
        eastButton.setOnAction(e -> goToSekretariat());
        westButton.setOnAction(e -> goToBaseScene());
       // backButton.setOnAction(e -> goToBaseScene());
        pickUpButton.setOnAction(e -> showPickUpError());
    }
    
    /**
     * Zeigt eine Fehlermeldung an, wenn kein Gegenstand zum Aufheben vorhanden ist.
     */
    private void showPickUpError() {
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Fehler");
    	alert.setHeaderText(null);
    	alert.setContentText("In diesem Raum liegt kein Gegenstand der aufgehoben werden kann.");
        alert.showAndWait();
    }
    
    /**
     * Navigiert zur Bibliothek (nördlicher Raum).
     */
    private void goToBibliothek() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/bibliothek.fxml"));
            Stage stage = (Stage) northButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void goToBaseScene() {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BaseScene.fxml"));
    		Stage stage = (Stage) westButton.getScene().getWindow();
    		Scene scene = new Scene(loader.load());
    		stage.setScene(scene);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    
    private void goToMensa() {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mensa.fxml"));
    		Stage stage = (Stage) southButton.getScene().getWindow();
    		Scene scene = new Scene(loader.load());
    		stage.setScene(scene);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    /**
     * Prüft, ob der Spieler den Schlüssel besitzt, und navigiert ggf. zum Sekretariat.
     */
    private void goToSekretariat() {
    	
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sekretariat.fxml"));
            Stage stage = (Stage) eastButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Überprüft, ob der Spieler den passenden Schlüssel für das Sekretariat besitzt.
     *
     * @return true, wenn der Spieler den passenden Schlüssel besitzt; andernfalls false.
     */
    private boolean playerHasKey() {
        return player.getInventory().values().stream()
                .anyMatch(item -> item instanceof Key && ((Key) item).getRoomName().equals("Sekretariat"));
    }

    /**
     * Navigiert zur Basisszene.
     */
//    private void goToBaseScene() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BaseScene.fxml"));
//            Stage stage = (Stage) backButton.getScene().getWindow();
//            Scene scene = new Scene(loader.load());
//            stage.setScene(scene);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    
    /**
     * Zeigt eine Fehlermeldung an, wenn der Spieler in eine nicht erlaubte Richtung gehen möchte.
     *
     * @param direction Die nicht erlaubte Richtung.
     */
    private void showError(String direction) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(null);
        alert.setContentText("Du kannst nicht nach " + direction + " gehen.");
        alert.showAndWait();
    }
    
    
    /** Zeigt den Inhalt des Spieler-Inventars an. 
     * Falls das Inventar leer ist, wird eine entsprechende Meldung angezeigt. 
     *  Ansonsten wird eine Liste der im Inventar befindlichen Gegenstände angezeigt. 
     */
    @FXML
    private void showInventoryWindow() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Inventar");
        alert.setHeaderText("Dein Inventar");

        // Zeige alle Items aus dem Inventar an oder melde, dass es leer ist
        if (Inventory.getItems().isEmpty()) {
            alert.setContentText("Das Inventar ist leer.");
        } else {
            alert.setContentText(String.join(", ", Inventory.getItems()));
        }

        alert.showAndWait();
    }
    
    
    /** 
     * Ermöglicht dem Spieler, einen Gegenstand aus seinem Inventar abzulegen.
     * Falls das Inventar leer ist, wird eine entsprechende Meldung angezeigt.
     * Ansonsten wird ein Dialog zur Auswahl des abzulegenden Gegenstands geöffnet, 
     * und der ausgewählte Gegenstand wird aus dem Inventar entfernt.  
     */
    @FXML
    private void dropItem() {
        // Überprüfen, ob das Inventar leer ist
        if (Inventory.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Inventar");
            alert.setHeaderText(null);
            alert.setContentText("Dein Inventar ist leer, es gibt nichts abzulegen!");
            alert.showAndWait();
        } else {
            // Dialog zur Auswahl des abzulegenden Items
            ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(null, Inventory.getItems());
            choiceDialog.setTitle("Item ablegen");
            choiceDialog.setHeaderText("Wähle ein Item aus, das du ablegen möchtest:");
            choiceDialog.setContentText("Item:");

            // Zeige den Dialog und warte auf die Auswahl
            choiceDialog.showAndWait().ifPresent(selectedItem -> {
                // Entferne das ausgewählte Item
                Inventory.removeItem(selectedItem);

                // Erfolgsmeldung
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Item abgelegt");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Das Item \"" + selectedItem + "\" wurde erfolgreich abgelegt.");
                successAlert.showAndWait();
            });
        }
    }

}