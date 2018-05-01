package testing.comp3111;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import core.comp3111.DataCollection;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataChart;
import core.comp3111.DataColumn;
import core.comp3111.DataType;

public class DataCollectionTest {
	DataCollection dc;
	
	@BeforeEach
	void init() {
		dc = new DataCollection();
	}
	
	@Test
	void testCoverageEmptyDataCollectionConstructor() {
		assert(dc.getTableCollection()!=null);
		assert(dc.getChartCollection()!=null);
	}
	
	@Test
	void testAddDataTable() {
		dc.addDataTable(new DataTable());
		assert(dc.containsTable("DataTable1"));
	}

	@Test
	void testAddDataChart() {
		dc.addDataChart(new DataChart());
		assert(dc.containsChart("DataChart1"));
	}
	
	@Test
	void testGetDataTableExistAndNonExist() throws DataTableException{
		DataTable dt = new DataTable();
		dt.addCol("testnumCol", new DataColumn());
		dc.addDataTable(dt);
		assert(dc.getDataTable("DataTable1").equals(dt));
		assert(dc.getDataTable("DataTable2")==null);
	}
	
	@Test
	void testGetDataChartExistAndNonExist() throws DataTableException{
		DataChart dtc = new DataChart();
		dc.addDataChart(dtc);
		assert(dc.getDataChart("DataChart1").equals(dtc));
		assert(dc.getDataChart("DataChart2")==null);
	}
	
	
	@Test
	void testRemoveDataTable() throws DataTableException{
		DataTable dt = new DataTable();
		dt.addCol("testnumCol", new DataColumn());
		dc.addDataTable(dt);
		assert(dc.containsTable("DataTable1")==true);
		dc.removeDataTable("DataTable2");
		assert(dc.containsTable("DataTable1")==true);
		dc.removeDataTable("DataTable1");
		assert(dc.containsTable("DataTable1")==false);
	}
	
	@Test
	void testRemoveDataChart() throws DataTableException{
		DataChart dtc = new DataChart();
		dc.addDataChart(dtc);
		assert(dc.containsChart("DataChart1")==true);
		dc.removeDataChart("DataChart2");
		assert(dc.containsChart("DataChart1")==true);
		dc.removeDataChart("DataChart1");
		assert(dc.containsChart("DataChart1")==false);
	}
	
	@Test
	void testNumFilterReplace() throws DataTableException {
		DataTable dataTable= new DataTable();
		DataColumn dataColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1,2,3,4,5,6});
		dataTable.addCol("testDataColumn", dataColumn);
		dc.addDataTable(dataTable);
		dc.numFilter("DataTable1", "testDataColumn", ">", "3", true);
		assert(dc.getDataTable("DataTable1").getNumRow()==3);
	}
	
	@Test
	void testNumFilterCreate() throws DataTableException {
		DataTable dataTable= new DataTable();
		DataColumn dataColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1,2,3,4,5,6});
		dataTable.addCol("testDataColumn", dataColumn);
		dc.addDataTable(dataTable);
		dc.numFilter("DataTable1", "testDataColumn", ">", "3", false);
		assert(dc.getDataTable("DataTable2").getNumRow()==3);
	}
	@Test
	void testNumFilter_NumberFormatException() throws DataTableException,NumberFormatException {
		DataTable dataTable= new DataTable();
		DataColumn dataColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1,2,3,4,5,6});
		dataTable.addCol("testDataColumn", dataColumn);
		dc.addDataTable(dataTable);
		dc.numFilter("DataTable1", "testDataColumn", ">", "ABC", true);
	}
	
	
	@Test
	void testNumFilter_DataTableException() throws DataTableException,NumberFormatException {
		DataTable dataTable= new DataTable();
		DataColumn dataColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1,2,3,4,5,6});
		dataTable.addCol("testDataColumn", dataColumn);
		assertThrows(DataTableException.class, ()->dataTable.addCol("testDataColumn", new DataColumn()));
	}
	
	
	@Test
	void testTextFilterReplace() throws DataTableException {
		DataTable dataTable= new DataTable();
		DataColumn dataColumn = new DataColumn(DataType.TYPE_STRING, new String[] {"1","2","3","4","5","6"});
		dataTable.addCol("testDataColumn", dataColumn);
		dc.addDataTable(dataTable);
		dc.textFilter("DataTable1", "testDataColumn", "3", true);
		assert(dc.getDataTable("DataTable1").getNumRow()==1);
	}
	
	@Test
	void testTextFilterCreate() throws DataTableException {
		DataTable dataTable= new DataTable();
		DataColumn dataColumn = new DataColumn(DataType.TYPE_STRING, new String[] {"1","2","3","4","5","6"});
		dataTable.addCol("testDataColumn", dataColumn);
		dc.addDataTable(dataTable);
		dc.textFilter("DataTable1", "testDataColumn", "3", false);
		assert(dc.getDataTable("DataTable2").getNumRow()==1);
	}
}
