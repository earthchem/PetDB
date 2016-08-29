
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class Rock_MethodCtlQuery extends ControlQuery 
{

	public Rock_MethodCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
	
		qry = " select t.samp_technique_num, t.samp_technique_desc from samp_technique_list t";
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

