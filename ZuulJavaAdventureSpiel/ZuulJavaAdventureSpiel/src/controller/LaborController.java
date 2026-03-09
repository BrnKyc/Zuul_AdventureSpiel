package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import model.Player;
import model.Inventory;
import model.Item;
import model.Key;
import model.Room;

/**
 * Diese Klasse steuert die Labor-Szene in der Anwendung.
 * Sie verwaltet die Aktionen des Spielers innerhalb des Raums "Labor".
 */
public class LaborController {

	
	 private boolean keyCollected = false;
	
    /**
     * Der Spieler, der durch das Spiel navigiert. 
     * Die Instanz wird aus dem BaseSceneController übernommen.
     */
    private static Player player = BaseSceneController.getPlayer();

    /**
     * Der aktuelle Raum, in dem der Spieler sich befindet (Labor).
     */
    private Room currentRoom;

    /**
     * Ein Schlüssel, der im Raum "Labor" verfügbar ist.
     */
    private Key key;

    // FXML-Elemente, die in der Labor-Szene verwendet werden
    @FXML
    private GridPane inventoryGrid;
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
    	
    /**
     * Initialisiert die Labor-Szene.
     * Diese Methode wird automatisch nach dem Laden der FXML-Datei aufgerufen.
     * Sie setzt den aktuellen Raum auf "Labor", fügt einen Schlüssel hinzu 
     * und definiert die Event-Handler für die Buttons.
     */
    public void initialize() {
        currentRoom = new Room("Labor"); // Initialisiert den aktuellen Raum
        player.setCurrentRoom(currentRoom); // Setzt den Raum für den Spieler
        //key = new Key("Sekretariat", "Schlüssel"); // Erstellt einen Schlüssel
        key = new Key("Computerraum", "Schlüssel"); // 
        currentRoom.setItem(key); // Fügt den Schlüssel dem Raum hinzu

        checkForKey(); // Überprüft, ob ein Schlüssel im Raum vorhanden ist

        // Event-Handler für Buttons
        pickUpButton.setOnAction(e -> pickUpItem());
       //backButton.setOnAction(e -> goToPreviousRoom());
        northButton.setOnAction(e -> showError("Norden"));
        southButton.setOnAction(e -> goToWestfluegel());
        westButton.setOnAction(e -> showError("Westen"));
        eastButton.setOnAction(e -> showError("Osten"));
     
    }

    /**
     * Überprüft, ob ein Schlüssel im Raum vorhanden ist, und zeigt eine Benachrichtigung an, wenn dies der Fall ist.
     */
    private void checkForKey() {
        if (currentRoom.getItemsString().contains("Schlüssel")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Schlüssel gefunden");
            alert.setHeaderText(null);
            alert.setContentText("Es gibt einen Schlüssel in diesem Raum.");
            alert.showAndWait();
        }
    }

    /**
     * Führt den Aufheben-Befehl aus, um den Schlüssel in das Inventar des Spielers zu legen,
     * und zeigt eine Benachrichtigung an.
     */
    @FXML
    private void pickUpItem() {
        if (!Inventory.getItems().contains("Schlüssel")) {
            
            Key labKey = new Key("Computerraum", "Schlüssel");
          
           
            player.addItem(labKey);
            
            Inventory.addItem("Schlüssel");
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Gegenstand aufgenommen");
            alert.setHeaderText(null);
            alert.setContentText("Du hast den Schlüssel aufgehoben!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Bereits aufgenommen");
            alert.setHeaderText(null);
            alert.setContentText("Du hast den Schlüssel bereits aufgenommen.");
            alert.showAndWait();
        }
    }


    /**
     * Wechselt zurück in den vorherigen Raum.
     * Diese Methode lädt die FXML-Datei für den Ostflügel und zeigt die entsprechende Szene an.
     */
    private void goToWestfluegel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Westfluegel.fxml"));
            Stage stage = (Stage) southButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    private void goToPreviousRoom() {
//    	try {
//    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Westfluegel.fxml"));
//    		Stage stage = (Stage) backButton.getScene().getWindow();
//    		Scene scene = new Scene(loader.load());
//    		stage.setScene(scene);
//    	} catch (IOException e) {
//    		e.printStackTrace();
//    	}
//    }
    
    /**
     * Zeigt eine Fehlermeldung an, wenn der Spieler versucht, in eine Richtung zu gehen, die nicht verfügbar ist.
     * 
     * @param direction Die Richtung, in die der Spieler gehen wollte.
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
    private void showInventory() {
        if (Inventory.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Inventar");
            alert.setHeaderText(null);
            alert.setContentText("Dein Inventar ist leer!");
            alert.showAndWait();
        } else {
            StringBuilder items = new StringBuilder("Du hast folgende Gegenstände im Inventar:\n");
            for (String item : Inventory.getItems()) {
                items.append("- ").append(item).append("\n");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Inventar");
            alert.setHeaderText(null);
            alert.setContentText(items.toString());
            alert.showAndWait();
        }
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