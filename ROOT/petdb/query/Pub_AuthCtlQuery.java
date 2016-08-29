
package petdb.query;

import java.sql.*;
import petdb.data.*;

public class Pub_AuthCtlQuery extends ControlQuery 
{

	public Pub_AuthCtlQuery() 
	{
		super();
		setQuery();
	}

	protected int setQuery() 
	{
	
		qry = "SELECT DISTINCT P.PERSON_NUM,"
			+ " P.LAST_NAME ||', '||P.FIRST_NAME "
			+ " FROM PERSON P, AUTHOR_LIST A, REFERENCE R "
			+ " WHERE LAST_NAME IS NOT NULL AND P.PERSON_NUM = A.PERSON_NUM " 
			+ " AND A.REF_NUM=R.REF_NUM AND R.status <> 'IN_QUEUE' "
			+ " ORDER BY P.LAST_NAME ||', '||P.FIRST_NAME";
		
		//DataDCtlQuery.writeQueryToFile(qry, "C:\\Users\\Lulin Song\\Downloads\\pubauth.sql");
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

