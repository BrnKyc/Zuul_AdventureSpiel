package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import model.Room;
import model.Inventory;
import model.Item;
import model.Key;


/**
* Controller-Klasse für die Steuerung der Sekretariats-Szene.
* Verwaltet die Benutzerinteraktionen und logischen Abläufe für den Raum "Sekretariat".
*/
public class SekretariatController {
	
	// Der Spieler, der die Anwendung benutzt.
    private static Player player = BaseSceneController.getPlayer();
    
    // Der aktuelle Raum des Spielers.
    private Room currentRoom;

    private boolean isDoorUnlocked = false;
    
    
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
        currentRoom = new Room("Sekretariat");
        player.setCurrentRoom(currentRoom);
        
       
        northButton.setOnAction(e -> showError("Norden"));
        southButton.setOnAction(e -> showError("Süden"));
        westButton.setOnAction(e -> goToOstfluegel());
        eastButton.setOnAction(e -> showError("Osten"));
        pickUpButton.setOnAction(e -> showPickUpError());
      //  backButton.setOnAction(e -> goToPreviousRoom())
        ;
        
        
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
     * Navigiert den Spieler in den vorherigen Raum.
     */
    private void goToOstfluegel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Ostfluegel.fxml"));
            Stage stage = (Stage) westButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    private void goToPreviousRoom() {
//    	try {
//    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Ostfluegel.fxml"));
//    		Stage stage = (Stage) backButton.getScene().getWindow();
//    		Scene scene = new Scene(loader.load());
//    		stage.setScene(scene);
//    	} catch (IOException e) {
//    		e.printStackTrace();
//    	}
//    }
    
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
