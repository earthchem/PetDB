
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class Exp_YearCtlQuery extends ControlQuery 
{

	public Exp_YearCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
	
		qry = "SELECT DISTINCT e.exp_year_from"
			+ " FROM EXPEDITION e"
			+ " WHERE e.EXP_YEAR_FROM >1900"
			+ " ORDER BY e.EXP_YEAR_FROM";
		return 1;
	}



	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new ListDS(rs);
		} 
		catch (Exception e) 
		{
		;
		}
	}
	

}

