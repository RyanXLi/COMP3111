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
 * Tests for the DataTable class.
 * 
 * You'll be writing tests here for the Unit Testing lab!
 * 
 * @author victorkwan
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
		DataTable dataTable = new DataTable();
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
	}
	
	@Test
	void testRemoveCol_Exists() throws DataTableException {
		DataTable dataTable = new DataTable();
		dataTable.addCol("testNumberColumn", testDataColumn);
		dataTable.removeCol("testNumberColumn");
		assertEquals(0, dataTable.getNumCol());
	}
	

}
