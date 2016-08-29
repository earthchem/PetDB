
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class NavigationCtlQuery extends ControlQuery 
{

	public NavigationCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
	
		qry = " select t.navmethod_code, t.navmethod_name from navmethod t order by t.navmethod_code";
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

