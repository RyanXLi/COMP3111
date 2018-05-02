package core.comp3111;


import java.util.HashMap;

import java.util.Map;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * 2D array of data values with the following requirements: (1) There are 0 to
 * many columns (2) The number of row for each column is the same (3) 2 columns
 * may have different type (e.g. String and Number). (4) A column can be
 * uniquely identified by its column name (5) add/remove a column is supported
 * (6) Suitable exception handling is implemented
 * 
 * @author cspeter
 *
 */
public class DataTable implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * Construct - Create an empty DataTable
	 */
	public DataTable() {

		// In this application, we use HashMap data structure defined in
		// java.util.HashMap
		dc = new HashMap<String, DataColumn>();
	}

	
	/**
	 * Add a data column to the table.
	 * 
	 * @param colName
	 *            - name of the column. It should be a unique identifier
	 * @param newCol
	 *            - the data column
	 * @throws DataTableException
	 *             - It throws DataTableException if a column is already exist, or
	 *             the row size does not match.
	 */
	public void addCol(String colName, DataColumn newCol) throws DataTableException {
		if (containsColumn(colName)) {
			throw new DataTableException("addCol: The column already exists");
		}

		int curNumCol = getNumCol();
		if (curNumCol == 0) {
			dc.put(colName, newCol); // add the column
			return; // exit the method
		}

		// If there is more than one column,
		// we need to ensure that all columns having the same size

		int curNumRow = getNumRow();
		if (newCol.getSize() != curNumRow) {
			throw new DataTableException(String.format(
					"addCol: The row size does not match: newCol(%d) and curNumRow(%d)", newCol.getSize(), curNumRow));
		}

		dc.put(colName, newCol); // add the mapping
	}

	/**
	 * Remove a column from the data table
	 * 
	 * @param colName
	 *            - The column name. It should be a unique identifier
	 * @throws DataTableException.
	 *             It throws DataTableException if the column does not exist
	 */
	public void removeCol(String colName) throws DataTableException {
		if (containsColumn(colName)) {
			dc.remove(colName);
			return;
		}
		throw new DataTableException("removeCol: The column does not exist");
	}

	/**
	 * Get the DataColumn object based on the give colName. Return null if the
	 * column does not exist
	 * 
	 * @param colName
	 *            The column name
	 * @return DataColumn reference or null
	 */
	public DataColumn getCol(String colName) {
		if (containsColumn(colName)) {
			return dc.get(colName);
		}
		return null;
	}

	/**
	 * Check whether the column exists by the given column name
	 * 
	 * @param colName
	 * @return true if the column exists, false otherwise
	 */
	public boolean containsColumn(String colName) {
		return dc.containsKey(colName);
	}

	/**
	 * Return the number of column in the data table
	 * 
	 * @return the number of column in the data table
	 */
	public int getNumCol() {
		return dc.size();
	}

	/**
	 * Return the number of row of the data table. This data structure ensures that
	 * all columns having the same number of row
	 * 
	 * @return the number of row of the data table
	 */
	public int getNumRow() {
		if (dc.size() <= 0)
			return dc.size();

		// Pick the first entry and get its size
		// assumption: For DataTable, all columns should have the same size
		Map.Entry<String, DataColumn> entry = dc.entrySet().iterator().next();
		return dc.get(entry.getKey()).getSize();
	}
	
	
	
	/**
	 * 
	 * @return the map containing all the DataColumns
	 */
	public Map<String, DataColumn> getDataTable(){
		return dc;
	}
	
	
	

	public ArrayList<DataColumn> getDataTableCols(){
		ArrayList<DataColumn> dtCols = new ArrayList<>();
		for (DataColumn entry: dc.values()) {
			dtCols.add(entry);
		}
		return dtCols;
	}
	

	
	
	//**********************************************************************//
	//Following is the filtering and transformation algorithm implementation.
	//In these algorithms the dataTye of the target DataColumn is checked
	//before doing transformations
	
	
	
	/**
	 * Do the numeric filtering according to the operator and number provided by user. 
	 * @param colName
	 *         - The column name, which column is based on when filtering.
	 * @param operator
	 *         - The operator, could be >, <, >=, <=, == 
	 * @param num
	 *         - The number provided by user
	 * @return the new DataTable after filtering
	 * 
	 * @throws DataTableException
	 *         It throws DataTableException if the column cannot be added to the new DataTable
	 *         
	 */
	public DataTable filterByOperator(String colName, String operator, double num) throws DataTableException, NumberFormatException {

		DataTable result = new DataTable();
        
		// In case the DataTable is empty
		int rowNum= getNumRow();
		if(rowNum<=0) return result;
	    //Operator op = Operator.getEnum(operator);
	
		Object[] targetData= getCol(colName).getData();
		ArrayList<Integer> keep= new ArrayList<Integer>();
		
		//find out which element to keep in the new DataTable;
		
		
		
		
		if(operator.equals(">")) {
		    for(int i=0; i< getCol(colName).getSize();i++){
			    if(Double.parseDouble(targetData[i].toString())>num) {
				    keep.add(i);
			    }
		    }
		}		
		else if(operator.equals("<")) {
			for(int i=0; i< getCol(colName).getSize();i++){
			    if(Double.parseDouble(targetData[i].toString())<num) {
				    keep.add(i);
			    }
		    }
		}
		else if(operator.equals(">=")) {
		    for(int i=0; i< getCol(colName).getSize();i++){
			    if(Double.parseDouble(targetData[i].toString())>=num) {
				    keep.add(i);
			    }
		    }
		}		
		else if(operator.equals("<="))
			for(int i=0; i< getCol(colName).getSize();i++){
			    if(Double.parseDouble(targetData[i].toString())<=num) {
				    keep.add(i);
			    }
		    }		    
		else if(operator.equals("==")){
			for(int i=0; i< getCol(colName).getSize();i++){
			    if(Double.parseDouble(targetData[i].toString())==num) {
				    keep.add(i);
			    }
		    }
		}
		else {
			return result;
		}
		
		
		//create the new DataTable
		int newSize = keep.size();
		if(newSize<=0) {
			return result;
		}
		//each iteration deals with 1 column
		for(String curColName: dc.keySet()) {
			DataColumn curCol = getCol(curColName);
			Object[] curColData = curCol.getData();
			Object[] newColData = new Object[newSize];
			
			int pos = 0;
			for(int i : keep) {
				newColData[pos] = curColData[i];
				i++;
			}
			
			DataColumn newCol= new DataColumn(curCol.getTypeName(), newColData);
			result.addCol(curColName, newCol);
		}	
		
		return result;
	}
	
	
	
	/**
	 * Do the textual filtering according to the operator and number provided by user. 
	 * @param colName
	 *         - The column name, which column is based on when filtering.
	 * @param label
	 *         - The text provided by user to do filtering
	 *         
	 * @return the new DataTable after filtering
	 * 
	 * @throws DataTableException
	 *         It throws DataTableException if the column cannot be added to the new DataTable
	 */
	public DataTable filterByLabel(String colName, String label) throws DataTableException {
		
		DataTable result = new DataTable();
        
		// In case the DataTable is empty
		int rowNum= getNumRow();
		if(rowNum<=0) return result;
				
		
		Object[] targetData= getCol(colName).getData();
		ArrayList<Integer> keep= new ArrayList<Integer>();
		
		//find out which element to keep in the new DataTable;
		for(int i=0; i<getCol(colName).getSize();i++) {
			if(label.equalsIgnoreCase(targetData[i].toString())) {
				keep.add(i);
			}
		}
		
		//create the new DataTable
		int newSize = keep.size();
		if(newSize<=0) {
			return result;
		}
		//each iteration deals with 1 column
		for(String curColName: dc.keySet()) {
			DataColumn curCol = getCol(curColName);
			Object[] curColData = curCol.getData();
			Object[] newColData = new Object[newSize];
			
			int pos = 0;
			for(int i : keep) {
				newColData[pos] = curColData[i];
				i++;
			}
			
			DataColumn newCol= new DataColumn(curCol.getTypeName(), newColData);
			result.addCol(curColName, newCol);
		}	
		
		return result;
	}
	
	
	

	// attribute: A java.util.Map interface
	// KeyType: String
	// ValueType: DataColumn
	private Map<String, DataColumn> dc;









}



