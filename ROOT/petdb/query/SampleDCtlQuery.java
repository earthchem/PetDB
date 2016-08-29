
package petdb.query;

import java.sql.*;
import petdb.data.*;
import petdb.criteria.*;

public class SampleDCtlQuery extends DynamicCtlQuery 
{

	public SampleDCtlQuery(Criteria c) 
	{
		super();
		setCriteria(c);
	}

	public synchronized void  prepareData() 
	{
		try {
			ds = (DataSet) new SampleRecordDS(rs, qry);
		} 
		catch (Exception e) 
		{
			;
		}
	}
	

}

