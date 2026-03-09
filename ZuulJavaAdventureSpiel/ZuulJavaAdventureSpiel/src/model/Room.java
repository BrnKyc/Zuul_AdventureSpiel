package model;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Die Klasse "Room" stellt einen Raum (Ort) in der Spielewelt dar.
 * Die Räume sind miteinander durch Türen verbunden.
 * In jedem Raum sind in der HashMap exits die Ausgänge in alle möglichen Richtungen
 * als Referenz auf den dann erreichten Zielraum gespeichert.
 * 
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> exits;    
    private HashMap<String, Item> items;
    private boolean locked;
    private Key schluessel;
    
    /**
     * Konstruktor der Klasse Raum.
     * Lege einen Raum mit einer Raumbeschreibung (description) an.
     * Die Raumbeschreibung ist so etwas wie
     * "der Fahrstuhl" oder "die Herrentoilette".
     * Der neu erzeugte Raum hat noch keine Ausgänge.
     * @param description Die Raumbeschreibung.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new HashMap<>();
        this.locked = false;
    }
    
    // Methode zum Hinzufügen eines Gegenstands
    public void setItem(Item item) {
        if (items.containsKey(item.getInformation())) {
            System.out.println("Der Gegenstand '" + item.getInformation() + "' ist bereits im Raum.");
        } else {
            items.put(item.getInformation(), item);
            System.out.println("Gegenstand hinzugefügt: " + item.getInformation());
        }
    }

    // Methode zum Entfernen von Gegenständen 
    public Item removeItem(String itemName) {
        return items.remove(itemName);
    }
    
    // Methode zum Anzeigen der Gegenstände im Raum
    public String getItemsString() {
        if (items.isEmpty()) {
            return "Es gibt hier keine Gegenstände.";
        } 
        StringBuilder itemList = new StringBuilder("Hier sind die Gegenstände: ");
        for (String itemName : items.keySet()) {
            itemList.append(itemName).append(" ");
        }
        return itemList.toString();
    }
    
    /**
     * Lege die Ausgänge des Raumes fest.
     * @param direction Bewegungsrichtung.
     * @param neighbor Der bei Bewegung in diese Richtung erreichte Zielraum.
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * Schließt einen Raum zu.
     */
    public void lockRoom(Key schluessel) {
        this.locked = true;
        this.schluessel = schluessel;
    }

    /**
     * Schließt einen Raum wieder auf.
     */
    public boolean unlockRoom(Key schluessel) {
        if (this.locked && this.schluessel != null && this.schluessel.equals(schluessel)) {
            this.locked = false;
            return true;
        }
        return false;
    }
    
    /**
     * Gib die Raumbeschreibung zurück, so wie sie im Konstruktor angegeben wurde.
     */
    public String getShortDescription() {
        return description;
    }

    /**
     * Gib eine längere Beschreibung des Raumes zurück. Beispiel-Format:
     *     Deine Position: die Herrentoilette.
     *     Ausgänge: Norden Westen.
     */
    public String getLongDescription() {
        return "Deine Position: " + description + ".\n" + getExitString() + "\n" + getItemsString();
    }
    
    /**
     * Gib einen String zurück, der die Ausgänge des Raumes beschreibt.
     * Beispiel:
     * Ausgänge: Norden Westen.
     */
    public String getExitString() {
        String returnString = "Ausgänge: ";
        Optional<Set<String>> optionalKeys = Optional.ofNullable(exits).map(Map::keySet);	 // alle Schlüssel als Menge
         
        returnString += optionalKeys
                .map(keys -> String.join(" ", keys))
                .orElse(""); 

        return returnString;
    }

    /**
     * Gibt zurück, ob ein Raum geschlossen ist.
     */
    public boolean isLocked() {
        return locked;
    }
    
    /**
     * Gib den Raum zurück, der bei Bewegung in eine Richtung erreicht wird
     * @param direction Bewegungsrichtung.
     * @return erreichter Zielraum, <null>, wenn es in die angegebene Richtung keinen Ausgang gibt.
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * Gib den Namen des Raumes zurück.
     */
    public String getRoomName() {
        return description;
    }
}