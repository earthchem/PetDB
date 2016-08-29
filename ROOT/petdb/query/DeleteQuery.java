package petdb.query;

import java.sql.*;
import petdb.config.*;
import petdb.data.*;
public class DeleteQuery extends EditingQuery
{

	public DeleteQuery(String session_id)
	{
		super();
		int r = setQuery(session_id, "");
	}
    
    public DeleteQuery(String session_id, boolean isTagAlong)
	{
		super();
		int r = setTagAlongQuery(session_id, "");
	}


	public  int setQuery(String session_id, String s)
	{
		qry = "delete from sessionparam"
		+ " where session_id ='" + session_id + "'";
		return 1;
	}
    
    public  int setTagAlongQuery(String session_id, String s)
	{
		qry = "delete from sessionparam"
		+ " where (param_name = 'sample_num_ta' and date_inserted < sysdate-1) or (param_name = 'sample_num_ta' and session_id ='" + session_id + "')";
		return 1;
	}
}

