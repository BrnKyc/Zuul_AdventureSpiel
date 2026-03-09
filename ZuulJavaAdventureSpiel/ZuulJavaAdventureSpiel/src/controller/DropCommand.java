package controller;

import java.util.Optional;

import model.Item;
import model.Player;

public class DropCommand extends Command {

    public DropCommand() {
        super();
    }

    /**
     * Beschreibt den Befehl 'ablegen' genauer.
     */
    @Override
    public String commandInfo() {
        return "Mit 'ablegen <Gegenstand>' kannst du einen Gegenstand ablegen.";
    }

    /**
     * Beschreibt, was passiert, wenn ein Spieler einen Gegenstand wegwirft.
     */
    @Override
    public boolean execute(Player player) {
        Optional<String> optionalItemName = getSecondWord();

        if (optionalItemName.isPresent()) {
            String itemName = optionalItemName.get();
            Item item = player.removeItem(itemName); // Direkter Rückgabewert

            if (item != null) { // Überprüfen, ob ein Item entfernt wurde
                player.getCurrentRoom().setItem(item);
                System.out.println("Du hast " + item.getInformation() + " abgelegt.");
            } else {
                System.out.println("Du hast diesen Gegenstand nicht im Inventar.");
            }
        } else {
            System.out.println("Welchen Gegenstand möchtest du ablegen?");
        }

        return false;
    }

}