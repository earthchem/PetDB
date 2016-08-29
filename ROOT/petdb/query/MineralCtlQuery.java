
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class MineralCtlQuery extends ControlQuery 
{

	public MineralCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
		qry = "select distinct mineral_num, mineral_code from mineral_list where mineral_code is not null";
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

