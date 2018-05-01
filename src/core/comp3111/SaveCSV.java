package core.comp3111;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.*;
import java.util.*;

/*
 * This is a function to save dataTable to a csv file
 * The inputs are the filename and the dataTable you want to save
 */

public class SaveCSV {
	
	/**
	 * Save a dataTable to .csv with the filename you want
	 * @author xzhaoar
	 * @param filename
	 *            - the name you want, or the file you want to replace
	 * @param source
	 *            - the DataTable you want to save
	 * @throws DataTableException,IOException
	 *             - It throws DataTableException if a column is already exist, or
	 *             the row size does not match.
	 *             - It throws IOException if the file cannot be open or cannot be written
	 */
	public static void saveCSV(String fileName, DataTable source) throws IOException, DataTableException {
		
		ArrayList<DataColumn> result = source.getDataTableCols();
		String str = new String();
		// File name problem has been settled in the UI
		File csv = new File(fileName); 
        BufferedWriter bw = new BufferedWriter(new FileWriter(csv)); 
		
        // Write the ith digit of the DataColumn line-by-line
		for (int i = 1; i <= source.getNumRow(); i++) {
			for (int j = 0; j < result.size(); j++){
					Object[] temp = result.get(j).getData();// Current Data Column
						str = temp[i-1].toString();
						bw.write(str);
					if (j + 1 < result.size()) {
						bw.write(",");						
					}
			}
			bw.newLine();
		}
		bw.close();
		
	}
	
	// A constructor for test coverage
		public SaveCSV(){
			init = "Success";
		}
	public String init;
}
