package model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private static List<String> items = new ArrayList<>();

    // Methode zum Abrufen des Inventars
    public static List<String> getItems() {
        return items;
    }

    // Methode zum Hinzufügen von Items
    public static void addItem(String item) {
        items.add(item);
    }

    // Methode zum Entfernen von Items
    public static void removeItem(String item) {
        items.remove(item);
    }

    // Methode zum Leeren des Inventars
    public static void clearInventory() {
        items.clear();
    }
}
