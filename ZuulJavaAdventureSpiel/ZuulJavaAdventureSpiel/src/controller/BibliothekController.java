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

import model.Book;
import model.Inventory;
import model.Player;
import model.Room;

/**
 * Diese Klasse steuert die Bibliotheks-Szene in der Anwendung.
 * Sie verwaltet die Aktionen des Spielers innerhalb des Raums "Bibliothek".
 */
public class BibliothekController {

    /**
     * Der Spieler, der durch das Spiel navigiert. 
     * Die Instanz wird aus dem BaseSceneController übernommen.
     */
    private static Player player = BaseSceneController.getPlayer();
    
    private Book book;
    private boolean bookCollected = false;
    /**
     * Der aktuelle Raum, in dem der Spieler sich befindet (Bibliothek).
     */
    private Room currentRoom;

    // FXML-Elemente, die in der Bibliotheks-Szene verwendet werden
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
     * Initialisiert die Bibliotheks-Szene.
     * Diese Methode wird automatisch nach dem Laden der FXML-Datei aufgerufen.
     * Sie setzt den aktuellen Raum auf "Bibliothek" und fügt Event-Handler für die Buttons hinzu.
     */
    public void initialize() {
        currentRoom = new Room("Bibliothek");
        player.setCurrentRoom(currentRoom);

        // Erstellt ein neues Buch und fügt es dem Raum hinzu
        book = new Book("Altes Buch", "Buch");
        currentRoom.setItem(book);

        checkForItems(); // Prüft, ob Gegenstände im Raum vorhanden sind

        // Event-Handler für Buttons
     //   backButton.setOnAction(e -> goToPreviousRoom());
        northButton.setOnAction(e -> showError("Norden"));
        southButton.setOnAction(e -> goToOstfluegel());
        westButton.setOnAction(e -> showError("Westen"));
        eastButton.setOnAction(e -> showError("Osten"));
        pickUpButton.setOnAction(e -> pickUpItem());
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
    
    private void pickUpItem() {
        if (!bookCollected && !Inventory.getItems().contains("Buch")) {
            player.addItem(book);
            Inventory.addItem("Buch");
            bookCollected = true;

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Gegenstand aufgenommen");
            alert.setHeaderText(null);
            alert.setContentText("Du hast das Buch aufgehoben!");
            alert.showAndWait();
        } else if (bookCollected || Inventory.getItems().contains("Buch")) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Bereits aufgenommen");
            alert.setHeaderText(null);
            alert.setContentText("Du hast das Buch bereits aufgenommen.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText(null);
            alert.setContentText("In diesem Raum liegt kein Gegenstand der aufgehoben werden kann.");
            alert.showAndWait();
        }
    }
    
    private void checkForItems() {
        if (currentRoom.getItemsString().contains("Buch")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Gegenstand gefunden");
            alert.setHeaderText(null);
            alert.setContentText("Du siehst ein altes Buch in diesem Raum.");
            alert.showAndWait();
        }
    }

    /**
     * Zeigt eine Fehlermeldung an, wenn kein Gegenstand im Raum aufhebbar ist.
     */
    private void showPickUpError() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(null);
        alert.setContentText("In diesem Raum liegt kein Gegenstand der aufgehoben werden kann.");
        alert.showAndWait();
    }

    /**
     * Wechselt zurück in den vorherigen Raum.
     * Diese Methode lädt die FXML-Datei für den Ostflügel und zeigt die entsprechende Szene an.
     */
//    private void goToPreviousRoom() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Ostfluegel.fxml"));
//            Stage stage = (Stage) backButton.getScene().getWindow();
//            Scene scene = new Scene(loader.load());
//            stage.setScene(scene);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    private void goToOstfluegel() {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Ostfluegel.fxml"));
    		Stage stage = (Stage) southButton.getScene().getWindow();
    		Scene scene = new Scene(loader.load());
    		stage.setScene(scene);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

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

