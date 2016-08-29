
package petdb.query;

import java.sql.*;
import petdb.data.*;

/* FIXME(LS): redunt layer of abstraction, could combined with Query*/
abstract public class ControlQuery extends Query
{
	protected DataSet ds;

/* Will connect to database, run the query and 
 * convert ResultSet to DataSet and close query 
*/
	public synchronized DataSet getDataSet()
	{
		if (ds != null) return ds;
		if (runQuery() == 0) {
			prepareData();
			closeQuery();
			return ds;
		}
		closeQuery();
		return null;
	}
}
