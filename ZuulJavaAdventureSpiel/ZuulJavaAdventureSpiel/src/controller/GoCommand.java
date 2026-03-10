package controller;

import java.util.Optional;

import model.Player;

/**
 * Implementierung des Kommandos "gehe"
 * 
 * @version 1.0 (December 2002)
 */
public class GoCommand extends Command
{
    /**
     * Konstruktor.
     */
    public GoCommand()
    {
    }

    /** 
     * 
     * Versuche, in eine Richtung zu laufen. Wenn es dort einen Ausgang
     * gibt, betritt den betreffenden Raum. Andernfalls wird eine Fehlermeldung
     * ausgegeben.
     * 
     * @return immer false.
     */
   
    /**
     * Gibt die Information, was der Befehl "gehe" macht.
     */
    @Override
    public String commandInfo() {
    	return "Mit 'gehe' geben Sie an, wo sie als Nächstes hingehen wollen"
    	+ "\n Sollte in der Richtung, die Sie nach dem gehe angeben, kein Raum sein"
    	+ "\n wird eine Fehlermeldung ausgegeben.";
    	
    }
    /*
     * Beschreibt die Durchführung es "gehe"-Befehls.
     */
    public boolean execute(Player player)
    {
        if(hasSecondWord()) {
            Optional<String> direction = getSecondWord();
            player.walk(direction.orElse(""));
        }
        else {
        	 // Wenn kein zweites Wort eingegeben wurde, haben wir keine Ahnung, wohin der Spieler gehen will:
            System.out.println("WOHIN gehen?");
        }
        return false;
    }
}
