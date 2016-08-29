package petdb.query;

import java.sql.*;
import petdb.data.*;

/* This class will get item_code and item_description from item_measured table and 
 * save data in AValuePerKeyDS object */
public class ItemMeasuredTableQuery extends ControlQuery 
{

	public ItemMeasuredTableQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
	
		qry = "select item_code, item_description from item_measured order by item_code";
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