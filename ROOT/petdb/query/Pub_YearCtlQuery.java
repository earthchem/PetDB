
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class Pub_YearCtlQuery extends ControlQuery 
{

	public Pub_YearCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
		qry = "SELECT DISTINCT PUB_YEAR "
			+ " FROM REFERENCE WHERE PUB_YEAR > 1900 "
			+ " ORDER BY PUB_YEAR DESC";
		return 1;
	}


	public synchronized void prepareData() 
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

