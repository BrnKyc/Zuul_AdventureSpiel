package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Inventory;
import model.Key;
import model.Knife;
import model.Player;
import model.Room;


/**
* Controller-Klasse für die Steuerung der Computerraum-Szene.
* Verwaltet die Benutzerinteraktionen und logischen Abläufe für den "Computerraum".
*/
public class ComputerraumController {

	
	// Der Spieler, der die Anwendung benutzt.
    private static Player player = BaseSceneController.getPlayer();
    
    // Der aktuelle Raum des Spielers.
    private Room currentRoom;
    
    // Ein Schlüssel für die Tür des Computerraum
    private Key key;
    
    // Status, ob die Tür zum Computerraum aufgeschlossen ist. 
    private boolean isDoorUnlocked = false;
    
    private Knife knife;
    
    private Knife messer;
    
    private boolean knifeCollected = false;
    
    @FXML
    private Button northButton;
    @FXML
    private Button southButton;
    @FXML
    private Button eastButton;
    @FXML
    private Button westButton;
//    @FXML
//    private Button backButton;
    @FXML
    private Button openDoorButton;
    @FXML
    private Button showInventoryButton;
    @FXML
    private Button pickUpButton;
    @FXML
    private Button dropItemButton;
    
    /**
     * Initialisiert die Szene und setzt den Spieler in den aktuellen Raum.
     * Konfiguriert die Button-Aktionen für die Bewegung und Interaktionen im Raum.
     */
    public void initialize() {
        currentRoom = new Room("Computerraum");
        player.setCurrentRoom(currentRoom);
        
       
        northButton.setOnAction(e -> showError("Norden"));
        southButton.setOnAction(e -> showError("Süden"));
        westButton.setOnAction(e -> showError("Westen"));
        eastButton.setOnAction(e -> goToWestfluegel());
        pickUpButton.setOnAction(e -> pickUpItem());
        //backButton.setOnAction(e -> goToPreviousRoom());
        
     // Messer erstellen und im Raum platzieren
        messer = new Knife("Zeigt Fingerabdruecke von Maria", "Messer");
        currentRoom.setItem(messer);

        // Weitere Initialisierungen
        checkForKnife();

        if (isDoorUnlocked || Inventory.getItems().contains("Schlüssel")) {
            isDoorUnlocked = true;
            greetPlayer();
        }
    }

    /**
     * Führt den Aufheben-Befehl aus, um den Schlüssel in das Inventar des Spielers zu legen,
     * und zeigt eine Benachrichtigung an.
     */
    
    private void pickUpItem() {
        if (!knifeCollected && !Inventory.getItems().contains("Messer")) {
            // Messer ins Inventar hinzufügen
            player.addItem(messer); // Klassenvariable 'messer' verwenden
            Inventory.addItem("Messer");
            knifeCollected = true; // Status setzen, dass das Messer aufgenommen wurde

            // Erfolgsmeldung anzeigen
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Gegenstand aufgenommen");
            alert.setHeaderText(null);
            alert.setContentText("Du hast das Messer aufgehoben! Man erkennt Fingerabdrücke, die von Maria stammen!");
            alert.showAndWait();
        } else if (knifeCollected || Inventory.getItems().contains("Messer")) {
            // Warnung anzeigen, wenn das Messer bereits aufgenommen wurde
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Bereits aufgenommen");
            alert.setHeaderText(null);
            alert.setContentText("Du hast das Messer bereits aufgenommen.");
            alert.showAndWait();
        } else {
            // Fehler anzeigen, wenn kein Messer im Raum ist
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText(null);
            alert.setContentText("In diesem Raum liegt kein Gegenstand der aufgehoben werden kann.");
            alert.showAndWait();
        }
    }


    /**
     * Überprüft, ob der Spieler Zugang zum Raum hat.
     * Falls der Spieler den passenden Schlüssel besitzt, wird der Raum betreten.
     * Andernfalls wird eine Warnung angezeigt.
     */
    private void checkRoomAccess() {
        if (Inventory.getItems().contains("Schlüssel")) {
            isDoorUnlocked = true;
            player.setCurrentRoom(currentRoom);
        } else {
            showLockedRoomAlert();
        }
    }
    
    /**
     * Zeigt eine Fehlermeldung an, wenn kein Gegenstand im Raum vorhanden ist, der aufgehoben werden kann.
     */
    private void showPickUpError() {
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setTitle("Fehler");
    	alert.setHeaderText(null);
    	alert.setContentText("In diesem Raum liegt kein Gegenstand der aufgehoben werden kann.");
        alert.showAndWait();
    }
    
    /**
     * Überprüft, ob der Spieler einen Schlüssel für den aktuellen Raum besitzt.
     * @return true, wenn der Spieler den passenden Schlüssel besitzt, andernfalls false.
     */
    private boolean playerHasKey() {
        return player.getInventory().values().stream()
                .anyMatch(item -> item instanceof Key && ((Key) item).getRoomName().equals(currentRoom.getRoomName()));
    }

    /**
     * Zeigt eine Warnung an, dass der Raum gesperrt ist und ein Schlüssel benötigt wird.
     */
    private void showLockedRoomAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Raum gesperrt");
        alert.setHeaderText(null);
        alert.setContentText("Dieser Raum ist gesperrt. Du benötigst einen Schlüssel, um ihn zu öffnen.");
        alert.showAndWait().ifPresent(response -> goToWestfluegel());
    }


    /**
     * Überprüft, ob ein Schlüssel im aktuellen Raum vorhanden ist, und informiert den Spieler.
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
    
    private void checkForKnife() {
        if (currentRoom.getItemsString().contains("Messer")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Messer gefunden");
            alert.setHeaderText(null);
            alert.setContentText("Der Mörder hat die Mordwaffe in diesem Raum versteckt.");
            alert.showAndWait();
        }
    }
    
    /**
     * Öffnet die Tür zum , falls der Spieler den passenden Schlüssel besitzt.
     * Zeigt ansonsten eine Fehlermeldung an.
     */
    @FXML
    private void openDoor() {
       
        if (Inventory.getItems().contains("Schlüssel")) {
            isDoorUnlocked = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tür geöffnet");
            alert.setHeaderText(null);
            alert.setContentText("Du hast die Tür erfolgreich mit dem Schlüssel geöffnet!");
            alert.showAndWait();
            greetPlayer();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Tür abgeschlossen");
            alert.setHeaderText(null);
            alert.setContentText("Du hast keinen passenden Schlüssel, um das Computerraum zu betreten!");
            alert.showAndWait();
        }
    }

    
    /**
     * Zeigt eine Warnung an, dass der Spieler keinen passenden Schlüssel besitzt.
     */
    private void showKeyMissingAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Schlüssel fehlt");
        alert.setHeaderText(null);
        alert.setContentText("Du hast keinen passenden Schlüssel, um diesen Raum aufzuschließen.");
        alert.showAndWait();
    }

    /**
     * Begrüßt den Spieler, wenn er das Computerraum erfolgreich betreten hat.
     */
    private void greetPlayer() {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Willkommen");
        alert.setHeaderText(null);
        alert.setContentText("Willkommen im Computerraum! Schön, dass du den Schlüssel gefunden hast.");
    }
    
    
    
//    private void goToPreviousRoom() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Westfluegel.fxml"));
//            Stage stage = (Stage) backButton.getScene().getWindow();
//            Scene scene = new Scene(loader.load());
//            stage.setScene(scene);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    
    /**
     * Navigiert den Spieler in den Osten.
     */
    private void goToWestfluegel() {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Westfluegel.fxml"));
    		Stage stage = (Stage) eastButton.getScene().getWindow();
    		Scene scene = new Scene(loader.load());
    		stage.setScene(scene);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Zeigt eine Fehlermeldung an, wenn der Spieler versucht, in eine unzulässige Richtung zu gehen.
     * @param direction Die unzulässige Richtung.
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
	
	
	
	

