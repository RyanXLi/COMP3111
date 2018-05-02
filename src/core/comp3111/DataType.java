package core.comp3111;

import java.io.Serializable;

/**
 * DataType helper class In the sample project, 3 types are supported
 * 
 * @author cspeter
 *
 */
public class DataType implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final String TYPE_OBJECT = "java.lang.Object";
	public static final String TYPE_NUMBER = "java.lang.Number";
	public static final String TYPE_STRING = "java.lang.String";

	// TODO: Add more type mapping here...
	
	// for 100% coverage
	public DataType(){
		init = "Success";
	}
	public String init;
}
