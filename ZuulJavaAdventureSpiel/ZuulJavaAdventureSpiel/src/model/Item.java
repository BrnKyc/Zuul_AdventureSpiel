package model;

import java.util.HashMap;

/**
 * Die Klasse beinhaltet eine Oberklasse Item, von der alle Unterklassen erben.
 */
public class Item {
	
	private String information;
	private HashMap<String, Item> items;
	
	 /**
     * Konstruktor für die Klasse Item.
     * Initialisiert ein Item mit den angegebenen Informationen und einer leeren Sammlung
     * verknüpfter Items.
     *
     * @param information eine Beschreibung oder weitere Details zum Gegenstand
     */
	 public Item(String information) {
		this.information = information;
		//this.bezeichnung = bezeichnung;
		items = new HashMap<>();
		}
	/**
	 * Gibt genauere Informationen ueber den Gegenstand an.
	 *
	 *@return die Beschreibung oder Details des Gegenstandes
	 */
	public String getInformation() {
		 return information;
	 }
	 
	/**
	 * Überarbeitet die Informationen des Gegenstandes.
	 *
	 ** @param information die neuen Informationen oder Details zum Gegenstand
	 */
	 public void setInformation(String information) {
		 this.information = information;
	 }
}
