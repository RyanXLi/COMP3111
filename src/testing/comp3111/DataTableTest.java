
package testing.comp3111;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
	DataTable dataTable;
	DataColumn testStringColumn;
    @BeforeEach
    void init() {
    	testDataColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1,2,3,4,5,6});
    	testStringColumn = new DataColumn(DataType.TYPE_STRING, new String[] {"1","2","3","4","5","6"});
    	dataTable= new DataTable();
    }
	@Test
	void testGetNumRow_Empty() {
		assertEquals(0, dataTable.getNumRow());
	}
	
	@Test
	void testGetNumRow_NonEmpty() throws DataTableException {
		dataTable.addCol("testColumn", new DataColumn());
		
		assertEquals(0, dataTable.getNumRow());
	}
	
	@Test
	void testAddCol_SizeMismatch()throws DataTableException{
		dataTable.addCol("testColumn", testDataColumn);
		assertThrows(DataTableException.class, ()->dataTable.addCol("testNewColumn",new DataColumn()));
	}
	
	@Test
	void testAddCol_AlreadyExist()throws DataTableException{
		dataTable.addCol("testColumn", testDataColumn);
		assertThrows(DataTableException.class, ()->dataTable.addCol("testColumn", testStringColumn));
	}
	
	@Test
	void testAddCol_AddAnotherColumn()throws DataTableException{
		dataTable.addCol("testDataColumn", testDataColumn);
		dataTable.addCol("testStringColumn", testStringColumn);
		assert(dataTable.containsColumn("testStringColumn"));
	}
	
	@Test
	void testAddCol_RemoveColExist()throws DataTableException{
		dataTable.addCol("testDataColumn", testDataColumn);
		dataTable.addCol("testStringColumn", testStringColumn);
		dataTable.removeCol("testStringColumn");
		assert(dataTable.containsColumn("testStringColumn")==false);
	}
	
	@Test
	void testAddCol_RemoveColNonExist()throws DataTableException{
		dataTable.addCol("testDataColumn", testDataColumn);
		assertThrows(DataTableException.class, ()->dataTable.removeCol("testStringColumn"));
	}
	
	@Test
	void testAddCol_GetColExist()throws DataTableException{
		dataTable.addCol("testDataColumn", testDataColumn);
		dataTable.addCol("testStringColumn", testStringColumn);
		assert(dataTable.getCol("testStringColumn").equals(testStringColumn));
	}

	@Test
	void testAddCol_GetColNonExist()throws DataTableException{
		dataTable.addCol("testDataColumn", testDataColumn);
		assert(dataTable.getCol("testStringColumn")==null);
	}
	
	@Test
	void testFilterByOperator_GreaterThan()throws DataTableException{
		dataTable.addCol("testDataColumn", testDataColumn);
		DataTable afterFilter=dataTable.filterByOperator("testDataColumn", ">", 3);
		assert(afterFilter.getNumRow()==3);
	}
	
	@Test
	void testFilterByOperator_SmallerThan()throws DataTableException{
		dataTable.addCol("testDataColumn", testDataColumn);
		DataTable afterFilter=dataTable.filterByOperator("testDataColumn", "<", 3);
		assert(afterFilter.getNumRow()==2);
	}
	
	@Test
	void testFilterByOperator_GreaterOrEqual()throws DataTableException{
		dataTable.addCol("testDataColumn", testDataColumn);
		DataTable afterFilter=dataTable.filterByOperator("testDataColumn", ">=", 3);
		assert(afterFilter.getNumRow()==4);
	}
	
	@Test
	void testFilterByOperator_SmallerOrEqual()throws DataTableException{
		dataTable.addCol("testDataColumn", testDataColumn);
		DataTable afterFilter=dataTable.filterByOperator("testDataColumn", "<=", 3);
		assert(afterFilter.getNumRow()==3);
	}
	
	@Test
	void testFilterByOperator_Equal()throws DataTableException{
		dataTable.addCol("testDataColumn", testDataColumn);
		DataTable afterFilter=dataTable.filterByOperator("testDataColumn", "==", 3);
		assert(afterFilter.getNumRow()==1);
	}
	
	@Test
	void testFilterByOperator_Empty()throws DataTableException{
		dataTable.addCol("testDataColumn", new DataColumn());
		DataTable afterFilter=dataTable.filterByOperator("testDataColumn", ">", 3);
		assert(afterFilter.getNumRow()==dataTable.getNumRow());
	}
	
	@Test
	void testFilterByLabel_Empty()throws DataTableException{
		dataTable.addCol("testStringColumn", new DataColumn());
		DataTable afterFilter=dataTable.filterByLabel("testStringColumn", "1");
		assert(afterFilter.getNumRow()==dataTable.getNumRow());
	}
	
	@Test
	void testFilterByOperator_Default()throws DataTableException{
		dataTable.addCol("testDataColumn", testDataColumn);
		DataTable afterFilter=dataTable.filterByOperator("testDataColumn", "!=",4);
		assert(afterFilter.getNumRow()==0);
	}
	

	@Test
	void testFilterByLabel()throws DataTableException{
		dataTable.addCol("testStringColumn", testStringColumn);
		DataTable afterFilter=dataTable.filterByLabel("testStringColumn", "1");
		assert(afterFilter.getNumRow()==1);
	}
	
	@Test
	void testGetDataTable()throws DataTableException{
		dataTable.addCol("testStringColumn", testStringColumn);
		assert(dataTable.getDataTable().keySet().contains("testStringColumn"));

	}
}

