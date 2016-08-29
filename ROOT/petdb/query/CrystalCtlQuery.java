
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class CrystalCtlQuery extends ControlQuery 
{

	public CrystalCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
	
		qry = " select t.crystal_code, t.crystal_description from crystal_list t order by t.crystal_code";
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

