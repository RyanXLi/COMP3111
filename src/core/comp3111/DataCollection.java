package core.comp3111;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

//
<<<<<<< HEAD
   /**
    * A collection of two maps, one to store all the DataTables 
    * and one to stor all the Charts
    * 
    * 
    * @author qchen
    *
    */
public class DataCollection {
	/**
	 * Construct - create an empty DataCollection
	 */
=======
public class DataCollection implements Serializable{
	
	private static final long serialVersionUID = 1L;
>>>>>>> refs/remotes/origin/master
	
	public DataCollection(){
		tableCollection = new LinkedHashMap<String, DataTable>();
		chartCollection = new LinkedHashMap<String, DataChart>();
		tableNum=1;
		chartNum=1;
	}
	
<<<<<<< HEAD
	/**
	 * Check whether the DataTable exists by the given name
	 * @param tableName
	 *            - the name of the target DataTable
	 * @return True if it contains the dataTable, false otherwise
	 * 
	 */
=======
	DataCollection(DataCollection dc){
		tableCollection = dc.tableCollection;
		chartCollection = dc.chartCollection;
		tableNum = dc.tableNum;
		chartNum = dc.chartNum;
	}
>>>>>>> refs/remotes/origin/master
	
	public boolean containsTable(String tableName) {
		return tableCollection.containsKey(tableName);
	}
	
<<<<<<< HEAD
	
	/**
	 * Check whether the Chart exists by the given name
	 * @param chartName
	 *            - the name of the target chart
	 * @return True if it contains the chart, false otherwise
	 * 
	 */
	
=======
>>>>>>> refs/remotes/origin/master
	public boolean containsChart(String chartName) {
		return chartCollection.containsKey(chartName);
	}
	
	/**
	 * Add a dataTable to the collection.
	 * 
	 * @param dt
	 *        - the DataTable, it will be given a name automatically
	 *
	 */
	
	public void addDataTable(DataTable dt) {
		String name = "DataTable" + tableNum.toString();
		tableCollection.put(name, dt);
		++tableNum;
		return;
	}
	
	/**
	 * Add a Chart to the collection.
	 * 
	 * @param dc
	 *        - the Chart, it will be given a name automatically
	 * 
	 */
	
	public void addDataChart(DataChart dc) {
		String name = "DataChart" + chartNum.toString();
		chartCollection.put(name, dc);
		++chartNum;
		return;
	}
	
	
	/**
	 * Get the DataTable map
	 * @return  the map containing all the DataTables
	 */
	
	public Map<String, DataTable> getTableCollection(){
		return tableCollection;
	}
	
	
	/**
	 * Get the Chart map
	 * @return the map containing all the Charts
	 */
	public Map<String, DataChart> getChartCollection(){
		return chartCollection;
	}
	
	
	/**
	 * Get the DataTable object based on the give tableName. Return null if the
	 * DataTable does not exist
	 * 
	 * @param tableName
	 *            - name of the DataTable needed to fetch.

	 * @return the required DataTable or null
	 */
	
	public DataTable getDataTable(String tableName) {
		if(containsTable(tableName)) {
			return tableCollection.get(tableName);
		}
		return null;
	}	
	
	/**
	 * Get the Chart object based on the give chartName. Return null if the
	 * Chart does not exist
	 * 
	 * @param chartName
	 *            - name of the Chart needed to fetch.

	 * @return the required Chart or null
	 */
	
	
	public DataChart getDataChart(String chartName) {
		if(containsChart(chartName)) {
			return chartCollection.get(chartName);
		}
		return null;
	}
	
	
	/**
	 * Remove a DataTable named tableName from the Collection
	 * Do nothing if such DataTable doesn't exist
	 * 
	 * @param tableName
	 *        - The name of the DataTable the user wants to remove
	 */
	
	public void removeDataTable(String tableName) {
		if(containsTable(tableName)) {
			tableCollection.remove(tableName);
		    --tableNum;	
		}
		return;
	}
	
	/**
	 * Remove a Chart named chartName from the Collection
	 * Do nothing if such Chart doesn't exist
	 * 
	 * @param chartName
	 *        - The name of the Chart the user wants to remove
	 */
	
	public void removeDataChart(String chartName) {
		if(containsChart(chartName)) {
			chartCollection.remove(chartName);
		    --chartNum;	
		}
		return;
	}
	
	
	/**
	 * Do numeric filter on a specified DataTable
	 * then replace the original table with the result or create a new dataTable
	 * @param dtName
	 *        - The DataTable selected to do the filtering
	 * @param colName
	 *        - The column in the DataTable which we based on when filtering
	 * @param op
	 *        - operator used when filtering
	 * @param num
	 *        - target number used when filtering
	 * @param handleMode
	 *        - when True, the original DaTatable is replaced, when false, a new DataTable is created.
	 * @throws DataTableException
	 *        -  It throws DataTableException if the filtering failed.
	 */
	
	
	//NumberFormatException, DataTableException 
	public void numFilter(String dtName, String colName, String op, String num, 
			boolean handleMode) throws DataTableException{
		DataTable originDT = tableCollection.get(dtName);
		DataTable resultDT = new DataTable();
        try{resultDT = originDT.filterByOperator(colName, op, Double.parseDouble(num.toString()));}
        catch(NumberFormatException e3) {
        	return;
        }
        if(handleMode==true) {
        	removeDataTable(dtName);
        }
        addDataTable(resultDT);
        return;
	}
	
	/**
	 * Do textual filter on a specified DataTable
	 * then replace the original table with the result or create a new dataTable 
	 * @param dtName
	 *        - The DataTable selected to do the filtering
	 * @param colName
	 *        - The column in the DataTable which we based on when filtering	 
	 * @param text
	 *        - text used when filtering
	 * @param handleMode
	 *        - when True, the original DaTatable is replaced, when false, a new DataTable is created.
	 * @throws DataTableException
	 *        -  It throws DataTableException if the filtering failed.
	 */
	
	public void textFilter(String dtName, String colName, String text, boolean handleMode) throws DataTableException {
		DataTable originDT = tableCollection.get(dtName);
		DataTable resultDT = new DataTable();
		resultDT = originDT.filterByLabel(colName, text);
		if(handleMode==true) {
	      	removeDataTable(dtName);
	    }
	    addDataTable(resultDT);
	    return;
	}
	
	private Map<String, DataTable> tableCollection;
	private Map<String, DataChart> chartCollection;
    private Integer tableNum = 1;
    private Integer chartNum = 1;
}
