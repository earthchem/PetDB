
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class Rock_ClassCtlQuery extends ControlQuery 
{

	public Rock_ClassCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
	
		qry = "SELECT r.rocktype_name, t.rockclass, t.rockclass_num"
			+ " FROM rockclass t, rocktype r"
			+ " WHERE t.rocktype_num = r.rocktype_num"
			+ " AND t.rockclass is not Null "
			+ " ORDER BY r.rocktype_name, t.rockclass";

		return 1;
	}



	public synchronized void  prepareData() 
	{
		try {
			//ds = (DataSet) new RockRecordDS(rs);
			//ds = (DataSet) new UniformValueDS(rs);
			ds = (DataSet) new UniformKeyedValueDS(rs);
		} 
		catch (Exception e) 
		{
		;
		}
	}
	

}

