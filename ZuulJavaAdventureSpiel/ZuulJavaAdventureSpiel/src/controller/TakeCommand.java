package controller;

import java.util.Optional;

import model.Item;
import model.Player;
import model.Room;

/**
 * Die Klasse TakeCommand repräsentiert den Befehl, mit dem der Spieler 
 * einen Gegenstand aus dem aktuellen Raum aufnehmen kann. 
 * 
 * Dieser Befehl überprüft, ob ein Gegenstand mit dem angegebenen Namen 
 * im aktuellen Raum vorhanden ist und entfernt ihn dann aus dem Raum, 
 * sodass der Spieler ihn jetzt besitzt.
 */
public class TakeCommand extends Command {

    // Konstruktor
    public TakeCommand() {
        super();
    }

    /**
     * Gibt eine Beschreibung des Befehls zurück.
     */
    @Override
    public String commandInfo() {
        return "Mit 'nimm <Gegenstand>' nehmen Sie einen Gegenstand auf.";
    }

    /**
     * Führt den Befehl aus, um einen Gegenstand aufzunehmen.
     * 
     * Überprüft, ob ein zweites Wort (der Name des Gegenstands) vorhanden 
     * ist und ob dieser Gegenstand im aktuellen Raum vorhanden ist.
     */
    @Override
    public boolean execute(Player player) {
        Optional<String> optionalItemName = getSecondWord();

        if (optionalItemName.isPresent()) {
            String itemName = optionalItemName.get();
            Room currentRoom = player.getCurrentRoom();
            Optional<Item> optionalItem = Optional.ofNullable(currentRoom.removeItem(itemName));

            if (optionalItem.isPresent()) {
                Item item = optionalItem.get();
                player.addItem(item);
                System.out.println("Du hast " + item.getInformation() + " genommen.");
            } else {
                System.out.println("Es gibt hier keinen solchen Gegenstand.");
            }
        } else {
            System.out.println("Was möchtest du nehmen?");
        }
        return false;
    }
}
