package core.comp3111;

import java.io.FileNotFoundException;
import java.io.BufferedReader; 
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * This is a function to load .csv to create a datatable
 * The inputs are the filename and miss data handling type
 */

public class LoadCSV {
	
	private static final int ColNum = 0;

	public DataTable loadCSV(String fileName, String handleType) throws IOException {
		
		DataTable result = new DataTable();
		int RowNum = 0;
		int ColNum = 0;
		
		
		
		// Find the size of the table
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				String[] item = line.split(",");
				if (item.length > RowNum) {
					RowNum = item.length;
				}
				ColNum = ColNum + 1;
			}
		} catch (Exception e) {
				System.out.println("Given fileName cannot be processed");
		}
		
		
		// Load the data
		ArrayList<ArrayList<String>> csvList = new ArrayList<ArrayList<String>>();
		
		BufferedReader reader2 = new BufferedReader(new FileReader(fileName));
		String line2 = null;
		
		while ((line2 = reader2.readLine()) != null) {
				String[] items = line2.split(",");
				ArrayList<String> temp = null;
				for (int i = 0; i < items.length; i++) {
					temp.add(items[i]);
				}
				csvList.add(temp);
		}
		
		
		// Decide the filling staff
		// There are two situations when we need fill: Row < RowNum and ,, for Num
		String stringfill = "";
		int numfill = 0;
		
		// first fill everything with ""
		for(int i = 0; i < csvList.size(); i++) {
			while (csvList.get(i).size() < ColNum) {
				csvList.get(i).add(stringfill);
			}
		}
		
		// Transpose the Arraylist
		
			
			
			
					
					

		return result;
	}
}
