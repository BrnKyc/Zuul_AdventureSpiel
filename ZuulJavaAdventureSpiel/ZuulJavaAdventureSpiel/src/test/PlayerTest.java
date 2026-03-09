package test;
import org.junit.Before;

import org.junit.Test;

import model.Player;
import model.Room;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player player;
    private Room room1;
    private Room room2;

    @Before
    public void setUp() {
        player = new Player();
        room1 = new Room("Raum 1");
        room2 = new Room("Raum 2");
        
        room1.setExit("norden", room2); // Raum 1 führt nach Norden zu Raum 2
        room2.setExit("süden", room1); // Raum 2 führt nach Süden zurück zu Raum 1

        player.setCurrentRoom(room1); // Spieler startet in Raum 1
    }

    @Test
    public void testInitialRoom() {
        assertEquals("Raum 1", player.getCurrentRoomName());
    }

    @Test
    public void testWalkToNextRoom() {
        player.walk("norden");
        assertEquals("Raum 2", player.getCurrentRoomName());
    }

    @Test
    public void testWalkToInvalidDirection() {
        player.walk("westen");
        assertEquals("Raum 1", player.getCurrentRoomName()); // Spieler sollte im Raum 1 bleiben
    }

    @Test
    public void testGoBack() {
        player.walk("norden"); // Gehe zu Raum 2
        player.goBack(); // Gehe zurück zu Raum 1
        assertEquals("Raum 1", player.getCurrentRoomName());
    }

    @Test
    public void testGoBackWhenNoHistory() {
        player.goBack(); // Sollte noch in Raum 1 sein
        assertEquals("Raum 1", player.getCurrentRoomName());
    }


    @Test
    public void testGoBackMultipleSteps() {
        Player player = new Player();  // Angenommen, dass der Spieler in Raum 1 startet
        
        // Gehe in verschiedene Richtungen und speichere die Räume
        player.walk("norden");  // Gehe zu Raum 2
        player.walk("norden");  // Gehe zu Raum 3
        player.walk("süden");   // Gehe zurück zu Raum 2
        
        // Assert: Überprüfen, ob der Spieler im richtigen Raum ist
        assertEquals("Raum 2", player.getCurrentRoomName(), "Spieler sollte sich in Raum 2 befinden");
        
        // Act & Assert: Gehe zurück und teste mehrfaches Zurückgehen
        player.goBack();  // Gehe zurück zu Raum 3
        assertEquals("Raum 3", player.getCurrentRoomName(), "Spieler sollte sich in Raum 3 befinden");

        player.goBack();  // Gehe zurück zu Raum 2
        assertEquals("Raum 2", player.getCurrentRoomName(), "Spieler sollte sich in Raum 2 befinden");

        player.goBack();  // Gehe zurück zu Raum 1
        assertEquals("Raum 1", player.getCurrentRoomName(), "Spieler sollte sich in Raum 1 befinden");

        // Gehe weiter zurück, aber es gibt keinen Raum mehr davor
        player.goBack();  // Sollte in Raum 1 bleiben
        assertEquals("Raum 1", player.getCurrentRoomName(), "Spieler sollte sich weiterhin in Raum 1 befinden");
        
        // Überprüfen, ob noch weitere `goBack()` Aufrufe keine Auswirkungen haben
        player.goBack();  // Sollte in Raum 1 bleiben
        assertEquals("Raum 1", player.getCurrentRoomName(), "Spieler sollte sich weiterhin in Raum 1 befinden");

        // Gehe vorwärts und gehe dann zurück, um zu prüfen, ob sich die Position ändert
        player.walk("norden"); // Gehe zu Raum 2
        assertEquals("Raum 2", player.getCurrentRoomName(), "Spieler sollte sich in Raum 2 befinden");
        player.goBack(); // Gehe zurück zu Raum 1
        assertEquals("Raum 1", player.getCurrentRoomName(), "Spieler sollte sich in Raum 1 befinden");
    }
}
