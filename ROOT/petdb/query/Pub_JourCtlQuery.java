
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class Pub_JourCtlQuery extends ControlQuery 
{

	public Pub_JourCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
		qry = "SELECT DISTINCT JOURNAL "
			+ " FROM Reference  WHERE JOURNAL Is Not Null and status <> 'IN_QUEUE' "
			+ " ORDER BY JOURNAL";
		return 1;
	}


	public synchronized void  prepareData() 
	{
		try {
			ds = (DataSet) new ListDS(rs);
		} 
		catch (Exception e) 
		{
		;
		}
	}
	

}

