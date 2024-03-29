package testing.comp3111;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.comp3111.DataCollection;
import core.comp3111.DataColumn;
import core.comp3111.DataType;
import core.comp3111.EnvirHandler;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.LoadCSV;
import core.comp3111.SaveCSV;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

/** A Save/Load .comp3111 file test
 * 
 * @author xzhaoar
 *
 */
class EnvirHandleTest {  
	//DataColumn testDataColumn;
	DataTable dataTable;
	DataColumn testStringColumn;
	DataCollection dc= new DataCollection();
	DataCollection test = new DataCollection();
	/** 
	 * Initialize the test data needed.
	 * 
	 * @author xzhaoar
	 *
	 */
	@BeforeEach
    void init() {
    	//testDataColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1,2,3});
    	testStringColumn = new DataColumn(DataType.TYPE_STRING, new String[] {"1","2","3"});
    	dataTable= new DataTable();
    }
	/** 
	 * Test for satisfying the 100 coverage.
	 * 
	 * @author xzhaoar
	 *
	 */    
	 @Test
	    void testConstructor() {
	    	EnvirHandler x = new EnvirHandler();
	    	assert(x.init == "Success");
	    }
	/** 
	 * Test for Saving and Loading, the dataCollection should be the same.
	 * 
	 * @author xzhaoar
	 *
	 */   	
	@Test
	void writeFileTest() throws DataTableException, IOException, ClassNotFoundException {
		//dataTable.addCol("testDataColumn", testDataColumn);
		dataTable.addCol("testStringColumn", testStringColumn);
		dc.addDataTable(dataTable);;
		
		EnvirHandler.envirHandler(dc, "test.comp3111", "S");
		
		test = EnvirHandler.envirHandler(dc, "test.comp3111", "L");
		assert(test.getDataTable("DataTable1").containsColumn("testStringColumn"));
		
	}  
}
