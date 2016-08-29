
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class RockTypeCtlQuery extends ControlQuery 
{

	public RockTypeCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
		qry = "select distinct item_type_num, item_type_code from item_type";
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

