package model;

/**
 * Diese Klasse Knife erbt von der Oberklasse Item 
 * und repräsentiert die Mordwaffe.
 */
public class Knife extends Item {
    private String name;

    /**
     * Konstruktor für die Klasse Knife.
     * Initialisiert ein Buch mit einem Titel und zusätzlichen Informationen.
     *
     * @param name der Name des Messers
     * @param information zusätzliche Details oder Beschreibungen des Messers
     */
    public Knife(String name, String information) {
        super(information);
        this.name = name;
    }

    /**
     * Gibt den Titel des Buches zurück.
     */
    public String getName() {
        return name;
    }

    /**
     * Überschreibt die Methode der Oberklasse Item.
     * Gibt Informationen über das Buch an.
     */
    @Override
    public String getInformation() {
        return super.getInformation();
    }
}