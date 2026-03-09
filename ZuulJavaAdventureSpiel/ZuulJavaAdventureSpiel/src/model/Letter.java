package model;

/**
 * Eine Klasse Letter die vond er Oberklasse Item erbt. Sie enthält Objekte die eine Notiz beinhalten.
 */
public class Letter extends Item{

	public Letter(String information) {
		super(information);
		
	}
	
	public void openLetter() {
		System.out.println("");
	}

}