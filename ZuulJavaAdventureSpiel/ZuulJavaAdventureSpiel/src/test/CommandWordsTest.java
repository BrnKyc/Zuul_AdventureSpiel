package test;
import org.junit.Before;

import org.junit.Test;

import controller.Command;
import controller.CommandWords;
import controller.GoCommand;
import controller.HelpCommand;
import controller.NullCommand;
import controller.QuitCommand;
import java.util.Optional;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CommandWordsTest {

    private CommandWords commandWords;

    @Before
    public void setUp() {
        commandWords = new CommandWords(); // Initialisiere die CommandWords-Instanz
    }

    @Test
    public void testGetValidCommand() {
        // Teste, ob ein gültiges Kommando das richtige Command-Objekt zurückgibt
        Command command = commandWords.get("gehe");
        assertTrue(command instanceof GoCommand);

        command = commandWords.get("hilfe");
        assertTrue(command instanceof HelpCommand);

        command = commandWords.get("ende");
        assertTrue(command instanceof QuitCommand);
    }

    @Test
    public void testGetInvalidCommand() {
        // Teste, ob ein unbekanntes Kommando ein NullCommand-Objekt zurückgibt
    	Optional<Command> command = Optional.ofNullable(commandWords.get("unbekannt"));
        assertTrue(command.isPresent());
        assertTrue(command.get() instanceof NullCommand);
    }
}
