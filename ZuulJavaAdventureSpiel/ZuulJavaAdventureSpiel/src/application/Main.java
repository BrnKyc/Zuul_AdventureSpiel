package application;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import controller.BaseSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Inventory;
import javafx.stage.Modality;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
	    try {
	    	URL imageUrl = Main.class.getResource("/images/myImage.png");
	        if (imageUrl != null) {
	            ImageIcon icon = new ImageIcon(imageUrl);
	            JLabel label = new JLabel(icon);
	            JOptionPane.showMessageDialog(null, label, "Image Test", JOptionPane.PLAIN_MESSAGE);
	        } else {
	            System.out.println("Image not found!");
	        }
	    
	        showMurderScenePopup();
	    	
	    	// Inventar beim Start leeren
	        Inventory.clearInventory();

	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BaseScene.fxml"));
	        StackPane root = loader.load();

	        Scene scene = new Scene(root);
	        primaryStage.setTitle("Zuul Adventure - Startseite");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
    public static void main(String[] args) {
        launch(args);
    }
    private void showMurderScenePopup() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Schreckliche Entdeckung!");
        alert.setHeaderText("Eine Leiche wurde gefunden!");
        alert.setContentText("Du stehst vor einer grausamen Szene. Eine Person wurde ermordet. Deine Aufgabe ist es, herauszufinden, wer der Mörder ist. Sammle Hinweise, finde die Mordwaffe, und löse den Fall!");
        alert.showAndWait();
    }
    
}