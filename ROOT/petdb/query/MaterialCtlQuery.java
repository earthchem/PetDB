
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class MaterialCtlQuery extends ControlQuery 
{

	public MaterialCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
	
		qry = " select t.material_code, t.material_name from material t order by t.material_code";
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

