
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class ItemTypeCtlQuery extends ControlQuery 
{

	public ItemTypeCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
		qry = "select distinct item_type_num, item_type_code from item_type where item_type_code is not null";
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

