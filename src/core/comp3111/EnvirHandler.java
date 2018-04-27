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
	public static DataCollection envirHandler(DataCollection dc, String filename, String function) {
		
		DataCollection output = new DataCollection();
		// the filename problem is handled in the UI by checking the last digits
		
		if (function == "S") {
			// create a file folder for data collection
			File file = new File("3111save"+ File.separator + filename);
			FileOutputStream fos = null;
			
			// Serialize the objects and put them in the oos
			try {
				fos = new FileOutputStream(file);
				ObjectOutputStream oos = null;
				try {
					oos = new ObjectOutputStream(fos);
					oos.writeObject(dc);
					oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						oos.close();
					} catch (IOException e) {
						Alert alert = new Alert(AlertType.WARNING,"fail to close oos£º"+e.getMessage());
						alert.showAndWait();
					}
				}
			} catch (FileNotFoundException e) {
				Alert alert = new Alert(AlertType.WARNING,"fail to create the file"+e.getMessage());
				alert.showAndWait();
			} finally{
				try {
					fos.close();
				} catch (IOException e) {
					Alert alert = new Alert(AlertType.WARNING,"fail to close the file"+e.getMessage());
					alert.showAndWait();
				}
			}
			
		} else if (function == "L") {
			// import the objects to oos and make a new dataCollection through it
			File file2 = new File(filename);
			
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file2);
				ObjectInputStream ois = null;
				try {
					ois = new ObjectInputStream(fis);
					try {
						output = (DataCollection)ois.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						ois.close();
					} catch (IOException e) {
						Alert alert = new Alert(AlertType.WARNING,"fail to close ois"+e.getMessage());
						alert.showAndWait();
					}
				}
				
			} catch (FileNotFoundException e) {
				Alert alert = new Alert(AlertType.WARNING,"file not found"+e.getMessage());
				alert.showAndWait();
			} finally {
				try {
					fis.close();
				} catch (IOException e) {
					Alert alert = new Alert(AlertType.WARNING,"fail to close fis"+e.getMessage());
					alert.showAndWait();
				}
			}
			
		}
		
		
		return output;
		
	}

}
