
package petdb.query;

import java.sql.*;
import petdb.data.*;
import petdb.criteria.*;

public class SummaryCQuery extends CombinedQuery 
{
	protected DataSet ds;

	public SummaryCQuery()
	{
		ds= null;	
	}

	public SummaryCQuery(CombinedCriteria cc) 
	{
		super(cc);
	}
	
	protected  int setQuery()
	{
		int ret = super.setQuery();
		
		//if (ccriteria.size() !=1)
		//{
		qry = "select sample.sample_num "
			+ " FROM  sample "
			+ " WHERE sample.sample_num in " + qry;
		//	+ " ORDER BY sample.sample_id";
		//}
		return ret;
	}


        public  synchronized DataSet getDataSet()
        {
                if ((ccriteria != null) && ccriteria.isSet())
                {
                        if (runQuery() == 0) {
                                prepareData();
				closeQuery();
                                return ds;
			}
                }
		closeQuery();
                return null;
        }

       public synchronized void  prepareData()
        {
                try {
                        ds = (DataSet) new CriteriaDS(rs);
                }
                catch (Exception e)
                {
                ;
                }
        }

	public String toString()
	{
		return super.toString();
 		//+ "::::" + ccriteria.toString();

	}

		
}

