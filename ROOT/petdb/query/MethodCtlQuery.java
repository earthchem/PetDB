
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class MethodCtlQuery extends ControlQuery 
{

	public MethodCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
	
		qry = "select t.method_code, t.method_name from method t order by t.method_code";
		return 1;
	}



	public synchronized void  prepareData() 
	{
		try {
			ds = (DataSet) new AValuePerKeyDS(rs);
		} 
		catch (Exception e) 
		{
		;
		}
	}
	

}

