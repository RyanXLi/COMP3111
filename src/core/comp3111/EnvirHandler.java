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
	/**
	 * Save/Load all the charts and tables(they are all in a bigger class dataCollection) to/from .comp3111
	 * Remind that if loaded, current data will all be replace as there is only one dataCollection
	 * @author xzhaoar
	 * @param filename
	 *             the name you want, or the file you want to replace
	 * @param dc
	 *             the DataCollection (all the data) you want to save/load
	 * @param function
	 *             "S" for "Save, "L" for load  , the function you want to use
	 * @return DataCollection
	 * 			   return the dataCollection loaded from .comp3111, it will replace the one you are currently using               
	 * @throws IOException            
	 *              It throws IOException if the file cannot be open or cannot be written
	 * @throws ClassNotFoundException
	 *              It throws ClassNotFoundException if the saved file is not serialized by DataCollection
	 */
	
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
