package controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

/**
 * Diese Klasse enthält alle im Spiel bekannten Kommandowörter.
 * Sie wird genutzt, um eingegebene Kommandos zu erkennen.
 *
 */

public class CommandWords
{
    private HashMap<String, Command> commands;

    /**
     * Konstruktor - Kommandowörter initialisieren.
     */
    public CommandWords()
    {
        commands = new HashMap<String, Command>();
        commands.put("gehe", new GoCommand());
        commands.put("hilfe", new HelpCommand(this));
        commands.put("ende", new QuitCommand());
        commands.put("zurück", new BackCommand());
        commands.put("nimm", new TakeCommand());
        commands.put("ablegen", new DropCommand());
        commands.put("zeige", new ShowInventoryCommand());
    }

    /**
     * Liefere für ein gegebenes Kommandowort das dazugehörige Command-Objekt
     */
    public Command get(String word)
    {
    	// Wenn word kein gültiges Kommandowort ist, wird in der HashMap commands nichts gefunden.
    	// In diesem Falle liefere ein NullCommand-Objekt zurück.
    	

    	// Wenn word kein gültiges Kommandowort ist, wird in der HashMap commands nichts gefunden.
    	Optional<Command> command = Optional.ofNullable(commands.get(word));

    	return command.orElse(new NullCommand());

    }

    /**
     * Gib eine Liste aller existierenden Kommandos auf System.out aus.
     */
    public void showAll() 
    {
        for(Iterator i = commands.keySet().iterator(); i.hasNext(); ) {
            System.out.print(i.next() + "  ");
        }
        System.out.println();
    }
}
