package core.comp3111;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.BufferedReader; 
import java.io.FileReader;

import java.util.ArrayList;
import java.util.*;

/*
 * This is a function to load .csv to create a datatable
 * The inputs are the filename and miss data handling type(Median or Mean)
 */

public class LoadCSV {

	public DataTable loadCSV(String fileName, String handleType) throws IOException, DataTableException {
		
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
		ArrayList<ArrayList<String>> csvList = new ArrayList<>();
		
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
		double numfill = 0;
		
		// Generate a type mapping
		ArrayList<String> typeMap = new ArrayList<>();
		String Indicator = "Number";
		for (int i = 0; i < RowNum; i++) {
			for (int j = 0; j < csvList.size(); j++) {
				
				if (csvList.get(j).size() < i) {
					continue;
				}
				
				if (csvList.get(j).get(i) == "") {
					continue;
				} // so we define all comma case as Number
				
				if (isNumber(csvList.get(j).get(i)) == false) {
					Indicator = "String";
					// if one in the col is not string, it is string type
				}
			}
			
			typeMap.add(Indicator);
		}
		
		// first fill everything with ""
		
		for(int i = 0; i < csvList.size(); i++) {
			while (csvList.get(i).size() < RowNum) {
				csvList.get(i).add(stringfill);
			}
		}
		
		// deal with special fill
		for (int i = 0; i < RowNum; i++) {
			
			if (typeMap.get(i) == "Number") {
				ArrayList<Double> temp = new ArrayList<>();
				// get the numbers
				for (int j = 0; j < csvList.size(); j++) {
					if(csvList.get(j).get(i) != "") {
						temp.add(Double.parseDouble(csvList.get(j).get(i)));
					}
				}
				
				// Handle Mean
				if (handleType == "Mean") {
					double sum = 0;
					for (int j = 0; j < temp.size(); j++) {
						sum = sum + temp.get(j);
					}
					numfill = sum/ temp.size();
				}
				
				// Handle Media
				if (handleType == "Median") {
					// Sorting 
					Collections.sort(temp);
					
					int index;
					if (temp.size()%2 == 1) {
						index = temp.size()/2;
						index++;
						numfill = temp.get(index);
					} else {
						index = temp.size()/2;
						numfill = (temp.get(index) + temp.get(index+1))/2;
					}
				}
				
				for (int j = 0; j < csvList.size(); j++) {
					if(csvList.get(j).get(i) == "") {
						ArrayList<String> filltemp = csvList.get(j);
						filltemp.set(i, Double.toString(numfill));
						csvList.set(j, filltemp);
					}
				}
			}
		}
		
		//Set the columns and append to the result
		for (int i = 0; i < RowNum; i++) {
			
			if (typeMap.get(i) == "Number") {
				
				ArrayList<Double> temp = new ArrayList<>();
				// get the numbers
				for (int j = 0; j < csvList.size(); j++) {
					temp.add(Double.parseDouble(csvList.get(j).get(i)));
				}
				DataColumn newCol = new DataColumn("Number", temp.toArray());
				result.addCol("Number", newCol);
			
			} else {
				
				ArrayList<String> temp = new ArrayList<>();
				// get the strings
				for (int j = 0; j < csvList.size(); j++) {
					temp.add(csvList.get(j).get(i));
				}
				DataColumn newCol = new DataColumn("String", temp.toArray());
				result.addCol("Number", newCol);
			}
			
		}
		
		return result;
	}
	
	// Help Function: check if it's number
	public static boolean isNumber(String str) {
		try {
			double check = Double.parseDouble(str);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
}
