package testing.comp3111;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.comp3111.DataColumn;
import core.comp3111.DataType;
import core.comp3111.DataTable;
import core.comp3111.DataTableException;
import core.comp3111.LoadCSV;
import core.comp3111.SaveCSV;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

/** 
 * A SaveCSV file test
 * 
 * @author xzhaoar
 *
 */
class LoadCSVTest {  
	DataColumn testDataColumn;
	DataColumn testStringColumn;
	DataColumn testDoubleColumn;
	DataColumn testCommaColumn; 
	
	DataTable dataTable;
	/** 
	 * Initialize the test data needed.
	 * 
	 * @author xzhaoar
	 *
	 */
	@BeforeEach
    void init() {
    	testDataColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1,2});
    	testDoubleColumn = new DataColumn(DataType.TYPE_NUMBER, new Double[] {1.1, 2.2, 3.3});
    	testStringColumn = new DataColumn(DataType.TYPE_STRING, new String[] {"a","b","c"});
    	testCommaColumn = new DataColumn(DataType.TYPE_STRING, new String[] {"","",""});
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
    	LoadCSV x = new LoadCSV();
    	assert(x.init == "Success");
    }
    /** 
	 * Test if it can load strings correctly.
	 * 
	 * @author xzhaoar
	 *
	 */
	@Test
	void containsStringTest() throws DataTableException, IOException {
		//dataTable.addCol("testDataColumn", testDataColumn);
		dataTable.addCol("testStringColumn", testStringColumn);
		SaveCSV.saveCSV("testSaveCSV.csv", dataTable);
		
		DataTable test = LoadCSV.loadCSV("testSaveCSV.csv", "Default"); 
		
		assert(test.containsColumn("testStringColumn"));
	}
	 
	/** 
	 * Test if it can load numbers correctly.
	 * 
	 * @author xzhaoar
	 *
	 */	
	@Test
	void containsNumberTest() throws DataTableException, IOException {
		dataTable.addCol("testDataColumn", testDataColumn);
		//dataTable.addCol("testStringColumn", testStringColumn);
		SaveCSV.saveCSV("testSaveCSV.csv", dataTable);
		
		DataTable test = LoadCSV.loadCSV("testSaveCSV.csv", "Median"); 
		
		assert(test.containsColumn("testDataColumn"));
	}
	/** 
	 * Test the duplicate name handle, if it is working as the naming rule.
	 * 
	 * @author xzhaoar
	 *
	 */	
	@Test
	void containsDoubleTest() throws DataTableException, IOException {
		dataTable.addCol("testDoubleColumn", testDoubleColumn);
		//dataTable.addCol("testStringColumn", testStringColumn);
		SaveCSV.saveCSV("testSaveCSV.csv", dataTable);
		
		DataTable test = LoadCSV.loadCSV("testSaveCSV.csv", "Default"); 
		
		assert(test.containsColumn("testDoubleColumn"));
	}
	/** 
	 * Test the case when missing data handling type is mean
	 * 
	 * @author xzhaoar
	 *
	 */		
	@Test
	void meanTest() throws DataTableException, IOException {
		dataTable.addCol("testDoubleColumn", testDoubleColumn);
		//dataTable.addCol("testStringColumn", testStringColumn);
		SaveCSV.saveCSV("testSaveCSV.csv", dataTable);
		
		DataTable test = LoadCSV.loadCSV("testSaveCSV.csv", "Mean"); 
		
		assert(test.containsColumn("testDoubleColumn"));
	}
	/** 
	 * Test the case when missing data handling type is median
	 * 
	 * @author xzhaoar
	 *
	 */		
	@Test
	void medianTest() throws DataTableException, IOException {
		dataTable.addCol("testDoubleColumn", testDoubleColumn);
		//dataTable.addCol("testStringColumn", testStringColumn);
		SaveCSV.saveCSV("testSaveCSV.csv", dataTable);
		
		DataTable test = LoadCSV.loadCSV("testSaveCSV.csv", "Median"); 
		
		assert(test.containsColumn("testDoubleColumn"));
	}
	/** 
	 * Test the duplicated case for double.
	 * 
	 * @author xzhaoar
	 *
	 */	
	@Test
	void containsDupTest() throws DataTableException, IOException {
		dataTable.addCol("testDoubleColumn", testDoubleColumn);
		dataTable.addCol("testDoubleColumn2", testDoubleColumn);
		//dataTable.addCol("testStringColumn", testStringColumn);
		SaveCSV.saveCSV("testSaveCSV.csv", dataTable);
		
		DataTable test = LoadCSV.loadCSV("testSaveCSV.csv", "Default"); 
	
		assert(test.containsColumn("testDoubleColumn2"));
	}
	/** 
	 * Test the duplicated case for string.
	 * 
	 * @author xzhaoar
	 *
	 */		
	@Test
	void containsDupTest2() throws DataTableException, IOException {
		dataTable.addCol("testStringColumn", testStringColumn);
		dataTable.addCol("testStringColumn2", testStringColumn);
		//dataTable.addCol("testStringColumn", testStringColumn);
		SaveCSV.saveCSV("testSaveCSV.csv", dataTable);
		
		DataTable test = LoadCSV.loadCSV("testSaveCSV.csv", "Default"); 
	
		assert(test.containsColumn("testStringColumn"));
	}
	/** 
	 * Test the case when all the input are comma
	 * 
	 * @author xzhaoar
	 *
	 */		
	@Test
	void allCommaTest() throws DataTableException, IOException {
		dataTable.addCol("testCommaColumn", testCommaColumn);
		dataTable.addCol("testCommaColumn2", testCommaColumn);
		
		SaveCSV.saveCSV("testSaveCSV.csv", dataTable);
		DataTable test = LoadCSV.loadCSV("testSaveCSV.csv", "Default");
		assert(test.containsColumn("testCommaColumn"));
	}
	/** 
	 * Test the general case when we need to handle the missing data
	 * 
	 * @author xzhaoar
	 *
	 */		
	@Test
	void fillTest() throws IOException, DataTableException {
		
		File csv = new File("testSaveCSV.csv"); 
        BufferedWriter bw = new BufferedWriter(new FileWriter(csv));
        bw.write("1,,2,a,,");
        bw.newLine();
        bw.write("1,1,2,a,3,a,5");
        bw.newLine();
        bw.write(",,");
        bw.close();
        DataTable test = LoadCSV.loadCSV("testSaveCSV.csv", "Default");

        assert(test.containsColumn("1"));
	}
			
		
	
    
}
