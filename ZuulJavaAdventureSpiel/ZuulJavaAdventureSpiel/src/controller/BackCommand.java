package controller;

import model.Player;

/**
 * Die Klasse BackCommand die beschreibt, was bei Auswahl des Befehls "zurück"
 * ausgeführt wird.
 * 
 * @version 1.0 (Oktober 2024)
 */
public class BackCommand extends Command

{
/**
 * Konstruktor
 */
	public BackCommand()
	{
	}
/*
 * Überschreibt die Methode "commandInfo" mit Informationen zum "zurück"-Befehl.
 */
@Override
	public String commandInfo() {
	
		return "Mit 'zurück' kehren Sie in den vorherigen Raum zurück."
				+ "\nWenn Sie am Anfang sind, können Sie nicht weiter zurückkehren.";
}
/**
 * Beschreibt die Durchführung des "zurück"-Befehls.
 */
	public boolean execute(Player player) {
	
    	if (player.canGoBack()) {
    		player.goBack();
    		System.out.println("Du bist zurückgegangen.");
    } else {
        System.out.println("Du kannst nicht weiter zurückgehen. Du bist bereits im Anfangsraum.");
    }
    return false;
}
}
