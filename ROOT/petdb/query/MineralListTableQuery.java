
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class MineralListTableQuery extends ControlQuery 
{

	public MineralListTableQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
	
		qry = "select mineral_code, mineral_name from mineral_list order by mineral_code";
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