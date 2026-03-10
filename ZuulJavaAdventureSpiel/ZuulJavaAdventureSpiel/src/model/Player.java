package model;

import java.util.HashMap;
import java.util.Optional;
import java.util.Stack;

/**
 * Diese Klasse steht für einen Spieler im Spiel.
 * Ein Spieler befindet sich zu jedem Zeitpunkt des Spiels in einem bestimmten Raum.
 *
 */
public class Player {
    private Stack<Room> roomHistory;
    private Room currentRoom;
    private Room previousRoom;

    private HashMap<String, Item> inventory;

    /**
     * Konstruktor für die Player-Klasse.
     *  
     * Initialisiert die Raumhistorie, den aktuellen Raum und ein Inventar.
     */
     
    public Player() {
        roomHistory = new Stack<>();
        currentRoom = null; 
        this.inventory = new HashMap<>();
    }

    /**
     * Liefert den aktuellen Raum des Spielers zurück.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Setzt den aktuellen Raum des Spielers.
     */
    public void setCurrentRoom(Room room) {
        currentRoom = room;
    }

    /*
     * Setzt den aktuellen Raum vor dem Wechsel zum 'letzten' Raum
     */
    public void setPreviousRoom(Room room) {
        previousRoom = room;
    }

    public boolean canGoBack() {
        return !roomHistory.isEmpty();
    }

    /*
     * Fuegt einen Gegenstand dem Inventar hinzu
     */
    public void addItem(Item item) {
        inventory.put(item.getInformation(), item);
    }

    /*
     * Entfernt einen Gegenstand.
     */
    public Item removeItem(String itemName) {
        return inventory.remove(itemName);
    }

    /*
     * Gibt das Inventar an
     */
    public Item getItem(String itemName) {
        return inventory.get(itemName);
    }

    /*
     * Sucht im Inventar nach einem Gegenstand
     */
    public boolean hasItem(String itemName) {
        return inventory.containsKey(itemName);
    }

    /*
     * Zeigt das Inventar
     */
    public HashMap<String, Item> getInventory() {
        return inventory;
    }

    /*
     * Loescht alle Inhalte des Inventars.
     */
    public void clearInventory() {
        inventory.clear();
    }

    /*
     * Benutzt einen Schlüssel um einen Raum zu oeffnen.
     */
    public boolean useSchluessel(Room room) {
        for (Item item : inventory.values()) {
            if (item instanceof Key && room.unlockRoom((Key) item)) {
                System.out.println("Die Tür wurde erfolgreich aufgeschlossen.");
                return true;
            }
        }
        System.out.println("Du hast keinen passenden Schlüssel.");
        return false;
    }

    /**
     * Versucht, in eine bestimmte Richtung zu laufen.
     * Wenn dort eine Tür ist, wird sich dadurch der aktuelle Raum des Spielers ändern.
     */
    public void walk(String direction) {
        if (currentRoom == null) {
            System.out.println("Der aktuelle Raum ist nicht gesetzt. Du kannst dich nicht bewegen!");
            return;
        }

        Optional<Room> optionalNextRoom = Optional.ofNullable(currentRoom.getExit(direction)); // `getExit` gibt Optional<Room> zurück
        optionalNextRoom.ifPresentOrElse(
            nextRoom -> {
                if (nextRoom.isLocked()) {
                    System.out.println("Die Tür ist verschlossen!");
                    if (!useSchluessel(nextRoom)) {
                        System.out.println("Du kannst nicht hindurchgehen.");
                    }
                } else {
                    roomHistory.push(currentRoom);
                    setCurrentRoom(nextRoom);
                    System.out.println(nextRoom.getLongDescription());
                }
            },
            () -> System.out.println("Da ist keine Tür!")
        );
    }

    /**
     * Kehrt zum vorherigen Raum zurück.
     */
    public void goBack() {
        if (!roomHistory.isEmpty()) {
            currentRoom = roomHistory.pop();
            System.out.println(currentRoom != null ? currentRoom.getLongDescription() : "Unbekannter Raum");
        } else {
            System.out.println("Du kannst nicht weiter zurückgehen. Du bist bereits im Anfangsraum.");
        }
    }

    /**
     * Gibt den derzeitigen Raum an.
     */
    public String getCurrentRoomName() {
        return currentRoom != null ? currentRoom.getRoomName() : "Unbekannter Raum";
    }
}
