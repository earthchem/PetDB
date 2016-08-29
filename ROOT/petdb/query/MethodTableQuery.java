
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class MethodTableQuery extends ControlQuery 
{

	public MethodTableQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
	
		qry = "select method_code, method_name from method order by method_code";
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