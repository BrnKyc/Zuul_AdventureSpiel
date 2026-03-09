package controller;

import model.Item;
import model.Player;

/**
 * Der Befehl ShowInventoryCommand zeigt das Inventar des Spielers an.
 * Falls das Inventar leer ist, wird eine entsprechende Nachricht angezeigt.
 */
public class ShowInventoryCommand extends Command {

    public ShowInventoryCommand() {
    }
    /**
     * Gibt spezifische Informationen zum Befehl.
     */
    @Override
    public String commandInfo() {
        return "Mit 'zeige' wird das aktuelle Inventar angezeigt.";
    }
    /**
     * Beschreibt, was der Befehl bei Ausführung macht.
     */
    @Override
    public boolean execute(Player player) {
        if (player.getInventory().isEmpty()) {
            System.out.println("Dein Inventar ist leer.");
        } else {
            System.out.println("In deinem Inventar befinden sich folgende Gegenstände:");
            for (Item item : player.getInventory().values()) {
                System.out.println("- " + item.getInformation());
            }
        }
        return false; // Spiel soll weiterlaufen
    }
}
