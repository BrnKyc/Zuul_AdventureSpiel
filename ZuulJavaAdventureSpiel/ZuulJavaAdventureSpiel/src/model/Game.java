package model;

import controller.Command;
import controller.Parser;

/**
 * 
 * Dies ist die Hauptklasse des Spiels "Die Welt von Zuul"
 * 
 * "Die Welt von Zuul" ist ein einfaches textbasiertes Adventure-Spiel.
 * Der Spieler kann in der Welt herumlaufen.
 * Um das Spiel interessanter zu machen, sollten weitere Kommandos eingebaut werden!
 * 
 * Um das Spiel zu spielen, wird in der main-Methode eine Instanz dieser Klasse
 * erzeugt und die play-Methode aufgerufen.
 * 
 * Diese Klasse sorgt dafür, dass alle nötigen Objekte erzeugt und
 * initialisiert werden: Es werden alle Räume angelegt, der Parser
 * erzeugt und das Spiel gestartet.
 * 
 * In einer Endlosschleife wird dann dafür gesorgt, dass eingegebene Kommandos
 * analysiert und ausgeführt werden.
 * 
 * @author  Michael Kolling and David J. Barnes
 */

class Game 
{
    private Parser parser;
    private Player player;

    /**
     * Konstruktor der Game-Klasse, legt auch die Raumstruktur fest.
     */
    public Game() 
    {
        player = new Player();
        parser = new Parser();
        createRooms();
    }
    
    /**
     * Die main-Methode instanziiert eine neue Game-Klasse und startet das Spiel.
     */
    public static void main(String[] args) {
    	Game game = new Game();
    	game.play();
    }

    /**
     * Erzeugt alle Räume und die Verbindungen zwischen ihnen.
     */
    private void createRooms()
    {
        Room draussen, hoersaal, mensa, computerraum, adminraum, buero, labor, bibliothek, wohnheim,
        		studio, atelier, terrasse, sekretariat, nordflügel, ostflügel, westflügel, hauptgang;
      
        // Räume erzeugen:
        draussen = new Room("vor dem Haupteingang der Hochschule");
        hoersaal = new Room("in einem Hörsaal");
        mensa = new Room("in der Mensa");
        computerraum = new Room("in einem Computerraum");
        adminraum = new Room("im Raum des Computer-Admins");
        
        //Neu hinzugefügte Räume:
        buero = new Room("in einem Büro");
        labor = new Room("im Labor für Chemie und Biologie");
        bibliothek = new Room("in der Bibliothek");
        wohnheim = new Room("im Studentenwohnheim");
        studio = new Room("im Fitnessstudio");
        atelier = new Room("im Kunstatelier");
        terrasse = new Room("auf der Dachterrasse");
        sekretariat = new Room("im Sekreteriat");
        nordflügel = new Room("im Nordflügel");
        westflügel = new Room("im Westflügel");
        ostflügel = new Room("im Ostflügel");
        hauptgang = new Room("im Hauptgang");
        
        
     // Gegenstände erstellen
        Key schluessel = new Key("Computerraum", "Schlüssel");
        Item buch = new Item("Buch");
        
     // Gegenstände zu Räumen hinzufügen
        labor.setItem(schluessel);
        bibliothek.setItem(buch);
        
     // Tür schließen
        computerraum.lockRoom(schluessel);

     //Neue Raumstruktur:
        
        draussen.setExit("osten", wohnheim);
        draussen.setExit("norden", hauptgang);
        
        wohnheim.setExit("süden", studio);
        
        hauptgang.setExit("westen", westflügel);
        hauptgang.setExit("osten", ostflügel);
        hauptgang.setExit("norden", nordflügel);
        
        westflügel.setExit("westen", computerraum);
        westflügel.setExit("norden", labor);
        westflügel.setExit("süden", hoersaal);
        
        computerraum.setExit("norden", adminraum);
        
        ostflügel.setExit("osten", sekretariat);
        ostflügel.setExit("süden", mensa);
        ostflügel.setExit("norden", bibliothek);
        
        nordflügel.setExit("osten", atelier);
        nordflügel.setExit("norden", terrasse);
        
     // Der Spieler startet das Spiel draußen vor der Hochschule:
        player.setCurrentRoom(draussen);
    }


    /**
     *  Die Hauptroutine des Spiels
     *  Läuft in einer Schleife, bis das Spiel beendet wird.
     */
    public void play() 
    {            
        printWelcome();

        // Hier werden wiederholt Kommando-Eingaben gelesen und die
        // Kommandos ausgeführt, bis das Spiel beendet ist.
                
        boolean finished = false;
        while(! finished) {
            Command command = parser.getCommand();
                finished = command.execute(player);
        }
        System.out.println("Danke fürs Spielen! Schönen Tag noch!");
    }

    /**
     * Gibt die Willkommensnachricht für den Spieler aus.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Willkommen in der Welt von Zuul!");
        System.out.println("Die Welt von Zuul ist ein neues, unglaublich langweiliges Adventure-Spiel.");
        System.out.println("Gib 'hilfe' ein, um Hilfe zu bekommen.");
        System.out.println();
        System.out.println(player.getCurrentRoom().getLongDescription());
    }
}