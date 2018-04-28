package core.comp3111;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import ui.comp3111.DataChart;

//
public class DataCollection implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public DataCollection(){
		tableCollection = new LinkedHashMap<String, DataTable>();
		chartCollection = new LinkedHashMap<String, DataChart>();
		tableNum=1;
		chartNum=1;
	}
	
	DataCollection(DataCollection dc){
		tableCollection = dc.tableCollection;
		chartCollection = dc.chartCollection;
		tableNum = dc.tableNum;
		chartNum = dc.chartNum;
	}
	
	public boolean containsTable(String tableName) {
		return tableCollection.containsKey(tableName);
	}
	
	public boolean containsChart(String chartName) {
		return chartCollection.containsKey(chartName);
	}
	
	
	
	public void addDataTable(DataTable dt) {
		String name = "DataTable" + tableNum.toString();
		tableCollection.put(name, dt);
		++tableNum;
		return;
	}
	
	public void addDataChart(DataChart dc) {
		String name = "DataChart" + chartNum.toString();
		chartCollection.put(name, dc);
		++chartNum;
		return;
	}
	
	
	
	
	public Map<String, DataTable> getTableCollection(){
		return tableCollection;
	}
	
	public Map<String, DataChart> getChartCollection(){
		return chartCollection;
	}
	
	
	
	
	public DataTable getDataTable(String tableName) {
		if(containsTable(tableName)) {
			return tableCollection.get(tableName);
		}
		return null;
	}	
	
	public DataChart getDataChart(String chartName) {
		if(containsChart(chartName)) {
			return chartCollection.get(chartName);
		}
		return null;
	}
	
	
	
	
	public void removeDataTable(String tableName) {
		if(containsTable(tableName)) {
			tableCollection.remove(tableName);
		    --tableNum;	
		}
		return;
	}
	
	
	
	public void removeDataChart(String chartName) {
		if(containsChart(chartName)) {
			chartCollection.remove(chartName);
		    --chartNum;	
		}
		return;
	}
	
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
