package controller;

import java.util.Optional;

import model.Player;

/**
 * 
 * Diese Klasse ist eine abstrakte Oberklasse für alle Kommandoklassen des Spiels.
 * Jedes Kommando, das der Spieler eingeben kann, wird als Unterklasse hiervon implementiert.
 * 
 * Objekte der Klasse Command können ein optionales Objektwort (Attribut secondWord) enthalten,
 * wenn bei der Eingabe in der Kommandozeile ein zweites Wort eingegeben wurde.
 * Besteht das Kommando aus nur einem Wort, ist das Objektwort <null>.
 * 
 * 
 */

public abstract class Command
{
    private Optional<String> secondWord;

    /**
     * Konstruktor: Erzeuge ein Command-Objekt.
     */
    public Command()
    {
        secondWord = Optional.empty();
    }

    /**
     * Liefert das zweite Wort des Kommandos (Optional.empty(), wenn kein zweites Wort eingegeben wurde).
     * @return Ein Optional, das das zweite Wort des Kommandos enthält (oder Optional.empty(), wenn kein zweites Wort eingegeben wurde).
     */
    public Optional<String> getSecondWord()
    {
        return secondWord;
    }

    /**
     * @return true wenn das Kommando ein zweites Wort hat.
     */
    public boolean hasSecondWord()
    {
        return secondWord.isPresent();
    }

    /**
     * Setze das zweite Wort des Kommandos (Attribut secondWord)
     * Ein Optional.empty() zeigt an, dass kein zweites Wort eingegeben wurde.
     */
    public void setSecondWord(String secondWord)
    {
        this.secondWord = Optional.ofNullable(secondWord);
    }

    /**
     * Führe das Kommando aus. Liefert als Ergebnis einen booleschen Wert, der aussagt,
     * ob das Spiel beendet werden soll.
     * 
     * @return True, wenn das Spiel beendet werden soll, andernfalls false.
     */
    public abstract boolean execute(Player player);
    
    public abstract String commandInfo();
}
