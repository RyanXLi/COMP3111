package core.comp3111;

import java.io.BufferedReader; 
import java.io.FileReader; 

/*
 * This is a function to load .csv to create a datatable
 * The inputs are the filename and miss data handling type
 */

public class LoadCSV {
	
	public DataTable loadCSV(String fileName, String handleType) {
		
		DataTable result = new DataTable();
		
		// Find the size of the table
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = null;
			int RowNum = 0;
			int ColNum = 0;
			
			while ((line = reader.readLine()) != null) {
				String[] items = line.split(",");
				if (items.length > RowNum) {
					RowNum = items.length;
				}
				ColNum = ColNum + 1;
			}
		} catch (Exception e) {
				System.out.println("Given fileName cannot be processed");
		}
		
		// Decide the filling staff
			
			
			
					
					

		return result;
	}
}
