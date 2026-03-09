package controller;

import model.Player;

/**
 * Implementierung des Kommandos "ende"
 * 
 * @author Michael Kolling
 */
public class QuitCommand extends Command
{
    /**
     * Konstruktor.
     */
    public QuitCommand()
    {
    }

    /**
     * "ende" wurde eingegeben.
      * @return immer true (dadurch wird das Spiel beendet).
     * 
     */
    public boolean execute(Player player)
    {
    	return true;
    }
    /**
     * Gibt die Information, was der Befehl "ende" macht. 
     */
	@Override
	public String commandInfo() {
		return "Der Befehl 'ende' beendet das Spiel wobei der Speicherstand nicht geladen wird.";
	}

}