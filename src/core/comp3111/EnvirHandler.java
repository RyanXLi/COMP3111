package core.comp3111;

import java.io.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/*
 * This is a function to handle save and load of .comp3111
 * The inputs are current DataCollection function(S or L) 
 * and the filename(with .comp3111 as the end)
 */

public class EnvirHandler {
	public static DataCollection envirHandler(DataCollection dc, String filename, String function) throws IOException, ClassNotFoundException {
		
		DataCollection output = new DataCollection();
		// the filename problem is handled in the UI by checking the last digits
		
		if (function == "S") {
			// create a file folder for data collection
			
			FileOutputStream fos = null;
			
			// Serialize the objects and put them in the oos
			fos = new FileOutputStream(filename);
			ObjectOutputStream oos = null;
			oos = new ObjectOutputStream(fos);
			DataCollection temp = dc;
			oos.writeObject(temp);
			oos.flush();
			oos.close();
		
			
		} else if (function == "L") {
			// import the objects to oos and make a new dataCollection through it
			
			FileInputStream fis = null;
			fis = new FileInputStream(filename);
			ObjectInputStream ois = null;
			ois = new ObjectInputStream(fis);
			output = (DataCollection)ois.readObject();
		}		

		return output;
		
	}
	
	// A constructor for test coverage
		public EnvirHandler(){
				init = "Success";
		}
		public String init;
}
