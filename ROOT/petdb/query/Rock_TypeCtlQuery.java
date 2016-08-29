
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class Rock_TypeCtlQuery extends ControlQuery 
{

	public Rock_TypeCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
		qry = "select distinct item_type_code, item_type_code from item_type";
		return 1;
	}



	public synchronized void prepareData() 
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

