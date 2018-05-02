package testing.comp3111;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.DataType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A sample DataTable test case written using JUnit. It achieves 100% test
 * coverage on the DataTable class
 * 
 * @author qchenax
 *
 */
public class DataTableTest {
	
	DataColumn testDataColumn;
	DataColumn longerCol;
	DataColumn testDataColumn2;
	
	@BeforeEach
	void init() {
		testDataColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1,2,3});
		longerCol = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1,2,3,4,5});
		testDataColumn2 = new DataColumn(DataType.TYPE_NUMBER, new Number[] {3,2,1});
	}
	
	@Test
	void testGetNumRow_Empty() {
		DataTable dataTable = new DataTable();
		assertEquals(0, dataTable.getNumRow());
	}
	
	@Test
	void testGetNumRow_NonEmpty() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testColumn", new DataColumn());
		
		assertEquals(0, dataTable.getNumRow());
	}
	
	@Test
	void testGetNumCol_NonEmpty() throws DataTableException {
		DataTable dataTable = new DataTable();
		
		dataTable.addCol("testNumberColumn", testDataColumn);
		int numCol = dataTable.getNumCol();
		assertEquals(1, numCol);
	}
	
	@Test
	void testGetCol_NonExistent() throws DataTableException {
\		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn", testDataColumn);
		
		DataColumn dataColumn = dataTable.getCol("testStringColumn");
		assertEquals(null, dataColumn);	
	}
	
	@Test
	void testAddCol_AlreadyExist() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn", testDataColumn);
		assertThrows(DataTableException.class, ()->dataTable.addCol("testNumberColumn", new DataColumn()));
	}
	
	@Test
	void testAddCol_DiffSize() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn", testDataColumn);	
		assertThrows(DataTableException.class, ()->dataTable.addCol("longerColumn", longerCol));
	}
	
	@Test
	void testAddCol_Normal() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn1", testDataColumn);	
		dataTable.addCol("testNumberColumn2", testDataColumn2);
		assertEquals(testDataColumn2, dataTable.getCol("testNumberColumn2"));
	}
	
	@Test

	void testRemoveCol_NotExist() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn", testDataColumn);
		assertThrows(DataTableException.class, ()->dataTable.removeCol("jibberish"));

	void testFilterByOperator_EmptyAfterFilter()throws DataTableException{
		dataTable.addCol("testDataColumn", testDataColumn);
		DataTable afterFilter=dataTable.filterByOperator("testDataColumn", "<", 0);
		assert(afterFilter.getNumRow()==0);

	}
		
	@Test
	void testRemoveCol_Exists() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn", testDataColumn);
		dataTable.removeCol("testNumberColumn");
		assertEquals(0, dataTable.getNumCol());
	}
	
	@Test
	void testFilterByLabel_Empty()throws DataTableException{
		dataTable.addCol("testStringColumn", new DataColumn());
		DataTable afterFilter=dataTable.filterByLabel("testStringColumn", "1");
		assert(afterFilter.getNumRow()==dataTable.getNumRow());
	}
	

	@Test
	void testFilterByLabel()throws DataTableException{
		dataTable.addCol("testStringColumn", testStringColumn);
		DataTable afterFilter=dataTable.filterByLabel("testStringColumn", "1");
		assert(afterFilter.getNumRow()==1);
	}
	
	@Test
	void testFilterByLabel_EmptyAfterFilter()throws DataTableException{
		dataTable.addCol("testStringColumn", testStringColumn);
		DataTable afterFilter=dataTable.filterByLabel("testStringColumn", "0");
		assert(afterFilter.getNumRow()==0);
	}
	@Test
	void testGetDataTable()throws DataTableException{
		dataTable.addCol("testStringColumn", testStringColumn);
		assert(dataTable.getDataTable().keySet().contains("testStringColumn"));

	}

}
