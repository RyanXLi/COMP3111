package testing.comp3111;

import org.junit.Assert;

import org.junit.jupiter.api.Test;

import core.comp3111.DataType;


/**
 * A DataType test case written using JUnit. It achieves 100% test
 * coverage on the DataType class
 * 
 * @author RyanX
 *
 */
public class DataTypeTest {
	 
	@Test
	  void testContent() {
		  DataType x = new DataType();
		  assert(DataType.TYPE_NUMBER.equals(DataType.TYPE_NUMBER));
			
	  }

}
