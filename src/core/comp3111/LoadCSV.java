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

	public static DataTable loadCSV(String fileName, String handleType) throws IOException, DataTableException {
		
		DataTable result = new DataTable();
		int RowNum = 0;
		int ColNum = 0;
		
		// Find the size of the table
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = null;
			
		while ((line = reader.readLine()) != null) {
			String[] item = line.split(",",-1);
			if (item.length > RowNum) {
				RowNum = item.length;
			}
				
			ColNum = ColNum + 1;
		}
		reader.close();

		// Load the data
		ArrayList<ArrayList<String>> csvList = new ArrayList<>();
		
		BufferedReader reader2 = new BufferedReader(new FileReader(fileName));
		String line2 = new String();
		
		// Read the name row
		ArrayList<String> nameMap = new ArrayList<>();
		line2 = reader2.readLine();
		ColNum = ColNum -1;
		String[] nameRead = line2.split(",",-1);
		
		for (int i = 0; i < nameRead.length; i++) {
			nameMap.add(nameRead[i]);
		}
			// add "" as the primary name if not specified
		while (nameMap.size() < RowNum) {
			nameMap.add("");
		}
		
		
		while ((line2 = reader2.readLine()) != null) {
				String[] items = line2.split(",",-1);
				ArrayList<String> temp = new ArrayList<>();
				
				for (int i = 0; i < items.length; i++) {
					temp.add(items[i]);
				}
				
				csvList.add(temp);
		}
		reader2.close();
		
		// Decide the filling staff
		// There are two situations when we need fill: Row < RowNum and ,, for Num
		
		String stringfill = "";
		double numfill = 0;
		
		// Generate a type mapping
		ArrayList<String> typeMap = new ArrayList<>();
		String indicator = "Number";
		boolean allComma = true;
		
		for (int i = 0; i < RowNum; i++) {
			allComma = true;
			indicator = "Number";
			
			for (int j = 0; j < csvList.size(); j++) {
				
				if (csvList.get(j).size() <= i) {
					continue;
				}
				
				if (csvList.get(j).get(i).isEmpty()) {
					continue;
				} // so we define all comma case as String
				else{
					allComma = false;
				}
				
				if (isNumber(csvList.get(j).get(i)) == false) {
					indicator = "String";
					// if one in the col is not string, it is string type
				}
			}
			
			if (allComma == false) {
				typeMap.add(indicator);
			} else {
				typeMap.add("String");
			}
			
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
					if(!csvList.get(j).get(i).isEmpty()) {
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
				
				// Handle Median
				if (handleType == "Median") {
					// Sorting 
					Collections.sort(temp);
				
					int index;
					if (temp.size()%2 == 1) {
						index = temp.size()/2;
						numfill = temp.get(index);
					} else {
						index = temp.size()/2;
						numfill = (temp.get(index) + temp.get(index-1))/2;
					}
				}
				
				for (int j = 0; j < csvList.size(); j++) {
					if(csvList.get(j).get(i).isEmpty()) {
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
				if (!result.containsColumn(nameMap.get(i))) {
					result.addCol(nameMap.get(i), newCol);
				} else {
					result.addCol(nameMap.get(i) + " @"+ i, newCol);
					// if duplicated add the Column index in the name
				}
				
			
			} else {
				
				ArrayList<String> temp = new ArrayList<>();
				// get the strings
				for (int j = 0; j < csvList.size(); j++) {
					temp.add(csvList.get(j).get(i));
				}
				DataColumn newCol = new DataColumn("Number", temp.toArray());
				if (!result.containsColumn(nameMap.get(i))) {
					result.addCol(nameMap.get(i), newCol);
				} else {
					result.addCol(nameMap.get(i)+ " @" + i, newCol);
				}
			}
			
		}
		
		reader2.close();
		
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
	
	// A constructor for test coverage
		public LoadCSV(){
				init = "Success";
		}
		public String init;
}
