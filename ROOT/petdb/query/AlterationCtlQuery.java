
package petdb.query;

import java.sql.*;
import petdb.data.*;

// Test Change
public class AlterationCtlQuery extends ControlQuery 
{

	public AlterationCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
	
		qry = "SELECT DISTINCT alteration_code, alteration_name"
			+ " FROM alteration"
			+ " ORDER BY alteration_code";
		return 1;

	}



	public synchronized void  prepareData() 
	{
		try {
			ds = (DataSet) new  AValuePerKeyDS(rs);
		} 
		catch (Exception e) 
		{
		;
		}
	}
	

}

