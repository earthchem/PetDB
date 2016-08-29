
package petdb.query;

import java.sql.*;
import petdb.data.*;
import petdb.criteria.*;

public class ExpDCtlQuery extends DynamicCtlQuery 
{

	public ExpDCtlQuery(Criteria c) 
	{
		super();
		setCriteria(c);
	}


	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new ExpRecordDS(rs, qry);
		} 
		catch (Exception e) 
		{
		}
	}
	

}

