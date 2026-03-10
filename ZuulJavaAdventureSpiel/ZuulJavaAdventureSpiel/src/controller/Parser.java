package controller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.StringTokenizer;

/**
 * Dieser Parser liest die Eingaben des Spielers und versucht, sie als Kommando
 * für das Adventure-Spiel zu interpretieren.
 * Jedesmal, wenn er aufgerufen wird, liest er eine Eingabezeile und versucht,
 * diese Eingabe als Kommando, bestehend aus ein oder zwei Wörtern, zu
 *  interpretieren. Das Kommando wird dann als Objekt der Command-Klasse
 *  zurückgeliefert.
 * 
 * Der Parser greift auf eine Menge bekannter Kommandowörter zu.
 * Die Eingaben des Benutzers werden mit dieser Liste verglichen. Wenn die Eingabe
 * keinem gültigen Kommandowort entspricht, wird ein Objekt der Klasse
 * NullCommand zurückgeliefert.
 *
 */

public class Parser {

    private CommandWords commandwords;  // enthält alle erlaubten Kommandowörter

    public Parser() {
        commandwords = new CommandWords();
    }

    public Command getCommand() {
        String inputLine = "";   // In dieser Variablen wird die vom Spieler eingegebene Zeile stehen.
        Optional<String> word1 = Optional.empty();
        Optional<String> word2 = Optional.empty();

        System.out.print("> ");     // Zeichen ">" als Eingabeaufforderung ausgeben

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            inputLine = reader.readLine();
        }
        catch(java.io.IOException exc) {
            System.out.println("Fehler beim Lesen von: " + exc.getMessage());
        }

        StringTokenizer tokenizer = new StringTokenizer(inputLine);

        if (tokenizer.hasMoreTokens()) {
            word1 = Optional.of(tokenizer.nextToken());  // erstes eingegebenes Wort (Kommandowort)
        }

        if (tokenizer.hasMoreTokens()) {
            word2 = Optional.of(tokenizer.nextToken());  // zweites eingegebenes Wort
        }

        // Eventuell folgende Wörter werden ignoriert.

        Command command = commandwords.get(word1.orElse(null));  // Übergibt null, falls word1 leer ist
        word2.ifPresent(command::setSecondWord);  // Falls word2 vorhanden ist, wird es gesetzt
        return command;
    }

    /**
     * Gibt eine Liste aller gültigen Kommandowörter aus.
     */
    public void showCommands() {
        commandwords.showAll();
    }
}
