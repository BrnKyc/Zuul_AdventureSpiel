package model;

/**
 * Diese Klasse Book erbt von der Oberklasse Item 
 * und repräsentiert ein Buch, das in der Bibliothek
 * gefunden werden kann.
 */
public class Book extends Item {
    private String title;

    /**
     * Konstruktor für die Klasse Book.
     * Initialisiert ein Buch mit einem Titel und zusätzlichen Informationen.
     *
     * @param title der Titel des Buches
     * @param information zusätzliche Details oder Beschreibungen des Buches
     */
    public Book(String title, String information) {
        super(information);
        this.title = title;
    }

    /**
     * Gibt den Titel des Buches zurück.
     */
    public String getTitle() {
        return title;
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