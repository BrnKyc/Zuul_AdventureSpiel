package test;

import org.junit.*;

import model.Item;
import model.Player;
import model.Room;
import controller.TakeCommand;
import controller.DropCommand;
import model.Key;
import controller.ShowInventoryCommand;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Testklasse für die TakeCommand- und DropCommand-Kommandos.
 * Überprüft sowohl gültige als auch ungültige Eingaben.
 */
public class ItemCommandTest {

    private Player player;
    private Room room;
    private Item book;
    private Key key;
    private Item schwert;
    private Item schild;
    private ShowInventoryCommand showInventoryCommand;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    
    /**
     * Setzt die Testumgebung auf, indem ein Spieler, ein Raum und
     * Gegenstände hinzugefügt werden.
     */
    @Before
    public void setUp() {
        player = new Player();
        room = new Room("Testzimmer");
        book = new Item("Ein Buch");
        key = new Key("001", "Ein Schlüssel");
        schwert = new Item("Ein scharfes Schwert.");
        schild = new Item("Ein starker Schild.");
        showInventoryCommand = new ShowInventoryCommand();
        player = new Player(); // assuming a Player class with an inventory
    
        room.setItem(book);
        room.setItem(key);
        player.setCurrentRoom(room);
        System.setOut(new PrintStream(outContent));
}

    /**
     * Testet TakeCommand mit einem existierenden Gegenstand.
     */
    @Test
    public void testTakeCommandValidItem() {
        TakeCommand takeCommand = new TakeCommand();
        takeCommand.setSecondWord("Ein Buch");

        assertFalse(takeCommand.execute(player));
        assertTrue(player.hasItem("Ein Buch"));
        assertFalse(room.getItemsString().contains("Ein Buch"));
    }

    /**
     * Testet TakeCommand mit einem nicht existierenden Gegenstand.
     */
    @Test
    public void testTakeCommandInvalidItem() {
        TakeCommand takeCommand = new TakeCommand();
        takeCommand.setSecondWord("Schwert"); // Schwert existiert nicht im Raum

        assertFalse(takeCommand.execute(player));
        assertFalse(player.hasItem("Schwert"));
    }

    /**
     * Testet TakeCommand ohne Angabe eines Gegenstands.
     */
    @Test
    public void testTakeCommandNoItemSpecified() {
        TakeCommand takeCommand = new TakeCommand();

        assertFalse(takeCommand.execute(player));
        assertTrue(room.getItemsString().contains("Ein Buch"));
        assertFalse(player.hasItem("Ein Buch"));
    }

    /**
     * Testet DropCommand mit einem Gegenstand im Inventar des Spielers.
     */
    @Test
    public void testDropCommandValidItem() {
        // Nimmt den Gegenstand zuerst auf
        player.addItem(book);
        
        DropCommand dropCommand = new DropCommand();
        dropCommand.setSecondWord("Ein Buch");

        assertFalse(dropCommand.execute(player));
        assertTrue(room.getItemsString().contains("Ein Buch"));
        assertFalse(player.hasItem("Ein Buch"));
    }

    /**
     * Testet das DropCommand mit einem Gegenstand, den der Spieler nicht besitzt.
     */
    @Test
    public void testDropCommandInvalidItem() {
        DropCommand dropCommand = new DropCommand();
        dropCommand.setSecondWord("Schwert"); // Spieler hat keinen Schwert-Gegenstand

        assertFalse(dropCommand.execute(player));
        assertFalse(room.getItemsString().contains("Schwert"));
    }
    
    @Test
    public void testCommandInfo() {
        // Verify that the command information is correct
        String expectedInfo = "Mit 'zeige' wird das aktuelle Inventar angezeigt.";
        assertEquals(expectedInfo, showInventoryCommand.commandInfo());
    }

    @Test
    public void testExecuteWithEmptyInventory() {
        player.clearInventory();
       
        showInventoryCommand.execute(player);

        String expectedOutput = "Dein Inventar ist leer.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testExecuteWithNonEmptyInventory() {
        // Add items to the player's inventory
        player.addItem(schwert);
        player.addItem(schild);
        
        // Execute the command
        showInventoryCommand.execute(player);

        // Check the output message for a non-empty inventory
        String expectedOutput = "In deinem Inventar befinden sich folgende Gegenstände:\n"
                              + "- Ein scharfes Schwert.\n"
                              + "- Ein starker Schild.";
        assertEquals(expectedOutput.trim(), outContent.toString().trim());
    }
}