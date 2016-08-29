
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class Exp_ScieCtlQuery extends ControlQuery 
{

	public Exp_ScieCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
	
		qry = "SELECT DISTINCT P.PERSON_NUM, P.LAST_NAME ||', '||P.FIRST_NAME PERSON_NAME"
			+ " FROM PERSON P, CHIEF_SCIENTIST C"
			+ " WHERE P.PERSON_NUM=C.PERSON_NUM"
			+ " ORDER BY PERSON_NAME"; 
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

