package model;

/**
 * Diese Klasse Key erbt von der Oberklasse Item 
 * und ist daher ein Gegenstand mit einer 
 * eigenen individuellen Aufgabe. Man kann mithilfe eines individuellen Schlüssels die dazugehoerige Tuer aufschließen. 
 */
public class Key extends Item {
    private String id;

   
    /**
     * Konstruktor für die Klasse Key.
     * Initialisiert einen Schlüssel mit einer eindeutigen ID und zusätzlichen Informationen.
     *
     * @param id die eindeutige Kennung des Schlüssels 
     * @param information zusätzliche Details oder Beschreibungen des Schlüssels
     */
    public Key(String id, String information) {
        super(information);
        this.id = id;
    }

    /**
     * Gibt die benutzerdefinierte ID des Schlüssels an.
     */
    public String getId() {
        return id;
    }

    /**
     * Überschreibt die Methode der Oberklasse Item.
     * Gibt Informationen über den Schluessel an.
     */
    @Override
    public String getInformation() {
        return super.getInformation();
    }

    /**
     * Gibt den Namen des Raumes zurück, für den der Schlüssel bestimmt ist.
     */
    public String getRoomName() {
        return id;
    }
}
