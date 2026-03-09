package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

import model.Inventory;
import model.Player;
import model.Room;

/**
 * Diese Klasse steuert die Basisszene der Anwendung und ist für die Interaktion zwischen 
 * der Benutzeroberfläche (JavaFX) und dem Spielmodell verantwortlich.
 */
public class BaseSceneController {

    /**
     * Der Spieler, der im Spiel agiert. Beinhaltet den aktuellen Raum und das Inventar.
     */
    private static Player player = new Player();

    // FXML-Komponenten, die mit der Basisszene verbunden sind
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
     * Initialisiert die Szene, erstellt die Räume und fügt Event-Handler für die Buttons hinzu.
     * Diese Methode wird automatisch von JavaFX aufgerufen, nachdem die FXML-Datei geladen wurde.
     */
    public void initialize() {
        player = new Player();  // Initialisiert den Spieler
        createRooms();  // Erstellt und verbindet die Räume

        // Event-Handler für Bewegungsbuttons
        northButton.setOnAction(e -> showError("Norden"));
        southButton.setOnAction(e -> showError("Süden"));
        westButton.setOnAction(e -> goToWestfluegel());
        eastButton.setOnAction(e -> goToOstfluegel());
        
        // Event-Handler für Zurück- und Aufheben-Buttons
//        backButton.setOnAction(e -> showBackError());
        pickUpButton.setOnAction(e -> showPickUpError());
        showSolveCasePopUp();
    }

    /**
     * Zeigt eine Fehlermeldung an, wenn der Spieler nicht in eine bestimmte Richtung gehen kann.
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
    
    /**
     * Zeigt eine Fehlermeldung an, wenn der Spieler nicht zurückgehen kann.
     */
//    private void showBackError() {
//        Alert alert = new Alert(AlertType.ERROR);
//        alert.setTitle("Fehler");
//        alert.setHeaderText(null);
//        alert.setContentText("Du kannst nicht zurück gehen.");
//        alert.showAndWait();
//    }
    
    /**
     * Zeigt eine Fehlermeldung an, wenn es keinen Gegenstand gibt, den der Spieler aufheben kann.
     */
    private void showPickUpError() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(null);
        alert.setContentText("In diesem Raum liegt kein Gegenstand der aufgehoben werden kann.");
        alert.showAndWait();
    }

    /**
     * Lädt die Ostflügel-Szene, wenn der Spieler nach Osten geht.
     * Diese Methode verwendet den FXMLLoader, um die Ostflügel-Ansicht anzuzeigen.
     */
    private void goToOstfluegel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Ostfluegel.fxml"));
            Stage stage = (Stage) eastButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void goToWestfluegel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Westfluegel.fxml"));
            Stage stage = (Stage) westButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Bewegt den Spieler in den vorherigen Raum, falls möglich.
     * Gibt eine Fehlermeldung aus, wenn der Spieler sich nicht zurückbewegen kann.
     */
//    private void movePlayerBack() {
//        if (player.canGoBack()) {
//            player.goBack();
//            System.out.println("Du bist zurückgegangen.");
//        } else {
//            System.out.println("Du kannst nicht weiter zurückgehen. Du bist bereits im Anfangsraum.");
//        }
//    }

    /**
     * Erstellt die Räume und legt die Verbindungen zwischen ihnen fest.
     * Der Spieler wird initial in den ersten Raum gesetzt.
     */
    private void createRooms() {
        // Beispielräume
        Room draussen = new Room("vor dem Haupteingang der Hochschule");
        Room hoersaal = new Room("in einem Hörsaal");

        // Verbindungen zwischen Räumen
        draussen.setExit("norden", hoersaal);

        // Setzt den Spieler in den Startraum
        player.setCurrentRoom(draussen);
    }

    /**
     * Gibt die aktuelle Instanz des Spielers zurück.
     * 
     * @return Die Spielerinstanz.
     */
    public static Player getPlayer() {
        return player;
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
    private void showSolveCasePopUp() {
        if (Inventory.getItems().contains("Messer")) {
            // Zeige ein Bestätigungsdialogfenster
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Fall lösem");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Du hast die Mordwaffe mit den Fingerabdruecken. Möchtest du den Fall loesen?");

            // Warte auf die Benutzeraktion (OK oder Abbrechen)
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {

                	solveCase();
                } else {
                    // Falls der Benutzer "Abbrechen" wählt, mache nichts
                    Alert cancelledAlert = new Alert(Alert.AlertType.INFORMATION);
                    cancelledAlert.setTitle("Aktion abgebrochen");
                    cancelledAlert.setHeaderText(null);
                    cancelledAlert.setContentText("Du hast entschieden, den Fall noch nicht zu loesen.");
                    cancelledAlert.showAndWait();
                }
            });}
        }
            
        
    private void solveCase() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Fall gelöst");
        alert.setHeaderText(null);
        alert.setContentText("Du hast den Fall erfolgreich gelöst! Gratulation!");
        alert.showAndWait();
        Platform.exit();

}
}