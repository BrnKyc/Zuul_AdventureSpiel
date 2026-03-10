package controller;

import model.Player;

/**
 * Ein Objekt der Klasse NullCommand wird erzeugt, wenn ein ungültiges
 * Kommando eingegeben wurde.
 * Das NullCommand tut bei seiner Ausführung nichts weiter, als eine Fehlermeldung auszugeben. 
 * 
 */
public class NullCommand extends Command
{
    
    /**
     * Konstruktor.
     */
    public NullCommand()
    {
        
    }
    
    /**
     * Gibt eine Fehlermeldung aus.
     * @return immer false.
     */
    public boolean execute(Player player)
    {
        System.out.println("Ich verstehe nicht, was Du meinst... Gib \"hilfe\" ein, um eine Liste der gültigen Kommandos zu sehen.");
        return false;
    }
    @Override
	public String commandInfo() {
		return "";
	}
}
