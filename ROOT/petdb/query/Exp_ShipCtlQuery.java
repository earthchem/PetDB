
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class Exp_ShipCtlQuery extends ControlQuery 
{

	public Exp_ShipCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
	
		qry = "SELECT DISTINCT S.SHIP_NUM, S.SHIP_NAME"
			+ " FROM SHIP s"
			+ " WHERE s.ship_num in "
			+ " (SELECT DISTINCT e.ship_num " 
			+ "	 FROM EXPEDITION e"
			+ "	 WHERE e.ship_num Is Not Null)"
			+ " ORDER BY S.SHIP_NAME";
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

