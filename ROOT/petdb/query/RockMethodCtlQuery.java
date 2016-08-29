
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class RockMethodCtlQuery extends ControlQuery 
{

	public RockMethodCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
	
		qry = " select t.samp_technique_code, t.samp_technique_desc"
			+ " from samp_technique_list t order by t.samp_technique_code";
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

