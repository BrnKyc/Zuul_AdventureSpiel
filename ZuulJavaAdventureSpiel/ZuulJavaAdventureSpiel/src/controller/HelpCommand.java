package controller;

import java.util.Optional;

import model.Player;

/**
 * Implementierung des Kommandos "hilfe"
 * 
 */
public class HelpCommand extends Command {
    private CommandWords commandWords;

    /**
     * Konstruktor  
     * 
     * @param words eine Instanz der Klasse CommandWords.
     */
    public HelpCommand(CommandWords words) {
        commandWords = words; // Dadurch weiß die HelpCommand-Klasse, welche Kommandos es gibt.
    }

    /**
     * Gib einen kurzen Hilfetext und alle gültigen Kommandoworte aus.
     * Gibt außerdem, wenn vorhanden, genauere Informationen zum Befehl im 2. Wort.
     * 
     * @return immer false.
     */
    @Override
    public boolean execute(Player player) {
        Optional<String> optionalSecondWord = getSecondWord();

        if (optionalSecondWord.isPresent()) {
            String secondWord = optionalSecondWord.get();
            Optional<Command> optionalCommand = Optional.ofNullable(commandWords.get(secondWord));

            if (optionalCommand.isEmpty() || optionalCommand.get() instanceof NullCommand) {
                System.out.println("Unbekannter Befehl: '" + secondWord + "'.");
            } else {
                System.out.println("Befehl: " + secondWord + " = " + optionalCommand.get().commandInfo());
            }
        } else {
            System.out.println("Du bist alleine und verloren und lungerst");
            System.out.println("in der Hochschule rum.");
            System.out.println();
            System.out.println("Mögliche Kommandos sind:");
            commandWords.showAll();
            System.out.println("Durch Aufruf des Befehls 'hilfe' und eines anderen Befehls als zweites Wort");
            System.out.println("werden Informationen zu dem Befehl aufgerufen.");
        }

        return false;
    }

    /**
     * Gibt die Information, was der Befehl "hilfe" macht. 
     */
    @Override
    public String commandInfo() {
        return "Mit dem Command 'hilfe' werden Informationen zu den einsetzbaren Befehlen"
                + "\n gegeben und es wird bei Aufruf angegeben, was die Befehle machen.";
    }
}
