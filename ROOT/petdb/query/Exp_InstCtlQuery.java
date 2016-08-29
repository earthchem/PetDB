
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class Exp_InstCtlQuery extends ControlQuery 
{

	public Exp_InstCtlQuery() 
	{
		super();
		setQuery();
	}

	protected synchronized void setQuery() 
	{
	
		qry = "SELECT distinct I.INSTITUTION_NUM, I.INSTITUTION||decode(I.DEPARTMENT,null,'',', '||I.DEPARTMENT)"
			+ " FROM INSTITUTION I, EXPEDITION E"
			+ " WHERE I.INSTITUTION_NUM=E.INSTITUTION_NUM"
			+ " ORDER BY I.INSTITUTION||decode(I.DEPARTMENT,null,'',', '||I.DEPARTMENT)"; 
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

