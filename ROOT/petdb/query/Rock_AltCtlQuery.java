
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class Rock_AltCtlQuery extends ControlQuery 
{

	public Rock_AltCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
	
		qry = "SELECT DISTINCT alteration_num, alteration_name"
			+ " FROM alteration"
			+ " ORDER BY alteration_name";
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

