
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class TectCtlQuery extends ControlQuery 
{

	public TectCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
		qry ="SELECT DISTINCT t.TECTONIC_SETTING_NUM, t.TECTONIC_SETTING_name "
			+ " FROM TECTONIC_SETTING_LIST t"
			+ " ORDER BY t.TECTONIC_SETTING_name";
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

