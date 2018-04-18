package core.comp3111;

import java.io.*;


/*
 * This is a function to handle save and load of .comp3111
 * The inputs are current DataCollection function(S or L) 
 * and the filename(with .comp3111 as the end)
 * I wonder if I need to output something
 */

public class EnvirHandler {
	public DataCollection envirHandler(DataCollection dc, String filename, String function) {
		
		if (filename.substring(-9, -1) != ".comp3111") {
			System.out.println("Such filename is not supported");
		}
		
		DataCollection output = new DataCollection();
		
		if (function == "S") {
			File file = new File("3111save"+ File.separator + filename);
			FileOutputStream fos = null;
			
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
						System.out.println("fail to close oos£º"+e.getMessage());
					}
				}
			} catch (FileNotFoundException e) {
				System.out.println("fail to create the file"+e.getMessage());
			} finally{
				try {
					fos.close();
				} catch (IOException e) {
					System.out.println("fail to close the file"+e.getMessage());
				}
			}
		} else if (function == "L") {
			File file2 = new File("3111save"+ File.separator + filename);
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
						System.out.println("fail to close ois" + e.getMessage());
					}
				}
				
			} catch (FileNotFoundException e) {
				System.out.println("file not found: " + e.getMessage());
			} finally {
				try {
					fis.close();
				} catch (IOException e) {
					System.out.println("fail to close fis"+ e.getMessage());
				}
			}
			
		}
		
		
		
		
	}

}
