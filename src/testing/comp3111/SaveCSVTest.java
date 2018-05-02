package testing.comp3111;

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
import java.io.PrintStream;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

/** A SaveCSV file test
 * 
 * @author xzhaoar
 *
 */
class SaveCSVTest {  
	DataColumn testDataColumn;
	DataTable dataTable;
	DataColumn testStringColumn;
	/** 
	 * Initialize the test data needed.
	 * 
	 * @author xzhaoar
	 *
	 */
	@BeforeEach
    void init() {
    	testDataColumn = new DataColumn(DataType.TYPE_NUMBER, new Number[] {1,2,3});
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
    	SaveCSV x = new SaveCSV();
    	assert(x.init == "Success");
    }
	/** 
	 * Test for saving the data by read the file with primitive way.
	 * 
	 * @author xzhaoar
	 *
	 */
	@Test
	void writeFileTest() throws DataTableException, IOException {
		dataTable.addCol("testDataColumn", testDataColumn);
		dataTable.addCol("testStringColumn", testStringColumn);
		
		SaveCSV.saveCSV("testSaveCSV.csv", dataTable);
		BufferedReader reader = new BufferedReader(new FileReader("testSaveCSV.csv"));
		ArrayList<String> testList = new ArrayList<>();
		String line = null;
		while ((line = reader.readLine()) != null) {
			testList.add(line);
		}
		ArrayList<String> trueList = new ArrayList<>();
		trueList.add("1,1");
		trueList.add("2,2");
		trueList.add("3,3");
		reader.close();
		
		assert(trueList.equals(testList));
	}
	
		
	
		
		
	
    
}
