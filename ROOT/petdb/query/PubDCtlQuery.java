
package petdb.query;

import java.sql.*;
import petdb.data.*;
import petdb.criteria.*;

public class PubDCtlQuery extends DynamicCtlQuery 
{

	public PubDCtlQuery(Criteria c) 
	{
		super();
		setCriteria(c);
	}
	public synchronized void prepareData() 
	{
		try {
			ds = (DataSet) new PubRecordDS(rs, qry);
		} 
		catch (Exception e) 
		{
		;
		}
	}
	

}

