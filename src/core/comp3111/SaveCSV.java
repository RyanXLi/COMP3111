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

	public static void saveCSV(String fileName, DataTable source) throws IOException, DataTableException {
		
		Map<String, DataColumn> result = new HashMap<>(source.getMap());
		
		// File name problem has been settled in the UI
		File csv = new File(fileName); 
        BufferedWriter bw = new BufferedWriter(new FileWriter(csv)); 
		
        // Write the i th digit of the DataColumn line-by-line
		for (int i = 1; i <= source.getNumCol(); i++) {
			for (Map.Entry<String, DataColumn> entry: result.entrySet()) {
					Object[] temp = ((DataColumn) entry).getData();// Current Data Column
					bw.write((String)temp[i-1]);
					if (i != source.getNumCol()) {
						bw.write(",");
					}
			}
			bw.newLine();
		}
		bw.close();
		
	}	
}
